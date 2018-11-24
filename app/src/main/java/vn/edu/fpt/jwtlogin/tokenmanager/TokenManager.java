package vn.edu.fpt.jwtlogin.tokenmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int mode = 0;
    private static final String REF_NAME = "JwtToken";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_JWT_TOKEN = "token";
    private Context context;

    public TokenManager( Context context ) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences( REF_NAME, mode );
        editor = sharedPreferences.edit();
    }

    public void createSession( String username, String jwtValue ) {
        editor.putString( KEY_USERNAME, username );
        editor.putString( KEY_JWT_TOKEN, jwtValue );
        editor.commit();
    }

    public String getUsernameFromSession() {
        return sharedPreferences.getString( KEY_USERNAME, "" );
    }

    public String getTokenFromSession() {
        return sharedPreferences.getString( KEY_JWT_TOKEN, "" );
    }
}
