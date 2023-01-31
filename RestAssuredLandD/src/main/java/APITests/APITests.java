package APITests;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.*;
import static org.hamcrest.Matchers.*;

import Payload.payload;

public class APITests {

    public static void main(String[] args){
        System.out.println("Hello world");
        System.out.println("-----------------------------------");
        baseURI="https://rahulshettyacademy.com";
        String response =given().
            log().all().
            queryParam("key","qaclick123").
            header("Content-Type","application/json").
            body(payload.AddPlace()).
                when().post("maps/api/place/add/json").
                then().log().all().assertThat().statusCode(200).header("Server","Apache/2.4.18 (Ubuntu)").body("scope",equalTo("APP")).extract().response().asString();

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




    }



}
