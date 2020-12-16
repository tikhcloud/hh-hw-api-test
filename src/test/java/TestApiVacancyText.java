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

}
