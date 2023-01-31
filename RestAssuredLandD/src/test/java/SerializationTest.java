import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import pojo.AddBooksSerialization.AddBooks;
import pojo.AddBooksSerialization.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class SerializationTest {
    AddBooks addbooks = new AddBooks();
    Location location = new Location();

    public SerializationTest(){
        super();
        baseURI = "https://rahulshettyacademy.com";
        addbooks.setName("Rahul Shetty Academy");
        addbooks.setAccuracy(50);
        addbooks.setPhone_number("(+91) 983 893 3937");
        addbooks.setAddress("Test 123");
        addbooks.setWebsite("https://rahulshettyacademy.com");
        addbooks.setLanguage("French-IN");
        List<String> typeList = new ArrayList<>();
        typeList.add("shoe park");
        typeList.add("shop");
        addbooks.setTypes(typeList);
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addbooks.setLocation(location);
    }

    @Test
    void AddBooks(){
        Response res=
                given().log().all().header("Content-Type","application/json")
                        .queryParam("key","qaclick123").body(addbooks).
                when().post("maps/api/place/add/json").
                        then().log().all().assertThat().statusCode(200).extract().response();
        String responseString = res.asString();
        System.out.println(responseString);
    }

    @Test
    void getBooks(){
        Response res=
                given().log().all().header("Content-Type","application/json").
                        when().get("maps/api/place/get/json").
                        then().log().all().assertThat().statusCode(200).extract().response();
        String responseString = res.asString();
        System.out.println(responseString);
    }

    @Test
    void addBooksWithRequestSpec(){
        //build request specification using Req spec builder : set Base URI, set Content -Type, set Query param
        RequestSpecification reqSpec =
                new RequestSpecBuilder().
                        setBaseUri(baseURI).
                        setContentType("application/json").
                        addQueryParam("key","qaclick123").build();
        //build response specification using Response spec builder : expectedContentType , expectedStatusCode
        ResponseSpecification resSpec=
                new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON).expectStatusCode(200).build();

        //Append req with given(), spec (reqSpec), body(JSON- set)
        RequestSpecification req = given().spec(reqSpec).body(addbooks);
        //Fetch response - req >> when -- http method(get/post/put/del) >> then >spec(respSpec) - extract response
        Response res = req.when().post("maps/api/place/add/json").then().spec(resSpec).extract().response();
        System.out.println(res.asString());


    }
}
