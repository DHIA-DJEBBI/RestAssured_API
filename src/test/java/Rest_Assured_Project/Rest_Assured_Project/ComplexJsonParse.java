package Rest_Assured_Project.Rest_Assured_Project;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		//Number of courses in an Array ( Json file )....
		
		JsonPath js = new JsonPath(payload.coursePrice()); 
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("the Total amount is :  "+totalAmount);
		
		// Print the Title of the First Course...
		String firstTitle=  js.getString("courses[0].title");
		System.out.println(firstTitle);
		
		String LastTitle =js.getString("courses[2].title"); 
		System.out.println(LastTitle);
		// Print All courses titles ... 
		
		for ( int i=0; i<count;i++) {
			
			String courseTitle =  js.get("courses["+i+"].title");
			
			System.out.println(js.getInt("courses["+i+"].price"));
			
			System.out.println(courseTitle);
			
		}
		int sum =0;
for ( int i=0; i<count;i++) {
			
			String courseTitle =  js.get("courses["+i+"].title");
			if(courseTitle.equalsIgnoreCase("RPA")) {
				// Copie sold...
				int copies = js.get("courses["+i+"].copies");
				sum += copies;
				System.out.println(copies);
				break;
				
			}

		}
		
		
		
		
		
	}
	
}
