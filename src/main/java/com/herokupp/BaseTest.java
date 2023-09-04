package com.herokupp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {

    protected Response createBooking() {
        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname","Dmitry");
        body.put("lastname","Petrov");
        body.put("totalprice",150);
        body.put("depositpaid",true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin","2022-01-01");
        bookingdates.put("checkout","2022-03-03");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Breakfast");

        //Get response
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(body.toString()).post("https://restful-booker.herokuapp.com/booking");
        return response;
    }
}
