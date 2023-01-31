package TicketingAPI;

import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class LoginAPI {
	
	@Test
	public void login() {
		
		String url = "https://cloud-api-gateway.altorumleren.com/oauth/api/v1/test?code=979023ec6433a3e84ea84386976faa57665e15eb&client_id=b3caf465-235a-4d15-a99d-ffbff5f24552&role=9e60e6a9-8d99-4e8e-83a1-b20ae39ef595&tenantId=null&customerId=null";
		String splitCode = url.split("code=")[1];
		String actualCode = splitCode.split("&client_id")[0];
		System.out.println(actualCode);
		
		String Token = given().urlEncodingEnabled(false).contentType("application/x-www-form-urlencoded; charset=UTF-8")
				.formParam("grant_type", "pkce").formParam("code", actualCode)
				.formParam("code_verifier", "example_string")
				.formParam("client_id", "b3caf465-235a-4d15-a99d-ffbff5f24552")
				.formParam("redirect_uri", "https://cloud-api-gateway.altorumleren.com/oauth/api/v1/test").when().log()
				.all().accept("application/json").post("https://cloud-api-gateway.altorumleren.com/oauth/api/v1/token")
				.asString();
		
		JsonPath myToken = new JsonPath(Token);
		String AccessToken = myToken.get("accessToken");
		System.out.println("The access token is " + AccessToken);
	}
}
//}https:cloud-api-gateway.altorumleren.com/oauth/api/v1/test?code=d0c12f0f620191b3434100f997b23cc04ee47d42&client_id=b3caf465-235a-4d15-a99d-ffbff5f24552&role=9e60e6a9-8d99-4e8e-83a1-b20ae39ef595&tenantId=null&customerId=null
