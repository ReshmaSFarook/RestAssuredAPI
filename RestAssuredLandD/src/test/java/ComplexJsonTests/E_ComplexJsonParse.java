package ComplexJsonTests;

import io.restassured.path.json.JsonPath;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Payload.payload;

public class E_ComplexJsonParse {
    String dummyPayload;
    JsonPath js = new JsonPath(dummyPayload);
    int noOfCourses;
    int purchaseAmt;
    int price;
    int copies;
    int calc = 0;

    @BeforeEach
    void beforeTest()
    {
        //Load the payload into a jsonpath and use that for further validations
         js = new JsonPath(payload.CoursePrice());
        // Print purchase amount in dashboard node
        purchaseAmt = js.get("dashboard.purchaseAmount");
    }

    @Test
    void getCoursesCount(){
        //Get no of courses in the json
        noOfCourses=js.getInt("courses.size()");
        System.out.println(noOfCourses);
    }

    @Test
    void getPurchaseAmount(){
        //Print purchase amount in dashboard node
        purchaseAmt=js.getInt("dashboard.purchaseAmount");//getInt to get integer value
        System.out.println(purchaseAmt+"=purchase Amount");
    }

    @Test
    void getTitleOfFirstCourse(){
        //Find the title of the first course
        String firstTitle = js.get("courses[0].title");
        System.out.println("first title : " +firstTitle);
    }

    @Test
    void getCourseDetails(){
        //Get all the course details  - title,price ,copies of all the courses
        for(int i=0; i<js.getInt("courses.size()");i++){
            int j=i+1;
            System.out.println("title:"+j+" ="+js.get("courses["+i+"].title")+ "| price:"+j+" =" +js.get("courses["+i+"].price".toString())+ "| copies:"+j+" ="+js.get("courses["+i+"].copies".toString()));
        }
    }

    @Test
    void getRPACourseCopies(){
        //get course details of only rpa course
        for(int i=0; i<js.getInt("courses.size()");i++){
            int j=i+1;
            String courseTitle=js.get("courses["+i+"].title");
            if(courseTitle.equalsIgnoreCase("RPA")){
                System.out.println("Copies of RPA " +js.get("courses["+i+"].copies"));
                break;
            }
            System.out.println("title:"+j+" ="+js.get("courses["+i+"].title")+ "| price:"+j+" =" +js.get("courses["+i+"].price".toString())+ "| copies:"+j+" ="+js.get("courses["+i+"].copies".toString()));
        }
    }

    @Test
    void verifyPurchaseAmtCalculation(){
    //calculate if all the course prices add to purchase amt - price*copies of each course = purchase ant validation
        for(int i=0;i<js.getInt("courses.size");i++){
            int j=i+1;
            price = js.get("courses["+i+"].price");
            copies = js.get("courses["+i+"].copies");
            calc = calc +(price*copies);
            System.out.println("calc "+j+ "="+calc);
        }
        Assert.assertEquals(calc,purchaseAmt);
    }
}
