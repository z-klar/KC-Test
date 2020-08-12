import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import javax.swing.*;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JsonTools {


    /*------------------------------------------------------------------

     ------------------------------------------------------------------*/
    public static String ConvertJsonToString(Object obj) {

        String jsonString = obj.toString();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement json = gson.fromJson(jsonString,JsonElement.class);
        String jsonInString = gson.toJson(json);
        return(jsonInString);

    }

    public static void Convert64ToBase(JTextArea src, JTextArea dst) {
        String s64 = src.getText();
        try {
            String encodedString = new String(Base64.getDecoder().decode(s64));
            dst.setText(encodedString);
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void ConvertBaseTo64(JTextArea src, JTextArea dst) {
        String sopen = src.getText();
        try {
        String s64 =  Base64.getEncoder().encodeToString(sopen.getBytes());
        dst.setText(s64);
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void Convert64YoBaseMulti(JTextArea src, JTextArea dst) {
        String s64 = src.getText();
        String[] result = s64.split("\\.");
        String sout = "";
        for (int x = 0; x < result.length; x++) {
            try {
                String ss = new String(Base64.getDecoder().decode(result[x]));
                sout += ss;
                sout += "\n\n";
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null,
                        String.format("Error in section %d:\n%s",x, ex.getMessage()));
            }
        }
        dst.setText(sout);
    }

    public static void ProcessJwt(JTextArea src, JTextArea dst) {
        String stoken = src.getText();

        try {
            /*
            Algorithm algorithm = Algorithm.HMAC256("e63164b7-1a2a-4dfc-933d-c100e82a0ada");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance

            DecodedJWT jwt = verifier.verify(stoken);
             */
            DecodedJWT jwt = JWT.decode(stoken);

            String out = "";
            String subj;
            subj = "Algorithm:  " + jwt.getAlgorithm();
            out += subj;  out += "\n";
            subj = "Subject:    " + jwt.getSubject();
            out += subj;  out += "\n";
            subj = "Issuer:     " + jwt.getIssuer();
            out += subj;  out += "\n";
            subj = "Expires:    " + jwt.getExpiresAt().toString();
            out += subj;  out += "\n";
            subj = "Issued at:  " + jwt.getIssuedAt().toString();
            out += subj;  out += "\n";

            Map<String, Claim> claims = jwt.getClaims();    //Key is the Claim name
            Claim claim = claims.get("preferred_username");
            out += "UserName:   " + claim.asString() + "\n";

            dst.setText(out);
        } catch (Exception ex){
            //Invalid token
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

}
