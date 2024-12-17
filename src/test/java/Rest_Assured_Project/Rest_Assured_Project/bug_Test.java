package Rest_Assured_Project.Rest_Assured_Project;

import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class bug_Test {

    public static void main(String[] args) {
        // Set Base URI
        RestAssured.baseURI = "https://dhiadjebbi1.atlassian.net/";

        // Authorization Token
        String token = "Basic ZGhpYWRqZWJiaTFAZ21haWwuY29tOkFUQVRUM3hGZkdGMHdoN3R1dHA1bWdhUlc3UXFBYlZBeHp2MEpTTXpCQ3VaOTBsdHdPUmhZZjBIcENfNFVfMXlfYmdKOGg5MG03OG5seUcxVkJmeDg1cVdoU2lUVjdkRmh5cVkzTmJRZkJNUjBWTUYxLVZTNl9JVGUwU3hjbXc1LU9OYkMwMFh1UzVSSlFlYzNNLXBmdHNSa0xHT0dKYmc1REYzLWppckV6bTcybFZBWHR5ZHF6TT0xRDU0Qjc0MA==";

        // Send POST request to create an issue in Jira
        String createIssueResponse = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "          \"key\": \"RSJIR\"\n" +
                        "       },\n" +
                        "       \"summary\": \"cheking if the File is added to the Bug ....\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "    }\n" +
                        "}")
                .log().all()
                .when()
                .post("rest/api/3/issue")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .response()
                .asString();

        // Parse the response to extract the Issue ID
        
        JsonPath js = new JsonPath(createIssueResponse);
        String issueId = js.getString("id");
        System.out.println("Created Issue ID: " + issueId);
        
        // Executing the post , and creating a bug in Jira along with an attachement .... 

        // Step 2: Attach a File to the Created Issue
        given()
                .header("X-Atlassian-Token", "no-check") // Required for attachments
                .header("Authorization", token)
                .pathParam("key", issueId)
                .multiPart("file", new File("D:\\Telechargement\\image.PNG")) // Attach the file
                .log().all()
                .when()
                .post("rest/api/3/issue/{key}/attachments")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);  // 200 indicates the attachment was added successfully...
        
        
        
    }
}

