package GoogleMaps;

import io.restassured.path.json.JsonPath;

import Payload.RequestBodyGoogleMap;

public class ComplexJsonParse {

	public static void main(String[] args) {

		JsonPath Courses = new JsonPath(RequestBodyGoogleMap.coursePriceBody());
		// Print No of courses returned by API
		int numberOfCourses = Courses.getInt("courses.size()");
		System.out.println("The Total Number of courses are : " + numberOfCourses);

		// Print Purchase Amount
		int purchaseAmount = Courses.getInt("dashboard.purchaseAmount");
		System.out.println("The total Purchase amount of " + numberOfCourses + " courses is : " + purchaseAmount);

		// Print Title of the first course
		String firstTitle = Courses.getString("courses[0].title");
		System.out.println("The title of the first course is : " + firstTitle);

		// Print All course titles and their respective Prices
		String AllCoursesName;
		int AllPrices = 0;
		int Sum = 0;

		for (int i = 0; i < numberOfCourses; i++) {
			AllCoursesName = Courses.getString("courses[" + i + "].title");
			AllPrices = Courses.getInt("courses[" + i + "].price");
			System.out.println("The title of the course is " + AllCoursesName + " and the price is : " + AllPrices);

			// Print no of copies sold by RPA Course
			if (AllCoursesName.equalsIgnoreCase("RPA")) {
				int NumberOfCopies = Courses.getInt("courses[" + i + "].copies");
				System.out.println("The number of copies of RPA is :" + NumberOfCopies);
			}
			int NumberofCopies = Courses.getInt("courses[" + i + "].copies");

			// Verify if Sum of all Course prices matches with Purchase Amount
			int TotalAmount = AllPrices * NumberofCopies;
			Sum = Sum + TotalAmount;
			if (Sum == purchaseAmount) {
				System.out.println("The sum of the courses is " + Sum);
			}
		}

	}

}
