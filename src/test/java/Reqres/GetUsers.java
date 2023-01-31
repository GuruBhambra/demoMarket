package Reqres;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.annotations.Test;

import POJO.Data;
import POJO.UserList;

public class GetUsers {

	@Test
	public void Users() {
		RestAssured.baseURI = "https://reqres.in";

		UserList users = given().queryParam("page", "2").expect().defaultParser(Parser.JSON).when().get("api/users")
				.as(UserList.class);

//		System.out.println(users.getTotal());
//		System.out.println(users.getPer_page());
		//System.out.println(users.getData().get(1).getFirst_name());

		List<Data> myUser = users.getData();
		for(int i =0; i< myUser.size();i++) {
			
//			if(myUser.get(i).getFirst_name().equalsIgnoreCase("Tobias")) {
				
				System.out.println(myUser.get(i).getFirst_name());
//			}
		}

		System.out.println(users.getSupport().getUrl());
	}
}