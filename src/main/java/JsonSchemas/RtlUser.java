package JsonSchemas;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class RtlUser {

    private String usermame;
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean emailVerified;

    public RtlUser() {

    }

    public RtlUser(String name, String sid, String mail, boolean en) {
        this.enabled = en;
        this.email = mail;
        this.id = sid;
        this.usermame = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + usermame +
                ", email=" + email +
                ", enabled=" + enabled +
                "}";
    }

    public String getUsermame() {
        return usermame;
    }

    public void setUsermame(String usermame) {
        this.usermame = usermame;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
