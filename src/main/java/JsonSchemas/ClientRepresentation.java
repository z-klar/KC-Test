package JsonSchemas;

import java.util.Map;

public class ClientRepresentation {

    private Map<String, String> access;
    private String adminUrl;
    private boolean alwaysDisplayInConsole;
    private Map<String, String> attributes;
    private Map<String, String> authenticationFlowBindingOverrides;
    private boolean authorizationServicesEnabled;
    private ResourceServerRepresentation authorizationSettings;
    private String baseUrl;
    private boolean bearerOnly;
    private String clientAuthenticatorType;
    private String clientId;
    private boolean consentRequired;
    private String [] defaultClientScopes;
    private String [] defaultRoles;
    private String description;
    private boolean directAccessGrantsEnabled;
    private boolean enabled;
    private boolean frontchannelLogout;
    private boolean fullScopeAllowed;
    private String id;
    private boolean implicitFlowEnabled;
    private String name;
    private int nodeReRegistrationTimeout;
    private int notBefore;
    private String [] optionalClientScopes;
    private String origin;
    private String protocol;
    private ProtocolMapperRepresentation [] protocolMappers;
    private boolean publicClient;
    private String [] redirectUris;
    private Map<String, String> registeredNodes;
    private String registrationAccessToken;
    private String rootUrl;
    private String secret;
    private boolean serviceAccountsEnabled;
    private boolean standardFlowEnabled;
    private boolean surrogateAuthRequired;
    private String [] webOrigins;


    public Map<String, String> getAccess() {
        return access;
    }

    public void setAccess(Map<String, String> access) {
        this.access = access;
    }

    public String getAdminUrl() {
        return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public boolean isAlwaysDisplayInConsole() {
        return alwaysDisplayInConsole;
    }

    public void setAlwaysDisplayInConsole(boolean alwaysDisplayInConsole) {
        this.alwaysDisplayInConsole = alwaysDisplayInConsole;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getAuthenticationFlowBindingOverrides() {
        return authenticationFlowBindingOverrides;
    }

    public void setAuthenticationFlowBindingOverrides(Map<String, String> authenticationFlowBindingOverrides) {
        this.authenticationFlowBindingOverrides = authenticationFlowBindingOverrides;
    }

    public boolean isAuthorizationServicesEnabled() {
        return authorizationServicesEnabled;
    }

    public void setAuthorizationServicesEnabled(boolean authorizationServicesEnabled) {
        this.authorizationServicesEnabled = authorizationServicesEnabled;
    }

    public ResourceServerRepresentation getAuthorizationSettings() {
        return authorizationSettings;
    }

    public void setAuthorizationSettings(ResourceServerRepresentation authorizationSettings) {
        this.authorizationSettings = authorizationSettings;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isBearerOnly() {
        return bearerOnly;
    }

    public void setBearerOnly(boolean bearerOnly) {
        this.bearerOnly = bearerOnly;
    }

    public String getClientAuthenticatorType() {
        return clientAuthenticatorType;
    }

    public void setClientAuthenticatorType(String clientAuthenticatorType) {
        this.clientAuthenticatorType = clientAuthenticatorType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isConsentRequired() {
        return consentRequired;
    }

    public void setConsentRequired(boolean consentRequired) {
        this.consentRequired = consentRequired;
    }

    public String[] getDefaultClientScopes() {
        return defaultClientScopes;
    }

    public void setDefaultClientScopes(String[] defaultClientScopes) {
        this.defaultClientScopes = defaultClientScopes;
    }

    public String[] getDefaultRoles() {
        return defaultRoles;
    }

    public void setDefaultRoles(String[] defaultRoles) {
        this.defaultRoles = defaultRoles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDirectAccessGrantsEnabled() {
        return directAccessGrantsEnabled;
    }

    public void setDirectAccessGrantsEnabled(boolean directAccessGrantsEnabled) {
        this.directAccessGrantsEnabled = directAccessGrantsEnabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFrontchannelLogout() {
        return frontchannelLogout;
    }

    public void setFrontchannelLogout(boolean frontchannelLogout) {
        this.frontchannelLogout = frontchannelLogout;
    }

    public boolean isFullScopeAllowed() {
        return fullScopeAllowed;
    }

    public void setFullScopeAllowed(boolean fullScopeAllowed) {
        this.fullScopeAllowed = fullScopeAllowed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isImplicitFlowEnabled() {
        return implicitFlowEnabled;
    }

    public void setImplicitFlowEnabled(boolean implicitFlowEnabled) {
        this.implicitFlowEnabled = implicitFlowEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNodeReRegistrationTimeout() {
        return nodeReRegistrationTimeout;
    }

    public void setNodeReRegistrationTimeout(int nodeReRegistrationTimeout) {
        this.nodeReRegistrationTimeout = nodeReRegistrationTimeout;
    }

    public int getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(int notBefore) {
        this.notBefore = notBefore;
    }

    public String[] getOptionalClientScopes() {
        return optionalClientScopes;
    }

    public void setOptionalClientScopes(String[] optionalClientScopes) {
        this.optionalClientScopes = optionalClientScopes;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public ProtocolMapperRepresentation[] getProtocolMappers() {
        return protocolMappers;
    }

    public void setProtocolMappers(ProtocolMapperRepresentation[] protocolMappers) {
        this.protocolMappers = protocolMappers;
    }

    public boolean isPublicClient() {
        return publicClient;
    }

    public void setPublicClient(boolean publicClient) {
        this.publicClient = publicClient;
    }

    public String[] getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String[] redirectUris) {
        this.redirectUris = redirectUris;
    }

    public Map<String, String> getRegisteredNodes() {
        return registeredNodes;
    }

    public void setRegisteredNodes(Map<String, String> registeredNodes) {
        this.registeredNodes = registeredNodes;
    }

    public String getRegistrationAccessToken() {
        return registrationAccessToken;
    }

    public void setRegistrationAccessToken(String registrationAccessToken) {
        this.registrationAccessToken = registrationAccessToken;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isServiceAccountsEnabled() {
        return serviceAccountsEnabled;
    }

    public void setServiceAccountsEnabled(boolean serviceAccountsEnabled) {
        this.serviceAccountsEnabled = serviceAccountsEnabled;
    }

    public boolean isStandardFlowEnabled() {
        return standardFlowEnabled;
    }

    public void setStandardFlowEnabled(boolean standardFlowEnabled) {
        this.standardFlowEnabled = standardFlowEnabled;
    }

    public boolean isSurrogateAuthRequired() {
        return surrogateAuthRequired;
    }

    public void setSurrogateAuthRequired(boolean surrogateAuthRequired) {
        this.surrogateAuthRequired = surrogateAuthRequired;
    }

    public String[] getWebOrigins() {
        return webOrigins;
    }

    public void setWebOrigins(String[] webOrigins) {
        this.webOrigins = webOrigins;
    }
}
