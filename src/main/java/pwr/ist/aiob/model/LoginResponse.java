package pwr.ist.aiob.model;

public class LoginResponse {
    private final String jwt;
    private final String role;

    public LoginResponse(String jwt, String role) {
        this.jwt = jwt;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public String getRole() {
        return role;
    }
}
