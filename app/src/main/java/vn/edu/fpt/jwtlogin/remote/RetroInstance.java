package vn.edu.fpt.jwtlogin.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.43.185:8080";

    public static Retrofit getRetrofitSpringInstanc() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if ( retrofit == null ) {
            retrofit = new Retrofit.Builder()
                    .baseUrl( BASE_URL )
                    .addConverterFactory( GsonConverterFactory.create( gson ) )
                    .build();
        }
        return retrofit;
    }
}
