package pwr.ist.aiob.model;

public class LoginRequest {
    private String username;
    private String password;
    private String mfaCode;

    public LoginRequest() {

    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest(String username, String password, String mfaCode) {
        this.username = username;
        this.password = password;
        this.mfaCode = mfaCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMfaCode() {
        return mfaCode;
    }

    public void setMfaCode(String mfaCode) {
        this.mfaCode = mfaCode;
    }
}
