package Rest_Assured_Project.Rest_Assured_Project;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import files.payload;
import io.restassured.path.json.JsonPath;

public class sumValidation {

    @Test
    public void SumofCourses() {
        // Parse JSON response
        JsonPath js = new JsonPath(payload.coursePrice());

        // Extract details
        int count = js.getInt("courses.size()");
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        int totalObtained = 0;
        //int total=0;
        // Calculate sum of course prices
        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price"); 
            int copie =js.getInt("courses["+i+"].copies");
            //total = price*copie;
            totalObtained += price*copie;
        }

        // Log the values
        System.out.println("Expected Total Amount: " + totalAmount);
        System.out.println("Calculated Total Amount: " + totalObtained);

        // Assert the calculated sum matches the total amount
       assertEquals(totalObtained, totalAmount, "The calculated sum does not match the purchase amount.");
    }
}

