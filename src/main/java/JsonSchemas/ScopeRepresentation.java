package JsonSchemas;

public class ScopeRepresentation {

    private String displayName;
    private String iconUri;
    private String id;
    private String name;
    private PolicyRepresentation [] policies;
    private ResourceRepresentation [] resources;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
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
}
