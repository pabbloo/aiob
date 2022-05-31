package pwr.ist.aiob.model;

import pwr.ist.aiob.security.mfa.MfaTokenData;

public class SignupResponse {

    public enum Status {
        OK, USERNAME_TAKEN
    }

    private final SignupResponse.Status status;

    private final String username;

    private final String secret;

    private String qrCode;

    public SignupResponse(SignupResponse.Status status, String username) {
        this(status, username, null);
    }

    public SignupResponse(SignupResponse.Status status, String username, MfaTokenData mfaTokenData) {
        this.status = status;
        this.username = username;
        this.secret = mfaTokenData.getMfaCode();
        this.qrCode = mfaTokenData.getQrCode();
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

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

}
