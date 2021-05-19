package starter.math;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
public class TwitchTest {
    private static String requestBody = "{\n" +
            "  \"title\": \"foo\",\n" +
            "  \"body\": \"bar\",\n" +
            "  \"userId\": \"1\" \n}";

    @Test
    public void getRequestWithQueryParam() {
        Response response = given()
                .contentType(ContentType.JSON)
                .param("postId", "2")
                .when()
                .get("https://jsonplaceholder.typicode.com/comments")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals("Meghan_Littel@rene.us", response.jsonPath().getString("email[3]"));
    }
    @Test
    public void getRequest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals("qui est esse", response.jsonPath().getString("title[1]"));
    }
    @Test
    public void postRequest() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .extract().response();

        assertEquals(201, response.statusCode());
        assertEquals("foo", response.jsonPath().getString("title"));
        assertEquals("bar", response.jsonPath().getString("body"));
        assertEquals("1", response.jsonPath().getString("userId"));
        assertEquals("101", response.jsonPath().getString("id"));
    }
    @Test
    public void putRequest() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .put("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals("foo", response.jsonPath().getString("title"));
        assertEquals("baz", response.jsonPath().getString("body"));
        assertEquals("1", response.jsonPath().getString("userId"));
        assertEquals("1", response.jsonPath().getString("id"));
    }
    @Test
    public void deleteRequest() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
    }

/*    @Test
    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {

        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }
    @Test
    public void test_ResponseHeaderData_ShouldBeCorrect() {

        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                header("Content-Length",equalTo("4567"));
    }*/
}