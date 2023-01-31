package TicketingAPI;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import Payload.TicketingLoginBody;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HeaderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
//import io.restassured.RestAssured;
//import io.restassured.config.EncoderConfig;
//import io.restassured.config.RestAssuredConfig;
//import io.restassured.http.ContentType;
//import io.restassured.path.json.JsonPath;
import io.restassured.path.json.JsonPath;

public class LoationLoginAPI {

	@Test

	public void Location() {
		String EmailId = "arpith.verma@yopmail.com";

		String Official_Name = "Mark Jeffer";
		String Company_Name = "Jeffer Groups";
		String Email_Id = "jeffer@yopmail.com";
		String Phone_Number = "9988776655";
		String Billing_Address = "Lucknow, Uttar Pradesh";
		String Shipping_Address = "SultanPur Uttar Pradesh";
		String Depart_Ment = "Income Tax";
		String Web_Site = "jeffer@co.in";
		String Location = "Uttar Pradesh";

		String CustomerName = EmailId.split(".verma")[0];

		// a. Login In to get Scope and Role
		String login = given().header("Content-Type", "application/json").header("X-Requested-With", "randomUUID")
				.body(TicketingLoginBody.LoginBody(EmailId)).when()
				.post("https://cloud-api-gateway.altorumleren.com/oauth/api/v1/test/login").then().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath LoginIn = new JsonPath(login);
		String myScope = LoginIn.getString("scope");
		String ClientID = LoginIn.getString("loginData.client_id");
		System.out.println("The scope for " + CustomerName + "is " + myScope);

		// b. To get AuthorizationCode

		String Auth = given().header("Content-Type", "application/json").header("X-Requested-With", "randomUUID")
				.body(TicketingLoginBody.AuthorizationBody(myScope)).when()
				.post("https://cloud-api-gateway.altorumleren.com/oauth/api/v1/test/authorization").then().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath myAuth = new JsonPath(Auth);
		String AuthCode = myAuth.getString("code.authorizationCode");
		System.out.println("The Authorization Code is " + AuthCode);

		// c. AccessToken

		String getToken = given().header("X-Requested-With", "randomUUID")
				.contentType("application/x-www-form-urlencoded; charset=utf-8")
				.formParam("client_id", "b3caf465-235a-4d15-a99d-ffbff5f24552")
				.formParam("redirect_uri", "https://cloud-api-gateway.altorumleren.com/oauth/api/v1/test")
				.formParam("grant_type", "pkce").formParam("code_verifier", "example_string")
				.formParam("code_challenge", "m_tVqEBmF_8-Z2fsXSf8a1aCw6ecQV724IS_fQUCc-Y")
				.formParam("code_challenge_method", "S256").formParam("code", AuthCode).when()
				.post("https://cloud-api-gateway.altorumleren.com/oauth/api/v1/test/token").then().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath token = new JsonPath(getToken);
		String Token = token.getString("accessToken");
		System.out.println("The Access Token is " + Token);

		// d. Create Customer

		String CreateCustomer = given()
				.config(RestAssuredConfig.config().encoderConfig(
						EncoderConfig.encoderConfig().encodeContentTypeAs("application/json", ContentType.TEXT)))
				.body(TicketingLoginBody.CustomerBody())
				.headers("Authorization", "Bearer " + Token, "Content-Type", "application/json").when()
				.post("https://cloud-api-gateway.altorumleren.com/crm/api/v1/b5479be0-3157-47fd-bedb-2074916be377/customers")
				.then().assertThat().statusCode(201).extract().response().asString();

		JsonPath postCustomer = new JsonPath(CreateCustomer);
		String customerID = postCustomer.getString("customerId");
		System.out.println("The customer ID is " + customerID);

		// e. Get Customer Id
		String getCustomer = given().queryParam("_loadrelation", "true").queryParam("value", "1")
				.config(RestAssuredConfig.config().encoderConfig(
						EncoderConfig.encoderConfig().encodeContentTypeAs("application/json", ContentType.TEXT)))
				.body(TicketingLoginBody.CustomerBody())
				.headers("Authorization", "Bearer " + Token, "Content-Type", "application/json", "X-Requested-With",
						"randomUUID")
				.when()
				.get("https://cloud-api-gateway.altorumleren.com/crm/api/v1/b5479be0-3157-47fd-bedb-2074916be377/customer/"
						+ customerID)
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath cust = new JsonPath(getCustomer);
		String Customer_Name = cust.getString("officialName");
		System.out.println("The Name of the customer is " + Customer_Name);

	}
}
