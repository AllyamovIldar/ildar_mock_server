//import io.restassured.RestAssured.*;
//import io.restassured.matcher.RestAssuredMatchers.*;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import junit.framework.TestSuite;
//import net.minidev.json.JSONObject;
//import org.apache.commons.io.FileUtils;
//import org.apache.http.HttpStatus;
//import org.apache.tools.ant.types.resources.AbstractClasspathResource;
//import org.hamcrest.Matchers.*;
//import io.restassured.module.jsv.JsonSchemaValidator.*;
//import io.restassured.matcher.RestAssuredMatchers;
//import org.springframework.core.io.ClassPathResource;
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.channels.Channel;
//import java.nio.channels.Channels;
//import java.nio.channels.ReadableByteChannel;
//import java.io.BufferedInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.lang.Object;
//import org.junit.Assert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import io.restassured.http.ContentType;
import io.restassured.RestAssured;
import org.junit.Test;

public class TestRestInTestJava {
    @Test
    public void test_status_get_employee() {
        RestAssured.when().get("http://localhost:8080/mock/get_employee")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void test_status_delete_employee() {
        RestAssured.when().delete("http://localhost:8080/mock/delete_employee")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void test_status_create_employee(){
        given()
                .body("{\n" +
                        "    \"fio\": \"Аллямов Ильдар Зиннятуллаевич\",\n" +
                        "    \"position\": \"Специалист по тестированию\"\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:8080/mock/create_employee")
                .then()
                .statusCode(201);

    }
    @Test
    public void test_validate_file_html(){
        when()
                .get("http://localhost:8080/static/index.html")
                .then()
                .assertThat()
                .contentType(ContentType.HTML)
                .extract()
                .response();
    }

    @Test
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

    @Test
    public void test_validate_file_txt(){
        when()
                .get("http://localhost:8080/static/text.txt")
                .then()
                .assertThat()
                .contentType(ContentType.TEXT)
                .extract()
                .response();
    }
    @Test
    public void test_validate_post_json() {
        given()
                .body("{\n" +
                        "    \"fio\": \"Аллямов Ильдар Зиннятуллаевич\",\n" +
                        "    \"position\": \"Специалист по тестированию\"\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:8080/mock/create_employee")
                .then()
                .assertThat()
                .statusCode(201)
                .body("fio", equalTo("Аллямов Ильдар Зиннятуллаевич"))
                .body("position", equalTo("Специалист по тестированию"))
                .body("number", notNullValue());

    }
    @Test
    public void test_validate_get_json() {
        given()
                .when()
                .get("http://localhost:8080/mock/get_employee")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo("101"))
                .body("fio", equalTo("Иванов Иван Иванович"))
                .body("position", equalTo("Специалист по тестированию"))
                .body("number", equalTo("1024"));
    }
    @Test
    public void test_validate_delete_json() {
        int delId = 1;
        given()
                .contentType("application/json")
                .with()
                .queryParam("id", delId)
                .when()
                .delete("http://localhost:8080/mock/delete_employee?id=" + String.valueOf(delId))
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(String.valueOf(delId)))
                .body("status", equalTo("DELETED"));
    }

    public static void main(String[] args) {
    }
}
