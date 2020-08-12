import java.util.HashMap;

public class KeycloakTools {

    private String KcBaseUrl;
    private String KcAdminTokenUrl;
    private String KcCfgUserName;
    private String KcCfgPassword;

    /*###########################################################################

     ##########################################################################*/
    public KeycloakTools(String base, String admin) {
        KcBaseUrl = base;
        KcAdminTokenUrl = admin;
    }


    /*--------------------------------------------------------------------------

    ---------------------------------------------------------------------------*/
    public HttpQueryResult getAdminToken() {

        HashMap<String, String> headers = new HashMap<String, String>() ;
        headers.put("Accept", "application/json");
        headers.put("Cache-Control", "no-cache");
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        HashMap<String, String> data = new HashMap<String, String>() ;
        data.put("client_id", "admin-cli");
        data.put("grant_type", "password");
        data.put("username", "admin");
        data.put("password", "admin");

        String url = KcBaseUrl + KcAdminTokenUrl;
        //HttpQueryResult res = RestCallApi.RestPostFormData(url, headers, data);
        HttpQueryResult res = RestCallApi.SendHttpRequest("POST", url, headers, data);
        return(res);
    }

    /*--------------------------------------------------------------------------

    ---------------------------------------------------------------------------*/
    public HttpQueryResult getLoginToken(String app, String usr, String pwd) {

        HashMap<String, String> headers = new HashMap<String, String>() ;
        headers.put("Accept", "application/json");
        headers.put("Cache-Control", "no-cache");
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        HashMap<String, String> data = new HashMap<String, String>() ;
        data.put("client_id", app);
        data.put("grant_type", "password");
        data.put("username", usr);
        data.put("password", pwd);

        String url = KcBaseUrl + "realms/rtl/protocol/openid-connect/token";
        //HttpQueryResult res = RestCallApi.RestPostFormData(url, headers, data);
        HttpQueryResult res = RestCallApi.SendHttpRequest("POST", url, headers, data);
        return(res);
    }

    /*--------------------------------------------------------------------------

    ---------------------------------------------------------------------------*/
    public HttpQueryResult sendApiCallGet(String sUrl, String sToken) {

        String authString = "Bearer " + sToken;
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Cache-Control", "no-cache");
        headers.put("Authorization", authString);

        String url = KcBaseUrl + sUrl;
        //HttpQueryResult res = RestCallApi.RestGetData(url, headers);
        HttpQueryResult res = RestCallApi.SendHttpRequest("GET", url, headers, null);
        return(res);
    }

    /*--------------------------------------------------------------------------

    ---------------------------------------------------------------------------*/
    public HttpQueryResult sendApiCallDelete(String sUrl, String sToken) {

        String authString = "Bearer " + sToken;
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Cache-Control", "no-cache");
        headers.put("Authorization", authString);

        String url = KcBaseUrl + sUrl;
        //HttpQueryResult res = RestCallApi.RestDelete(url, headers);
        HttpQueryResult res = RestCallApi.SendHttpRequest("DELETE", url, headers, null);
        return(res);
    }

    /*--------------------------------------------------------------------------

    ---------------------------------------------------------------------------*/
    public HttpQueryResult sendApiCallPostBody(String sUrl, String sToken, String sBody, String bodyType) {

        String authString = "Bearer " + sToken;
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Cache-Control", "no-cache");
        headers.put("Authorization", authString);
        headers.put("Content-Type", bodyType);

        String url = KcBaseUrl + sUrl;
        //HttpQueryResult res = RestCallApi.RestPostRawData(url, headers, sBody);
        HttpQueryResult res = RestCallApi.SendHttpRequest("POST", url, headers, sBody);
        return(res);
    }

    /*--------------------------------------------------------------------------

    ---------------------------------------------------------------------------*/
    public HttpQueryResult sendApiCallPutBody(String sUrl, String sToken, String sBody, String bodyType) {

        String authString = "Bearer " + sToken;
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Cache-Control", "no-cache");
        headers.put("Authorization", authString);
        headers.put("Content-Type", bodyType);

        String url = KcBaseUrl + sUrl;
        //HttpQueryResult res = RestCallApi.RestPutRawData(url, headers, sBody);
        HttpQueryResult res = RestCallApi.SendHttpRequest("PUT", url, headers, sBody);
        return(res);
    }





}
