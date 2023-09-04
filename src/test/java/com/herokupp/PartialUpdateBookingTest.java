package com.herokupp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTest extends BaseTest {
    @Test
    public void partialUpdateBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "NewMine");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-08-01");
        bookingdates.put("checkout", "2022-03-03");
        body.put("bookingdates", bookingdates);

        //Partial Update booking
        Response responsePatch = RestAssured.given().auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON)
                .body(body.toString()).patch("https://restful-booker.herokuapp.com/booking/" + bookingid);


        //Verify response 200
        Assert.assertEquals(responsePatch.getStatusCode(), 200, "Status code should be 200, but not");

        //Verify First Name in response
        SoftAssert softAssert = new SoftAssert();
        var actionFirstName = responsePatch.jsonPath().getString("firstname");
        softAssert.assertEquals(actionFirstName, "NewMine", "firstname is response is not expected");
        var actionLastName = responsePatch.jsonPath().getString("lastname");
        softAssert.assertEquals(actionLastName, "Petrov", "lastname is response is not expected");
        var price = responsePatch.jsonPath().getString("totalprice");
        softAssert.assertEquals(price, "150", "totalprice is response is not expected");
        var depositPaid = responsePatch.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "depositPaid should be true");
        var actualChecking = responsePatch.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualChecking, "2022-08-01", "checkin is response is not expected");
        var actualCheckout = responsePatch.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-03-03", "checkout is response is not expected");
        var actualAdditionalneeds = responsePatch.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "checkout is response is not expected");
        softAssert.assertAll();
    }
}
