package com.herokupp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class GetFirstLastNamesTest {

    @Test
    public void getFirstLastNamesTest() {
        //Get response
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/5");
        response.print();
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but not");

        //Verify First Name in response
        SoftAssert softAssert = new SoftAssert();
        var actionFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actionFirstName, "Eric", "firstname is response is not expected");
        var actionLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actionLastName, "Jackson", "lastname is response is not expected");
        var price = response.jsonPath().getString("totalprice");
        softAssert.assertEquals(price, "462", "totalprice is response is not expected");
        var depositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "depositPaid should be true");
        var actualChecking = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualChecking, "2023-03-24", "checkin is response is not expected");
        var actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-04-15", "checkout is response is not expected");
        softAssert.assertAll();
    }
}
