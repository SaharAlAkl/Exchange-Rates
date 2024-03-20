import net.serenitybdd.junit.runners.SerenityRunner;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SerenityRunner.class)
public class SDETAssessment {

//API call is successful and returns valid price
    @Test
    public void APIRequestValidResponse() {
        given().get("https://open.er-api.com/v6/latest/USD")
                .then().statusCode(200)
                .and().body("rates.USD", equalTo(1));
    }

    //Check the status code and status returned by the API response.
    @Test
    public void StatusResponse() {
        given().get("https://open.er-api.com/v6/latest/USD")
                .then().body("result", equalTo("success"));
    }


    //Fetch the USD price against the AED and make sure the prices are in range on 3.6 – 3.7
    @Test
    public void USDtoAED() {
        given().get("https://open.er-api.com/v6/latest/USD")
                .then().body("rates.AED", greaterThanOrEqualTo(3.6f))
                .and().body("rates.AED", lessThanOrEqualTo(3.7f));

    }

    //Make sure API response time is not less then 3 seconds then current time in second.
    @Test
    public void TimeStamp() {
        given().get("https://open.er-api.com/v6/latest/USD")
                .then().time(lessThan(3000L));
    }

    //Verify that 162 currency pairs are returned by the API.
    @Test
    public void Currenciesnumber() {
        given().get("https://open.er-api.com/v6/latest/USD")
                .then().body("rates.size()", is(162));
    }

    //Make sure API response matches the Json schema
    @Test
    public void APIvsJson() throws IOException {
        String response = given().get("https://open.er-api.com/v6/latest/USD").asString();
        File schemaFile = Paths.get("src/test/schema/schema.json").toFile();
        String schemaContent = FileUtils.readFileToString(schemaFile, Charset.defaultCharset());
        given()
                .contentType("application/json")
                .body(response)
                .when()
                .then()
                .body(matchesJsonSchema(schemaContent))
                .body("result", equalTo("success"))
                .body("base", equalTo("USD"));
    }
}