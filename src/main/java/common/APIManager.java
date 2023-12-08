package common;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static common.Constants.JSON_FILE;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static helpers.Utils.getCurrentMethodName;

public abstract class APIManager {
    private static RequestSpecBuilder requestSpecBuilder;
    private static ResponseSpecBuilder responseSpecBuilder;
    private static DocumentContext apiConfig;

    public APIManager(){
        resetRESTState();
        apiConfig = getApiConfig();
    }

    public APIManager(LogDetail logDetail){
        resetRESTState();
        responseSpecBuilder.log(logDetail);
        apiConfig = getApiConfig();
    }

    public void addHeader(final String key, final String value){
        FilterableRequestSpecification request = (FilterableRequestSpecification) getRequestBuilder();
    }

    public RequestSpecBuilder getRequestBuilder() {return requestSpecBuilder;}

    public static DocumentContext getApiConfig(){
        if(Objects.isNull(apiConfig)){
            try
            {
                apiConfig = JsonPath.parse(new File(JSON_FILE));
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return apiConfig;
    }

    public static void resetSpecBuilder(){
        requestSpecBuilder = new RequestSpecBuilder().setRelaxedHTTPSValidation();
        responseSpecBuilder = new ResponseSpecBuilder();
    }
    public static void resetRESTState(){
        RestAssured.reset();
        resetSpecBuilder();
    }

    public Response get(final String url){
        Response response = given()
                .spec(requestSpecBuilder.build())
                .get(url)
                .then()
                .spec(responseSpecBuilder.build())
                .extract()
                .response();
        assertEquals(
                response.getStatusCode(),
                200,
                getCurrentMethodName().toUpperCase() + "call failed for" + url + "\n" + response.getBody().asString());
        return response;
    }

    public Response post(final String url){
        Response response = given()
                .spec(requestSpecBuilder.build())
                .post(url)
                .then()
                .spec(responseSpecBuilder.build())
                .extract()
                .response();
        assertEquals(
                response.getStatusCode(),
                200,
                getCurrentMethodName().toUpperCase() + "call failed for" + url + "\n" + response.getBody().asString());
        return response;
    }

}
