package co.medellin.eventos.dto;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UserSummary user;

    public AuthResponse() {}
    public AuthResponse(String accessToken, String refreshToken, UserSummary user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public UserSummary getUser() { return user; }
    public void setUser(UserSummary user) { this.user = user; }
}
