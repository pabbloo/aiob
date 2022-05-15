package pwr.ist.aiob.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    //private Role role
    private Date createdAt;
    private String mfaSecret;
    private boolean has2FA;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        createdAt = new Date();
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setMfaSecret(String mfaSecret) {
        this.mfaSecret = mfaSecret;
    }

    public String getMfaSecret() {
        return mfaSecret;
    }

    public boolean has2FA() {
        return has2FA;
    }

    public void setHas2FA(boolean has2FA) {
        this.has2FA = has2FA;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", mfaSecret='" + mfaSecret + '\'' +
                '}';
    }
}
