package JIRATests;

import Util.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;

import static io.restassured.RestAssured.*;

public class F_JiraTests {
    private static JsonPath js ;
    private static String JSESSIONID,response;

    SessionFilter session = new SessionFilter();//Creating a session filtee for authentication
    public F_JiraTests(){
        //Step 1  : Create authentication to access JIRA
        super();
        baseURI="http://localhost:8080";
        //relaxedHTTPSValidation is used to relax any HTTPS related restrictions
        response = given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json").body("{\n" +
                "    \"username\": \"reshma.s.farook\",\n" +
                "     \"password\": \"1234\"\n" +
                "     }").filter(session).when().post("rest/auth/1/session")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = ReUsableMethods.rawToJson(response);
        JSESSIONID = js.getString("session.value");
        System.out.println(JSESSIONID);
    }

    @Test
    void createBugFromAPI(){
        //Create bug from API in JIRA
      response =given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json")
              .body("{\n" +
              "    \"fields\": {\n" +
              "       \"project\":\n" +
              "       {\n" +
              "          \"key\": \"RES\"\n" +
              "       },\n" +
              "       \"summary\": \"Creating defect from code part 1.\",\n" +
              "       \"description\": \"Description of code from API part 1\",\n" +
              "       \"issuetype\": {\n" +
              "          \"name\": \"Bug\"\n" +
              "       }\n" +
              "   }\n" +
              "}").filter(session).when().post("/rest/api/2/issue")
              .then().log().all().assertThat().statusCode(201).extract().response().asString();
      System.out.println(response);

      //Add atttachment
        //Need to specify header here for token and content type, //add multi part to input details of the attachment
        response =given().log().all().header("X-Atlassian-Token","no-check").pathParam("key","10001")
                .header("Content-Type","multipart/form-data")
                .multiPart("file",new File("jira.txt")).filter(session).
                when().post("rest/api/2/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
