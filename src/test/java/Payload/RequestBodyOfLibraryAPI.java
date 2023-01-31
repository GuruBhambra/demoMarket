package Payload;

public class RequestBodyOfLibraryAPI {

	
	public static String AddBook(String ISBN, String ASILE, String Book) {
		
		return  "{\r\n"
				+ "\"name\":\""+Book+"\",\r\n"
				+ "\"isbn\":\""+ISBN+"\",\r\n"
				+ "\"aisle\":\""+ASILE+"\",\r\n"
				+ "\"author\":\"SS Ratan\"\r\n"
				+ "}";
	}
}
