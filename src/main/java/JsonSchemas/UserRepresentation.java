package JsonSchemas;

import java.util.Map;

public class UserRepresentation {

    private Map<String, Boolean> access;
    private Map<String, String> attributes;
    private UserConsentRepresentation [] clientConsents;
    private Map<String, String> clientRoles;   // ???????????????
    private long createdTimestamp;              // 64 bit ????
    private CredentialRepresentation [] credentials;
    private String  []  disableableCredentialTypes;
    private String email;
    private boolean emailVerified;
    private boolean enabled;
    private FederatedIdentityRepresentation [] federatedIdentities;
    private String federationLink;
    private String firstName;
    private String [] groups;
    private String id;
    private String lastName;
    private int notBefore;
    private String origin;
    private String [] realmRoles;
    private String [] requiredActions;
    private String self;
    private String serviceAccountClientId;
    private String username;

    //############################################################################
    public UserRepresentation(String sname) {
        this.username = sname;
    }

    public UserRepresentation() {

    }
    //============================================================================
    public Map<String, Boolean> getAccess() {
        return access;
    }

    public void setAccess(Map<String, Boolean> access) {
        this.access = access;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public UserConsentRepresentation[] getClientConsents() {
        return clientConsents;
    }

    public void setClientConsents(UserConsentRepresentation[] clientConsents) {
        this.clientConsents = clientConsents;
    }

    public Map<String, String> getClientRoles() {
        return clientRoles;
    }

    public void setClientRoles(Map<String, String> clientRoles) {
        this.clientRoles = clientRoles;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public CredentialRepresentation[] getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialRepresentation[] credentials) {
        this.credentials = credentials;
    }

    public String[] getDisableableCredentialTypes() {
        return disableableCredentialTypes;
    }

    public void setDisableableCredentialTypes(String[] disableableCredentialTypes) {
        this.disableableCredentialTypes = disableableCredentialTypes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public FederatedIdentityRepresentation[] getFederatedIdentities() {
        return federatedIdentities;
    }

    public void setFederatedIdentities(FederatedIdentityRepresentation[] federatedIdentities) {
        this.federatedIdentities = federatedIdentities;
    }

    public String getFederationLink() {
        return federationLink;
    }

    public void setFederationLink(String federationLink) {
        this.federationLink = federationLink;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(int notBefore) {
        this.notBefore = notBefore;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String[] getRealmRoles() {
        return realmRoles;
    }

    public void setRealmRoles(String[] realmRoles) {
        this.realmRoles = realmRoles;
    }

    public String[] getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(String[] requiredActions) {
        this.requiredActions = requiredActions;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getServiceAccountClientId() {
        return serviceAccountClientId;
    }

    public void setServiceAccountClientId(String serviceAccountClientId) {
        this.serviceAccountClientId = serviceAccountClientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return (String.format("Name: [%s]  Id: [%s]  Enabled: [%s]  Email: [%s]",
                                    username,   id,        enabled,       email));
    }
}
