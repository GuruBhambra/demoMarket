package LibraryAPI;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Payload.RequestBodyOfLibraryAPI;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Books {

	@Test(dataProvider = "BookIDs")
	// public static void main(String[] args) {

	public void addBook(String Isbn, String Asile, String bookName) {

		//String bookName = "Theory Of Machine";
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String getBookID = given().header("Content-Type", "application/json")
				.body(RequestBodyOfLibraryAPI.AddBook(Isbn, Asile, bookName)).when().post("Library/Addbook.php").then()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath book = new JsonPath(getBookID);
		String BookID = book.get("ID");
		System.out.println(BookID);
		

		String getBookName = given().queryParam("ID", BookID).when().get("Library/GetBook.php").then().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath bookN = new JsonPath(getBookName);
		String bookname = bookN.getString("book_name");
		System.out.println(bookname);

		
	}
	
	@DataProvider(name = "BookIDs")
	
	public Object[][] dataSet() {
		
		return new Object[][] {{"SOM", "100", "Solid Mechanics"},{"TOM", "455", "Theory Of Machine"},{"DOM", "296", "Design Of Machine"}};
	}

}
