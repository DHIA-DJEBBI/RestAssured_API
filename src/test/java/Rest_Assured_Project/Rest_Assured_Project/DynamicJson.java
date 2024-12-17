package Rest_Assured_Project.Rest_Assured_Project;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

    @Test(dataProvider= "booksData")
    public void addBook(String data1 ,String data2) {
        // Set the base URI
        RestAssured.baseURI = "http://216.10.245.166";

        // Send POST request to add a book
        String response = given()
                .header("Content-Type", "application/json")
                .body(payload.AddBook( data1,data2 ))
        .when()
                .delete("Library/Addbook.php")
        .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        // Parse the response to extract the ID
        JsonPath js = ReUsableMethods.rawToJson(response); // Parse response using JsonPath
        String id = js.getString("ID");

        // Print the extracted ID
        System.out.println("Extracted Book ID: " + id);} 
    
        
        
  @DataProvider(name="booksData")
        
        
    public  Object[][]  getdata() {
	  //Array is a collection of elements 
      // Multidimentionnal array = Collecion of Arrays...
	  
        return 	new Object[][] { {"fdhrs","25566"} ,{"dqefgg","46585"} , {"sodnqo","50505"} };    	
   
    }
  
}


