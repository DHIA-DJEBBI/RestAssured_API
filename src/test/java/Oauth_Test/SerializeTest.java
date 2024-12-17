package Oauth_Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.location;
public class SerializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com"; 
		 
	AddPlace p= new AddPlace(); 
	p.setAccuracy(50);
	p.setAddress("29, side layout, cohen 09");
	p.setLanguage("French-IN");
	p.setWebsite("http://google.com");
	p.setName("Frontline house");
	p.setPhone_number("(+91) 983 893 3937");
	List<String> myList = new ArrayList<String>(); 
	myList.add("shoe park"); 
	myList.add("shop"); 
	p.setTypes(myList);
	
	
	location l= new location(); 
	l.setLat(-38.383494);
	l.setLng(33.427362);
	
	p.setLocation(l); 
	
	
		
		String response  = given().queryParam("key", "qaclick123")
		.body(p)
		.when()
		.post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		// Output Query ...  
		
System.out.println("the response is   "+response);








	}

}
