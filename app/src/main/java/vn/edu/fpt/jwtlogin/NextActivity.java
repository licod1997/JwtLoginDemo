package vn.edu.fpt.jwtlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.fpt.jwtlogin.remote.ApiCall;
import vn.edu.fpt.jwtlogin.remote.RetroInstance;
import vn.edu.fpt.jwtlogin.tokenmanager.TokenManager;

public class NextActivity extends AppCompatActivity {

    private Button btnTestAccess;
    private TokenManager tokenManager;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_next );

        tokenManager = new TokenManager( getApplicationContext() );
        btnTestAccess = (Button) findViewById( R.id.btnTestAccess );

        btnTestAccess.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                btnTestAccessOnClickListener( view );
            }
        } );
    }

    private void btnTestAccessOnClickListener( View view ) {
        final ApiCall apiCall = RetroInstance.getRetrofitSpringInstanc().create( ApiCall.class );
        final String accessToken = tokenManager.getTokenFromSession();

        Call<String> getAccess = apiCall.testAccess( accessToken );
        getAccess.enqueue( new Callback<String>() {
            @Override
            public void onResponse( Call<String> call, Response<String> response ) {
                if ( response.code() == 200 ) {
                    showToast( response.body() );
                } else if ( response.code() == 401 ) {
                    showToast( "Session timed out. Please re-login" );
                    Intent intent = new Intent( NextActivity.this, MainActivity.class );
                    NextActivity.this.startActivity( intent );
                } else {
                    showToast( "Some errors have occurred: " + response.body() );
                }
            }

            @Override
            public void onFailure( Call<String> call, Throwable t ) {
                showToast( "" + t.getMessage() );
            }
        } );
    }

    private void showToast( String msg ) {
        Toast.makeText( this, msg + "", Toast.LENGTH_LONG ).show();
    }
}
