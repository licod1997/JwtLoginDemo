package vn.edu.fpt.jwtlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.fpt.jwtlogin.model.Auth;
import vn.edu.fpt.jwtlogin.model.User;
import vn.edu.fpt.jwtlogin.remote.ApiCall;
import vn.edu.fpt.jwtlogin.remote.RetroInstance;
import vn.edu.fpt.jwtlogin.tokenmanager.TokenManager;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private TokenManager tokenManager;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        tokenManager = new TokenManager( getApplicationContext() );
        btnLogin = (Button) findViewById( R.id.btnLogin );
        edtUsername = (EditText) findViewById( R.id.edtUsername );
        edtPassword = (EditText) findViewById( R.id.edtPassword );

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                btnLoginOnClickListener( view );
            }
        } );
    }

    private void btnLoginOnClickListener( View view ) {
        final ApiCall apiCall = RetroInstance.getRetrofitSpringInstanc().create( ApiCall.class );
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();

        Call<Auth> login = apiCall.login( new User( username, password ) );
        login.enqueue( new Callback<Auth>() {
            @Override
            public void onResponse( Call<Auth> call, Response<Auth> response ) {
                if ( response.code() == 200 ) {
                    Auth auth = response.body();
                    if ( auth != null ) {
                        showToast( auth.toString() );
                        tokenManager.createSession( username, auth.getToken().split( "Authorization=" )[1] );
                        Intent intent = new Intent( MainActivity.this, NextActivity.class );
                        MainActivity.this.startActivity( intent );
                    }
                } else if ( response.code() == 401 ) {
                    showToast( "Username or password was incorrect" );
                } else {
                    showToast( "Some errors have occurred" );
                }
            }

            @Override
            public void onFailure( Call<Auth> call, Throwable t ) {
                showToast( "" + t.getMessage() );
            }
        } );
    }

    private void showToast( String msg ) {
        Toast.makeText( this, msg + "", Toast.LENGTH_LONG ).show();
    }
}
