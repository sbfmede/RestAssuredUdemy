package com.herokupp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateNewBookingTest extends BaseTest{

    @Test
    public void updateBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname","Mine");
        body.put("lastname","Rebrov");
        body.put("totalprice",25);
        body.put("depositpaid",true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin","2022-01-01");
        bookingdates.put("checkout","2022-03-03");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "NewBreakfast");

        ///Update booking
        Response responseUpdate = RestAssured.given().auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON)
                .body(body.toString()).put("https://restful-booker.herokuapp.com/booking/" + bookingid);


        //Verify response 200
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but not");

        //Verify First Name in response
        SoftAssert softAssert = new SoftAssert();
        var actionFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actionFirstName, "Mine", "firstname is response is not expected");
        var actionLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actionLastName, "Rebrov", "lastname is response is not expected");
        var price = responseUpdate.jsonPath().getString("totalprice");
        softAssert.assertEquals(price, "25", "totalprice is response is not expected");
        var depositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "depositPaid should be true");
        var actualChecking = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualChecking, "2022-01-01", "checkin is response is not expected");
        var actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-03-03", "checkout is response is not expected");
        var actualAdditionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "NewBreakfast", "checkout is response is not expected");
        softAssert.assertAll();
    }
}