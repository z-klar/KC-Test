import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import javax.swing.*;
import java.util.Base64;

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
}
