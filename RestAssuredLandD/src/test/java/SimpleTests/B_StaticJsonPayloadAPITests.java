package SimpleTests;

import Util.ReUsableMethods;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.*;

public class B_StaticJsonPayloadAPITests {


    @BeforeEach
    void settingBaseURI(){
        baseURI = "http://216.10.245.166";
    }

    @Test
    void staticPayloadAddBooks() throws IOException{
        String response =
                given().log().all().header("Content-Type","application/json").body(GenerateStringFromResource("/Users/reshma_farook/IdeaProjects/RestAssuredAPI/src/main/java/StaticPayload/AddBooks.json")).
        when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js= ReUsableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);
    }

    public static String GenerateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}


