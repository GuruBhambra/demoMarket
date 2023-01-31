package GoogleMapsSpec;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.DeleteLocationAPI;
import POJO.Location;
import POJO.UpdatePlace;
import Payload.RequestBodyGoogleMap;

public class GoogleMapPOJOAPI   {

	public static void main(String[] args) throws IOException {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		AddPlace addPlace = new AddPlace();
		
		addPlace.setAccuracy(23);
		addPlace.setAddress("Pink City Jaipur");
		addPlace.setLanguage("English");
		addPlace.setName("Mark Mathew");
		addPlace.setPhone_number("9876543210");
		addPlace.setWebsite("https://rahulshettyacademy.com");
		
		List<String> myList = new ArrayList<String>();
		myList.add("House");
		myList.add("Home");
		addPlace.setTypes(myList);
		
		Location loc = new Location();
		loc.setLat(23.23424);
		loc.setLng(54.12345);
		addPlace.setLocation(loc);
		
		String createResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(addPlace).when().post("maps/api/place/add/json").then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).extract().response().asString();
		
		//to add body from file path
		
//		String createResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
//				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\user\\OneDrive\\Documents\\addPlace.json")))).when().post("maps/api/place/add/json").then().assertThat()
//				.statusCode(200).body("scope", equalTo("APP")).extract().response().asString();

		JsonPath js = new JsonPath(createResponse);
		String placeID = js.getString("place_id");
		System.out.println("The placeID is : " + placeID);
		
//		b. UpdateGoogleMapAPI
		
		
		UpdatePlace updatePlace = new UpdatePlace();
		updatePlace.setAddress("JN Road,Ward No 10, Rangapara, Assam");
		updatePlace.setKey("qaclick123");
		updatePlace.setPlace_id(placeID);

		String updateResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(updatePlace).when().put("maps/api/place/update/json").then()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath js1 = new JsonPath(updateResponse);
		String ActualMsg = js1.getString("msg");

		System.out.println("Hey the put request Output is : " + ActualMsg);

//		c. getAPI
		
		String GetResponse = given().queryParam("key", "qaclick123").queryParam("place_id", placeID).when()
				.get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath js2 = new JsonPath(GetResponse);
		String myAddress = js2.getString("address");

		System.out.println("The Updated Address is : " + myAddress);
		
//		d. DeleteGoogleMapAPI
		
		DeleteLocationAPI delete = new DeleteLocationAPI();
		delete.setPlace_id(placeID);

		String DeleteResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(delete).when().delete("maps/api/place/delete/json").then()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath Delete = new JsonPath(DeleteResponse);
		String Status = Delete.getString("status");

		System.out.println("The response of Delete for status is : " + Status);
	}

}
