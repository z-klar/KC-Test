package JsonSchemas;

public class ResourceServerRepresentation {

    private boolean allowRemoteResourceManagement;
    private String clientId;
    private enum decisionStrategy {AFFIRMATIVE, UNANIMOUS, CONSENSUS};
    private String id;
    private String name;
    private PolicyRepresentation [] policies;
    private enum policyEnforcementMode {ENFORCING, PERMISSIVE, DISABLED};
    private ResourceRepresentation [] resources;
    private ScopeRepresentation [] scopes;


    public boolean isAllowRemoteResourceManagement() {
        return allowRemoteResourceManagement;
    }

    public void setAllowRemoteResourceManagement(boolean allowRemoteResourceManagement) {
        this.allowRemoteResourceManagement = allowRemoteResourceManagement;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PolicyRepresentation[] getPolicies() {
        return policies;
    }

    public void setPolicies(PolicyRepresentation[] policies) {
        this.policies = policies;
    }

    public ResourceRepresentation[] getResources() {
        return resources;
    }

    public void setResources(ResourceRepresentation[] resources) {
        this.resources = resources;
    }

    public ScopeRepresentation[] getScopes() {
        return scopes;
    }

    public void setScopes(ScopeRepresentation[] scopes) {
        this.scopes = scopes;
    }
}
