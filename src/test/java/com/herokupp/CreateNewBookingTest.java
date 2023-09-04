package com.herokupp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateNewBookingTest extends BaseTest{

    @Test
    public void createBookingTest() {
        Response response = createBooking();
        response.print();

        //Verification

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but not");

        //Verify First Name in response
        SoftAssert softAssert = new SoftAssert();
        var actionFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actionFirstName, "Dmitry", "firstname is response is not expected");
        var actionLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actionLastName, "Petrov", "lastname is response is not expected");
        var price = response.jsonPath().getString("booking.totalprice");
        softAssert.assertEquals(price, "150", "totalprice is response is not expected");
        var depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositPaid, "depositPaid should be true");
        var actualChecking = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualChecking, "2022-01-01", "checkin is response is not expected");
        var actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-03-03", "checkout is response is not expected");
        var actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "checkout is response is not expected");
        softAssert.assertAll();
    }
}
