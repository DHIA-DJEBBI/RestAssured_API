package Oauth_Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import POJO.LoginRequest;
import POJO.LoginResponse;
import POJO.OrderDetails;
import POJO.Orders;
public class EcommerceApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
		.build();
		LoginRequest lgRequest =new LoginRequest(); 
		lgRequest.setUserEmail("dhiadjebbi1@gmail.com"); 
		lgRequest.setuserPassword("Djebbi123");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(lgRequest);
		
		LoginResponse LoginResponse = reqLogin.when().post("/api/ecom/auth/login").then()
				.extract().response().as(LoginResponse.class);
		
		System.out.println(LoginResponse.getToken());
		String MyToken = LoginResponse.getToken(); 
		System.out.println(LoginResponse.getUserId());

		RequestSpecification AddProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization",MyToken)
				.build();
		
        RequestSpecification productAdd = given().log().all().spec(AddProduct)
                .param("productName", "Djebbi's Product")
                .param("productAddedBy", LoginResponse.getUserId())
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "Addidas Originals")
                .param("productFor", "Men and Women")
                .multiPart("productImage", new File("D:\\Bureau\\Capture.png"));
	
	String ResponseAddProdct = productAdd.when().post("/api/ecom/product/add-product").then().log().all().extract().asString(); 
	
	JsonPath jp = new JsonPath(ResponseAddProdct); 
	String productID = jp.get("productId");
	
	// Create Order for the product ... 
	
	RequestSpecification createOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("Authorization",MyToken).setContentType(ContentType.JSON)
			.build();
	
	OrderDetails details = new OrderDetails(); 
    details.setCountry("tunisia"); 
    details.setproductOrderedId(productID);

    List<OrderDetails> orderdetailsList = new ArrayList<OrderDetails>(); 
 

    orderdetailsList.add(details); 

	Orders order = new Orders(); 
	order.setOrders(orderdetailsList); 
	
	RequestSpecification createOrderReq = given().log().all().spec(createOrder).body(order); 
	
	String ResponseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
	
	System.out.println("the response is  "+ResponseAddOrder); 
	

	// Delete the Product .... 
	 
	
	RequestSpecification deleteProduct  = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("Authorization",MyToken).setContentType(ContentType.JSON)
			.build();
	
	
	// Use the "relaxedHTTPSValidation " for the Http certifications.... 
	
	RequestSpecification deleteProdReq = given().relaxedHTTPSValidation().log().all().spec(deleteProduct).pathParam("productId",productID);
	
	 String  deleteMsg = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response()
			 .asString();
	 
	 
	System.out.println("the request status is ==>>>"+deleteMsg);
	


	
	
	
	
	}

}
