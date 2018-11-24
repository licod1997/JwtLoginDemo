package vn.edu.fpt.jwtlogin.remote;

import retrofit2.Call;
import retrofit2.http.*;
import vn.edu.fpt.jwtlogin.model.Auth;
import vn.edu.fpt.jwtlogin.model.User;

public interface ApiCall {
    @Headers( "Content-Type: application/json" )
    @POST( "/token/generate-token" )
    Call<Auth> login( @Body User user );

    @GET( "/android-demo-auth" )
    Call<String> testAccess( @Query( "Authorization" ) String token );
}
