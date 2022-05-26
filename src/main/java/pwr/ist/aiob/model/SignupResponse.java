package pwr.ist.aiob.model;

public class SignupResponse {

    public enum Status {
        OK, USERNAME_TAKEN
    }

    private final SignupResponse.Status status;

    private final String username;

    private final String secret;

    public SignupResponse(SignupResponse.Status status, String username) {
        this(status, username, null);
    }

    public SignupResponse(SignupResponse.Status status, String username, String secret) {
        this.status = status;
        this.username = username;
        this.secret = secret;
    }

    public SignupResponse.Status getStatus() {
        return this.status;
    }

    public String getSecret() {
        return this.secret;
    }

    public String getUsername() {
        return username;
    }
}
