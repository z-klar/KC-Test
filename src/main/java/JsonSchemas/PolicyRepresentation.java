package JsonSchemas;

import java.util.Map;

public class PolicyRepresentation {

    private Map<String, String> config;
    private enum decisionStrategy  {AFFIRMATIVE, UNANIMOUS, CONSENSUS};
    private String description;
    private String id;
    private enum logic {POSITIVE, NEGATIVE};
    private String name;
    private String owner;
    private String [] policies;
    private String [] resources;
    private ResourceRepresentation [] resourcesData;
    private String [] scopes;
    private ScopeRepresentation [] scopesData;
    private String type;

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String[] getPolicies() {
        return policies;
    }

    public void setPolicies(String[] policies) {
        this.policies = policies;
    }

    public String[] getResources() {
        return resources;
    }

    public void setResources(String[] resources) {
        this.resources = resources;
    }

    public ResourceRepresentation[] getResourcesData() {
        return resourcesData;
    }

    public void setResourcesData(ResourceRepresentation[] resourcesData) {
        this.resourcesData = resourcesData;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public ScopeRepresentation[] getScopesData() {
        return scopesData;
    }

    public void setScopesData(ScopeRepresentation[] scopesData) {
        this.scopesData = scopesData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
