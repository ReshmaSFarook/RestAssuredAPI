package LibraryAPITests;

import Util.ReUsableMethods;
import io.restassured.path.json.JsonPath;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import Payload.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class D_ParameterizedLibraryAPITests {
    private String isbn;
    private String aisle;
    private payload payload;
    JsonPath js ;

    @BeforeEach
    void setUpBaseURI(){
        baseURI="http://216.10.245.166";
    }

    public D_ParameterizedLibraryAPITests(String isbn, String aisle)
    {
        super();
        this.isbn=isbn;
        this.aisle=aisle;
    }

    @Parameterized.Parameters
    public static Collection input(){
        return Arrays.asList(new Object[][] {{"uuss","9298"},{"hjhd","9839"},{"dhjs","9129"}});
    }

    @Test
    public void AddBooksParameterise(){
        baseURI="http://216.10.245.166";
        String response = given().log().all().header("Content-Type","application/json")
                .body(payload.AddBooks(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        js= ReUsableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);
    }
}
