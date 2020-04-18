import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

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


}
