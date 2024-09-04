package com.sparta.kd.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BulkPostcodeLookupTests {

    private static Response response;

    @BeforeAll
    static void beforeAll() {
        String requestBody =
                "{ " +
                        "\"postcodes\" : [\"PR3 0SG\", \"M45 6GN\", \"EX165BL\"]" +
                        "}";

        response = RestAssured
                .given()
                .baseUri("https://api.postcodes.io")
                .basePath("/postcodes")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post()
                .thenReturn();
    }

    @Test
    @DisplayName("Status code 200 returned")
    void testStatusCode200() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("The total number of codes returned is 3")
    void testTotalNumberOfCodes() {
        MatcherAssert.assertThat(response.jsonPath().getList("result").size(), Matchers.is(3));
    }

    @Test
    @DisplayName("correct postcodes returned in response")
    void testCorrectPostcodeReturned() {
        MatcherAssert.assertThat(response.jsonPath().getString("result[0].result.postcode"), Matchers.is("PR3 0SG"));
        MatcherAssert.assertThat(response.jsonPath().getString("result[1].result.postcode"), Matchers.is("M45 6GN"));
        MatcherAssert.assertThat(response.jsonPath().getString("result[2].result.postcode"), Matchers.is("EX16 5BL"));
    }

    @Test
    @DisplayName("The server name in the headers should be cloudflare")
    void testServerNameIsCloudFlare() {
        MatcherAssert.assertThat(response.header("Server"), Matchers.is("cloudflare"));
    }

    @Test
    @DisplayName("The content type of the response should be application/json")
    void testContentTypeIsJson() {
        MatcherAssert.assertThat(response.getContentType().split(";")[0], Matchers.equalTo("application/json"));
    }

    @Test
    @DisplayName("The response time should be less than 2 seconds")
    void testResponseTime() {
        MatcherAssert.assertThat(response.getTime(), Matchers.lessThan(2000L));
    }
}
