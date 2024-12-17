package Rest_Assured_Project.Rest_Assured_Project;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import files.payload;

public class Basics {

    public static void main(String[] args) throws IOException {
        // Base URI for RestAssured
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Sending the POST request to add a place
        
        // Convert the Content of the file to a String -> content of Files -> converted to BYTE Data to String...
        // new String (Files.readAllBytes(Paths.get("D:\\Bureau\\RestAssured\\AddPlace.json"))) used in the Body
        
        String response = given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.Addplace())                  
        .when()
                .post("maps/api/place/add/json")
        .then()
                .log().all()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)")
                .extract()
                .response()
                .asString();

        System.out.println("POST Response: " + response);

        // Extracting the place_id from the response
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println("Extracted Place ID: " + placeId);

        // Updating the place with a new address
        String newAddress = "29, side layout, cohen 09";
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"place_id\": \"" + placeId + "\",\n" +
                        "  \"address\": \"" + newAddress + "\",\n" +
                        "  \"key\": \"qaclick123\"\n" +
                        "}")
        .when()
                .put("maps/api/place/update/json")
        .then()
                .log().all()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"));
    }
}


