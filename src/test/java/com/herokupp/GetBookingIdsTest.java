package com.herokupp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTest {

    @Test
    public void getBookingIdsWithoutFilterTest() {

        //Get response
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.print();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but not");

        //Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List is empty, but it should not be empty");
    }
}
