package LibraryAPITests;

import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Payload.*;
import Util.*;

public class C_LibraryAPITests {
    JsonPath js ;

    @BeforeEach
    void setUpBaseURI(){
        baseURI="http://216.10.245.166";
    }

    @Test
    void AddBooks(){
        String response = given().log().all().header("Content-Type","application/json")
                .body(payload.AddBooks())
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
         js= ReUsableMethods.rawToJson(response);
         String id = js.get("ID");
         System.out.println(id);
    }

    @Test
    void AddBooksDynamic(){
        String response = given().log().all().header("Content-Type","application/json")
                .body(payload.AddBooks("testing","9188"))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        js= ReUsableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);
    }

}
