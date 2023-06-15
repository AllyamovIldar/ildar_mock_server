import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import junit.framework.TestCase;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestRestInTestJava extends TestCase {
    @org.testng.annotations.Test
    public void test_status_get_employee() {
        RestAssured.when().get("http://localhost:8080/mock/get_employee")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @org.testng.annotations.Test
    public void test_status_delete_employee() {
        RestAssured.when().delete("http://localhost:8080/mock/delete_employee")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @org.testng.annotations.Test
    public void test_status_create_employee() {
        given()
                .body("{\n" +
                        "    \"fio\": \"Alliamov Ildar Zinniatullaevich\",\n" +
                        "    \"position\": \"QA Engineer\"\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:8080/mock/create_employee")
                .then()
                .statusCode(201);

    }
    @org.testng.annotations.Test
    public void test_validate_file_html() {
        when()
                .get("http://localhost:8080/static/index.html")
                .then()
                .assertThat()
                .contentType(ContentType.HTML)
                .extract()
                .response();
    }
    @org.testng.annotations.Test
    public void test_validate_file_jpg() {
        when()
                .get("http://localhost:8080/static/image.jpg")
                .then()
                .assertThat()
                .contentType("image/jpeg")
                .statusCode(200)
                .extract()
                .response();
    }
    @org.testng.annotations.Test
    public void test_validate_file_txt() {
        when()
                .get("http://localhost:8080/static/text.txt")
                .then()
                .assertThat()
                .contentType(ContentType.TEXT)
                .extract()
                .response();
    }
    @org.testng.annotations.Test
    public void test_validate_post_json() {
        given()
                .body("{\n" +
                        "    \"fio\": \"Alliamov Ildar Zinniatullaevich\",\n" +
                        "    \"position\": \"QA Engineer\"\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:8080/mock/create_employee")
                .then()
                .assertThat()
                .statusCode(201)
                .body("fio", equalTo("Alliamov Ildar Zinniatullaevich"))
                .body("position", equalTo("QA Engineer"))
                .body("number", notNullValue());

    }
    @org.testng.annotations.Test
    public void test_validate_get_json() {
        given()
                .when()
                .get("http://localhost:8080/mock/get_employee")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo("101"))
                .body("fio", equalTo("Ivanov Ivan Ivanovich"))
                .body("position", equalTo("QA engineer"))
                .body("number", equalTo("1024"));
    }

    @org.testng.annotations.Test
    public void test_validate_delete_json() {
        int delId = 1;
        given()
                .contentType("application/json")
                .with()
                .queryParam("id", delId)
                .when()
                .delete("http://localhost:8080/mock/delete_employee?id=" + delId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(String.valueOf(delId)))
                .body("status", equalTo("DELETED"));
    }

    public static void main(String[] args) {
    }
}
