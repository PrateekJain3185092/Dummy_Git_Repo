package ApiTraining.ApiTraining;

import java.util.ArrayList;
import java.util.Random;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;


public class Commands 
{
	String userId=null;
	Random random = new Random();
	
	String username = "xyz";
	String domain ="@xyz.com";
	int randomnumber = random.nextInt(10000);
	String dynamicEmail = username + randomnumber + domain;
	
	@Test (priority=10)
	public void UsingGet()
	{
		RestAssured.baseURI = "https://reqres.in";
		Response res = RestAssured.given().header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization","Bearer 4275e0158274c52983ce80a13234cdf89eff29daee0d46b6cdb82bd6a39d2b6f")
				.queryParam("page","2")
				.when().get("api/users").then().extract().response();
		res.prettyPeek();
		
		 ArrayList<Integer> id = res.jsonPath().get("data.id");
		 ArrayList<String> firstName = res.jsonPath().get("data.first_name");
		 
		 for(int i=0;i<id.size();i++)
		 {
			 if(id.get(i) == 8 && firstName.get(i).equals("Lindsay"))
			 {
				 System.out.println("Yes, you got it");
			 }
		}
	}


	@Test(priority=12)
	public void UsingPost()
	{ 
		
		RestAssured.baseURI = "https://gorest.co.in/";
		Response res = RestAssured.given().header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization","Bearer 4275e0158274c52983ce80a13234cdf89eff29daee0d46b6cdb82bd6a39d2b6f")
				.body("{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\",\"email\": \""+dynamicEmail+"\" ,\"status\":\"active\"}")
				.when().post("public/v2/users").then().extract().response();
		userId = res.jsonPath().getString("id");
		Assert.assertEquals(201,res.statusCode());
		Assert.assertEquals("Tenali Ramakrishna", res.jsonPath().getString("name"));
		res.prettyPeek();
	}
	
	
	@Test(priority=20)
	public void UsingPut()
	{
		RestAssured.baseURI = "https://gorest.co.in/";
		Response res = RestAssured.given().header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization","Bearer 4275e0158274c52983ce80a13234cdf89eff29daee0d46b6cdb82bd6a39d2b6f")
				.body("{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\", \"email\":\""+dynamicEmail+"\", \"status\":\"active\"}")
				.when().put("public/v2/users/"+userId);
		Assert.assertEquals(200,res.statusCode());
		res.prettyPeek();
	}
	
	
	@Test(priority=50)
	public void UsingDelete()
	{
		RestAssured.baseURI = "https://gorest.co.in/";
		Response res = RestAssured.given().header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization","Bearer 4275e0158274c52983ce80a13234cdf89eff29daee0d46b6cdb82bd6a39d2b6f")
				.when().delete("public/v2/users/"+userId);
		Assert.assertEquals(204,res.statusCode());
		res.prettyPeek();
	}
}
