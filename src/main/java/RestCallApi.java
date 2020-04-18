import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestCallApi {

    private static final Logger log = LoggerFactory.getLogger(RestCallApi.class);



    /*--------------------------------------------------------------------

     --------------------------------------------------------------------*/
    public static HttpQueryResult SendHttpRequest(
            String sType, String sUrl, Map<String,String> Headers, Object Data) {
        int resCode;
        String  msg = "", resData = "";

        try {

            log.info(String.format("Send %s:  URL=%s", sType, sUrl));
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // enable OUTPUT and/or INPUT data
            conn.setDoInput(true);
            if(Data != null) conn.setDoOutput(true);
            // ser request type
            conn.setRequestMethod(sType);
            // set HEADERs
            for (Map.Entry<String, String> entry : Headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            log.debug("  Headers:");
            //String spom = "";
            Map<String, List<String>> props = conn.getRequestProperties();
            for (Map.Entry<String, List<String>> entry : props.entrySet()) {
                log.debug("    - " + entry.getKey() + " : " + entry.getValue());
            }

            if(Data != null) {
                if(Data instanceof String){
                    // set output data - here application/x-www-form-urlencoded
                    OutputStream os = conn.getOutputStream();
                    os.write(((String)Data).getBytes());
                    os.flush();
                    log.debug("  OutData: " + (String)Data);
                }
                if(Data instanceof Map) {
                    Map<String, String> data = (Map<String, String>)Data;
                    // set output data - here application/x-www-form-urlencoded
                    StringBuilder params = new StringBuilder();
                    boolean first = true;
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        if (first)
                            first = false;
                        else
                            params.append("&");
                        params.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                        params.append("=");
                        params.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    }
                    OutputStream os = conn.getOutputStream();
                    os.write(params.toString().getBytes());
                    os.flush();
                    log.debug("  OutData: " + params.toString());
                }
            }
            // send the request
            resCode = conn.getResponseCode();

            if(resCode < 300) {
                Object obj = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                msg = "";
                log.debug("Output from Server .... ");
                while ((output = br.readLine()) != null) {
                    log.debug(output);
                    msg += output;
                }
            }
            resData = conn.getResponseMessage();
            log.info(String.format("  Response code: %d", resCode));

            log.debug("  Response Headers:");
            for (Map.Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
                log.debug("    - " + header.getKey() + "=" + header.getValue());
            }

            conn.disconnect();
            log.info("  MSG:  " + msg);
            log.info("  DATA: " + resData);
            return(new HttpQueryResult(resCode, msg, resData));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return(new HttpQueryResult(0, "", "MalformedURLException"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return(new HttpQueryResult(0, "", "IOException"));
        }

    }


}


