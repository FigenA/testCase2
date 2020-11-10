package com.book.api.tests;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import org.json.simple.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;


public class BookApiTest {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
	}

	@Test
	public void checkEmptyList() {
		given().when().get("/api/books/").then().assertThat().body("books.id", hasSize(0));
	}

	@Test
	public void postBook() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("author", "Test Author");
			requestParams.put("title", "Title");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(200, response.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkHttpStatus200() {

		given().when().get("/api/books/").then().assertThat().statusCode(200);
	}

	@Test
	public void checkHttpStatus404() {

		given().when().get("/api/books/23").then().assertThat().statusCode(404);
	}

	@Test
	public void postBookWithoutAuthor() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("title", "Test Title");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(400, response.getStatusCode());
			AssertJUnit.assertTrue(response.getBody().asString().contains("Field author is required"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void postBookWithoutTitle() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("author", "Test");



			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(400, response.getStatusCode());
			AssertJUnit.assertTrue(response.getBody().asString().contains("Field title is required"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void postBookEmptyAuthor() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("author", "");
			requestParams.put("title", "Test Title");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(400, response.getStatusCode());
			AssertJUnit.assertTrue(response.getBody().asString().contains("Field author cannot be empty."));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void postBookEmptyTitle() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("author", "Test");
			requestParams.put("title", "");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(400, response.getStatusCode());
			AssertJUnit.assertTrue(response.getBody().asString().contains("Field title cannot be empty."));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postBookWithId() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("id", 3);
			requestParams.put("author", "Test");
			requestParams.put("title", "Test Title");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(400, response.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postBookAndControl() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("author", "Test");
			requestParams.put("title", "Test Title");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(200, response.getStatusCode());

			int bookId = 2;
			given().when().get("/api/books/" + bookId + "/").then().assertThat().body("books.author", equalTo("Test")).body("books.title", equalTo(
					"Test Title"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postDublicateBook() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("author", "Test");
			requestParams.put("title", "Test Title");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(400, response.getStatusCode());
			AssertJUnit.assertTrue(response.getBody().asString().contains("Another book with similar title and author already exists"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void postBookWithSameAuthor() {
		try {
			JSONObject requestParams = new JSONObject();
			requestParams.put("author", "Test");
			requestParams.put("title", "Book Title");


			Response response = RestAssured.given()
					.contentType(ContentType.JSON)
					.body(requestParams)
					.post("/api/books/");

			AssertJUnit.assertEquals(200, response.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
