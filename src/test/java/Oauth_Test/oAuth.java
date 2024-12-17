package Oauth_Test;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import POJO.Api;
import POJO.GetCourse;
import POJO.WebAutomation;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class oAuth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when().log().all()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println("The response is   :   " + response);
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");

		// Retreive the Data based on the Access Token we get .....

		GetCourse GcObject = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class); // the output is
																										// setted as a
																										// Json format
																										// and ALalysed
																										// throw the
																										// Architecture
																										// of Classes (
																										// sub Classes
																										// )....
		System.out.println("The LinkedIN is :   " + GcObject.getLinkedIn());

		System.out.println("The Instructor is   " + GcObject.getInstructor());

		System.out.println(GcObject.getCourses().getApi().get(1).getCourseTitle());

		List<Api> apiCourses = GcObject.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {

			if (GcObject.getCourses().getApi().get(i).getCourseTitle().contains("SoapUI Webservices testing")) {
				System.out.println(
						"the price of the specified course is   " + GcObject.getCourses().getApi().get(i).getPrice());

			}
		}

		String[] coursesTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

// Print All courses present in the WebAutomation...

		List<WebAutomation> coursesName = GcObject.getCourses().getWebAutomation();

		ArrayList<String> a = new ArrayList<String>();

		for (int j = 0; j < coursesName.size(); j++) {

			a.add(coursesName.get(j).getCourseTitle());
		}
		
		List<String> b = Arrays.asList(coursesTitles);

		Assert.assertTrue(a.equals(b));

	}

}



