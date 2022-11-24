package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
public class TC_VideoGamesAPI {
	@Test
	public void test_getAllVideoGames() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
		
		}
	@Test(priority=1)
	public void test_addNewVideoGame() {
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2022-11-23T16:16:58.177Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response response = 
		given().contentType("application/json").body(data)
		.when().post("http://localhost:8080/app/videogames")
		.then().statusCode(200).log().body()
		.extract().response();

		String jsonString = response.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}
	
	@Test(priority=2)
	public void test_getSingleVideoGame() {
		given()
		.when().get("http://localhost:8080/app/videogames/100")
		.then().statusCode(200)
		.log().body()
		.header("content-length", "233")
		.header("content-type","application/xml")
		.body("videoGame.id",equalTo("100"))
		.body("videoGame.name",equalTo("Spider-Man"));
		
	}
	
	@Test(priority = 3)
	public void test_updateVideoGame() {
		HashMap updatedata = new HashMap();
		updatedata.put("id", "100");
		updatedata.put("name", "Iron-Man");
		updatedata.put("releaseDate", "2022-11-22T16:16:58.177Z");
		updatedata.put("reviewScore", "4");
		updatedata.put("category", "Adventure");
		updatedata.put("rating", "Universal");
		
		Response response=
		given()
		.contentType("application/json")
		.body(updatedata)
		.when().put("http://localhost:8080/app/videogames/100")
		.then().statusCode(200)
		.log().body()
		.extract().response();
		
		String updateValidate=response.asString();
		Assert.assertEquals(updateValidate.contains("Iron-Man"), true);
		
	}
	
	@Test(priority=4)
	public void test_deleteVideoGame() throws InterruptedException {
		Response response=
		given()
		.when().delete("http://localhost:8080/app/videogames/100")
		.then().statusCode(200)
		.log().body()
		.extract().response();
		Thread.sleep(3000);
		String deleteValidate=response.asString();
		Assert.assertEquals(deleteValidate.contains("Record Deleted Successfully"), true);
		
	}

}
