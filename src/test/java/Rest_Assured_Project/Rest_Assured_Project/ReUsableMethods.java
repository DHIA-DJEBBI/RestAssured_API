package Rest_Assured_Project.Rest_Assured_Project;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {

    public static JsonPath rawToJson(String response) {
        JsonPath js1 = new JsonPath(response);
        return js1; // Return the JsonPath object within the method body
    }
}