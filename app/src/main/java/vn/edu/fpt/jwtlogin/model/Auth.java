package vn.edu.fpt.jwtlogin.model;

public class Auth {
    private String token;
    private long expire;

    public Auth() {
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire( long expir ) {
        this.expire = expir;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "token='" + token + '\'' +
                ", expire=" + expire +
                '}';
    }
}
