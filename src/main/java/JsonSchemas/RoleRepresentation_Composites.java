package JsonSchemas;

import java.util.Map;

public class RoleRepresentation_Composites {

    private Map<String, String> client;
    private String [] realm;

    public Map<String, String> getClient() {
        return client;
    }

    public void setClient(Map<String, String> client) {
        this.client = client;
    }

    public String[] getRealm() {
        return realm;
    }

    public void setRealm(String[] realm) {
        this.realm = realm;
    }
}
