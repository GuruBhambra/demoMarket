package GoogleMaps;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import Payload.RequestBodyGoogleMap;

public class GoogleMapAPI {

	public static void main(String[] args) throws IOException {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		
		String createResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(RequestBodyGoogleMap.googleMapPostBody()).when().post("maps/api/place/add/json").then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).extract().response().asString();
		
		//to add body from file path
		
//		String createResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
//				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\user\\OneDrive\\Documents\\addPlace.json")))).when().post("maps/api/place/add/json").then().assertThat()
//				.statusCode(200).body("scope", equalTo("APP")).extract().response().asString();

		JsonPath js = new JsonPath(createResponse);
		String placeID = js.getString("place_id");
		System.out.println("The placeID is : " + placeID);

		String updateResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(RequestBodyGoogleMap.googleMapPutBody(placeID)).when().put("maps/api/place/update/json").then()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath js1 = new JsonPath(updateResponse);
		String ActualMsg = js1.getString("msg");

		System.out.println("Hey the put request Output is : " + ActualMsg);

		String GetResponse = given().queryParam("key", "qaclick123").queryParam("place_id", placeID).when()
				.get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath js2 = new JsonPath(GetResponse);
		String myAddress = js2.getString("address");

		System.out.println("The Updated Address is : " + myAddress);

		String DeleteResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(RequestBodyGoogleMap.googleMapDeleteBody(placeID)).when().delete("maps/api/place/delete/json").then()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath delete = new JsonPath(DeleteResponse);
		String Status = delete.getString("status");

		System.out.println("The response of Delete for status is : " + Status);
	}

}
