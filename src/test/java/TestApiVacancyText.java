import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

public class TestApiVacancyText {
    private static final String AUTH_TOKEN = System.getProperty("hh_auth");
    private static ResponseSpecification checkStatusCodeAndContentType;

    @BeforeClass
    public static void init() {
        RestAssured.requestSpecification =
                new RequestSpecBuilder()
                        .setBaseUri("https://api.hh.ru/")
                        .setBasePath("vacancies")
                        .setAuth(oauth2(AUTH_TOKEN))
                        .build();

        checkStatusCodeAndContentType =
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build();
    }

    @Test
    public void testOAuth2Access_ShouldBeGivenAccess() {
        given().auth().oauth2(AUTH_TOKEN)
                .when()
                    .get("https://api.hh.ru/me")
                .then()
                    .assertThat()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testPutRequest() {
        given()
                .when()
                    .put()
                .then()
                    .statusCode(405);
    }

    @Test
    public void testDeleteRequest() {
        given()
                .when()
                    .delete()
                .then()
                    .statusCode(405);
    }

    @Test
    public void testEmptyRequest() {
        given()
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testOneCharRequest() {
        given().param("text", "j")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testEngSimpleRequest() {
        given().param("text", "java")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testQuotesRequest() {
        given().param("text", "\"java\"")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testTwoEngRequest() {
        given().param("text", "Java Developer")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testCyrillicSimpleRequest() {
        given().param("text", "Джава")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testTwoCyrillicRequest() {
        given().param("text", "Джава разработчик")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testNumericSimpleRequest() {
        given().param("text", "1929391293919")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testNumericEngRequest() {
        given().param("text", "1k3kj12233jjj kk3j3k 222")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testNumericCyrillicRequest() {
        given().param("text", "лдыофа212оов цо221329влй 122")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testSymbolsRequest() {
        given().param("text", " ßåƒ ƒ˜˜Ωç√˜˜åœ∑´´††¥¨ˆˆπ“«æ…¬˚∆˙©ƒ√∫˜˜≤≥ç˚£•™ª•¢¶ ƒ¬å…µ√Ωçæ«……“‘œ“")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testOrRequest() {
        given().param("text", "Java OR Swift")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testAndRequest() {
        given().param("text", "Java AND Swift")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }

    @Test
    public void testNotRequest() {
        given().param("text", "Java NOT Python")
                .when()
                    .get()
                .then()
                    .spec(checkStatusCodeAndContentType);
    }
}
