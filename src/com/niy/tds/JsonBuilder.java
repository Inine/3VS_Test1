package com.niy.tds;
/*import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;*/
import java.util.Map;

public class JsonBuilder {
    /*JsonObject jsonObject;
    public JsonBuilder(Map<String, String> args){
        JsonArrayBuilder rootArray = Json.createArrayBuilder();
        for (String key: args.keySet()){
            rootArray.add(Json.createObjectBuilder()
                .add("uid", key)
                .add("name", args.get(key))
            );
        }
        jsonObject = Json.createObjectBuilder().add("response", rootArray).build();
    }*/

    StringBuilder jsonObject = new StringBuilder();
    private final String jsonStartBracket = "{\"response\": [";
    private final String endBracket = "]}";

    public JsonBuilder(Map<String, String> args){
        jsonObject.append(jsonStartBracket);
        for (String key: args.keySet()){
            jsonObject.append("{\"uid\": \"");
            jsonObject.append(key);
            jsonObject.append("\",");
            jsonObject.append("\"name\": \"");
            jsonObject.append(args.get(key));
            jsonObject.append("\"}");
            jsonObject.append(",");
        }
        jsonObject.deleteCharAt(jsonObject.lastIndexOf(","));
        jsonObject.append(endBracket);
    }

    public String getJsonObject(){
        return jsonObject.toString();
    }
}
