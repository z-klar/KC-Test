package JsonSchemas;

import java.util.Map;

public class RoleRepresentation {

    private Map<String, String> attributes;
    private boolean clientRole;
    private boolean composite;
    private RoleRepresentation_Composites composites;
    private String containerId;
    private String description;
    private String id;
    private String name;

    public RoleRepresentation(String sname) {
        this.name = sname;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public boolean isClientRole() {
        return clientRole;
    }

    public void setClientRole(boolean clientRole) {
        this.clientRole = clientRole;
    }

    public boolean isComposite() {
        return composite;
    }

    public void setComposite(boolean composite) {
        this.composite = composite;
    }

    public RoleRepresentation_Composites getComposites() {
        return composites;
    }

    public void setComposites(RoleRepresentation_Composites composites) {
        this.composites = composites;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
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

    public String toString() {
        return(String.format("Name:[%s] Description:[%s]  Id:[%s]  clientRole:[%s]  composite:[%s]",
                                  name,  description, id,  clientRole, composite));
    }
}
