package SpecBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import POJO.AddPlace;
import POJO.DeleteLocationAPI;
import POJO.Location;
import POJO.UpdatePlace;

public class GoogleMapPOJOSpecBuilder {

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

		RequestSpecification postRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		RequestSpecification createResponse = given().spec(postRequest).body(addPlace);

		Response response = createResponse.when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).extract().response();
		String ID = response.asString();

		// to add body from file path

		/*
		 * String createResponse = given().queryParam("key",
		 * "qaclick123").header("Content-Type", "application/json") .body(new
		 * String(Files.readAllBytes(Paths.get(
		 * "C:\\Users\\user\\OneDrive\\Documents\\addPlace.json")))).when().post(
		 * "maps/api/place/add/json").then().assertThat() .statusCode(200).body("scope",
		 * equalTo("APP")).extract().response().asString();
		 */

		JsonPath js = new JsonPath(ID);
		String placeID = js.getString("place_id");
		System.out.println("The placeID is : " + placeID);

//		b. UpdateGoogleMapAPI

		UpdatePlace updatePlace = new UpdatePlace();
		updatePlace.setAddress("JN Road  Opposite of SBI India");
		updatePlace.setKey("qaclick123");
		updatePlace.setPlace_id(placeID);

		RequestSpecification updateRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		RequestSpecification update = given().spec(updateRequest).body(updatePlace);

		Response AddressUpdate = update.when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
				.extract().response();
		String updatedAddress = AddressUpdate.asString();

		JsonPath js1 = new JsonPath(updatedAddress);
		String ActualMsg = js1.getString("msg");

		System.out.println("Hey the put request Output is : " + ActualMsg);

//		c. getAPI

		RequestSpecification getRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").addQueryParam("place_id", placeID).build();

		RequestSpecification getAddress = given().spec(getRequest);

		Response responses = getAddress.when().get("maps/api/place/get/json").then().assertThat().statusCode(200).
				contentType("application/json").extract().response();
		String GetResponse = responses.asString();

		JsonPath js2 = new JsonPath(GetResponse);
		String myAddress = js2.getString("address");

		System.out.println("The Updated Address is : " + myAddress);

//		d. DeleteGoogleMapAPI

		DeleteLocationAPI delete = new DeleteLocationAPI();
		delete.setPlace_id(placeID);

		RequestSpecification deleteRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		RequestSpecification Delete = given().spec(deleteRequest).body(delete);
		Response deleted = Delete.when().delete("maps/api/place/delete/json").then().assertThat().statusCode(200)
				.extract().response();
		String DeleteResponse = deleted.asString();

		JsonPath DeleteLocation = new JsonPath(DeleteResponse);
		String Status = DeleteLocation.getString("status");

		System.out.println("The response of Delete for status is : " + Status);
	}

}
