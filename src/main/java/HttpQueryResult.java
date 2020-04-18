

public class HttpQueryResult {

    public int ResCode;
    public String ResMsg;
    public String ResText;

    /*########################################################################

     ########################################################################*/
    public HttpQueryResult(int code, String msg, String text) {
        ResCode = code;
        ResMsg = msg;
        ResText = text;
    }


}
