package JsonSchemas;

public class UserConsentRepresentation {

    private String clientId;
    private long createdDate;
    private String [] grantedClientScopes;
    private long lastUpdatedDate;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String[] getGrantedClientScopes() {
        return grantedClientScopes;
    }

    public void setGrantedClientScopes(String[] grantedClientScopes) {
        this.grantedClientScopes = grantedClientScopes;
    }

    public long getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(long lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
