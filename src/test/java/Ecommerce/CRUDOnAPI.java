package Ecommerce;

import org.testng.annotations.Test;

import POJO.LoginPOJO;
import POJO.LoginResponsePojo;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//import io.restassured.http.ContentType;

public class CRUDOnAPI {

	@Test
	public void LoginAPI() {

		RequestSpecification login = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginPOJO loginPojo = new LoginPOJO();
		loginPojo.setUserEmail("rahulshetty@gmail.com");
		loginPojo.setUserPassword("Iamking@000");

		RequestSpecification reqLogin = given().spec(login).body(loginPojo);

		LoginResponsePojo responseLogin = reqLogin.when().post("/api/ecom/auth/login").then().extract().response()
				.as(LoginResponsePojo.class);
		System.out.println(responseLogin.getToken());
		String Token = responseLogin.getToken();
		String UserID = responseLogin.getUserId();
		
		
//		RequestSpecification product = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", Token).build();
//	
//		given().spec(product).param("productName", "Laptop")
//		.param("productAddedBy", UserID).param("productCategory", "fashion")
//		.param("productSubCategory", "shirts").param("productPrice", "11500")
//		.param("productDescription", "Lenova").param("productFor", "men").multiPart("")
	
	
	
	
	}

}
