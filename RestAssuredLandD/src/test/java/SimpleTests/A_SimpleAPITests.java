package SimpleTests;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Payload.payload;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class A_SimpleAPITests {

    @BeforeEach
    void beforeTest(){
        System.out.println("-----------------------------------");
        baseURI="https://rahulshettyacademy.com";
    }

    @Test
    void Test01(){
        String response =given().
                log().all().
                queryParam("key","qaclick123").
                header("Content-Type","application/json").
                body(payload.AddPlace()).
                when().post("maps/api/place/add/json").
                then().log().all().assertThat().statusCode(200).header("Server","Apache/2.4.18 (Ubuntu)").body("scope",equalTo("APP")).extract().response().asString();

        given().log().all().header("content type","").queryParam("key","value").body("payload")
                .when().post("resource")
                .then().log().all().assertThat().statusCode(200).body("key",equalTo("valueexpected")).extract().response().toString();

        System.out.println(response);
        System.out.println("-----------------------------------");

        JsonPath js = new JsonPath(response);
        String place =(js.getString("place_id"));
        System.out.println("place id : "+place);
        String newAddress ="South Africa Test 123";
        System.out.println("-----------------------------------");
        System.out.println(given().
                log().all().
                queryParam("key","qaclick123").
                header("Content-Type","application/json").
                body("{\n" +
                        "    \"place_id\": \""+place+"\",\n" +
                        "\"address\": \""+newAddress+"\",\n" +
                        "\"key\": \"qaclick123\"\n" +
                        "}").
                when().put("maps/api/place/update/json").then().log().all().body("msg",equalTo("Address successfully updated")).extract().response().asString());
        System.out.println("-----------------------------------");

        String getResponse= given().log().all().queryParam("key","qaclick123").queryParam("place_id",place)
                .when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).body("address",equalTo(newAddress)).extract().response().asString();
        js = new JsonPath(getResponse);
        String address = js.getString("address");
        System.out.println("Expected address = " +newAddress);
        System.out.println("Actual address "+ address);
        Assertions.assertEquals(address,newAddress);
    }
}
