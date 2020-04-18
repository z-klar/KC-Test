package JsonSchemas;

import java.util.Map;

public class ResourceRepresentation {
    private String id;
    private Map<String, String> attributes;
    private String displayName;
    private String icon_uri;
    private String name;
    private boolean ownerManagedAccess;
    private ScopeRepresentation [] scopes;
    private String type;
    private String [] uris;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIcon_uri() {
        return icon_uri;
    }

    public void setIcon_uri(String icon_uri) {
        this.icon_uri = icon_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwnerManagedAccess() {
        return ownerManagedAccess;
    }

    public void setOwnerManagedAccess(boolean ownerManagedAccess) {
        this.ownerManagedAccess = ownerManagedAccess;
    }

    public ScopeRepresentation[] getScopes() {
        return scopes;
    }

    public void setScopes(ScopeRepresentation[] scopes) {
        this.scopes = scopes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getUris() {
        return uris;
    }

    public void setUris(String[] uris) {
        this.uris = uris;
    }
}
