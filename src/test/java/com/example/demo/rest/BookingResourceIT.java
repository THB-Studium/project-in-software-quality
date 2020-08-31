package com.example.demo.rest;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.demo.ItBase;
import com.example.demo.model.Booking;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
public class BookingResourceIT extends ItBase {

    private Booking booking1;
    private Booking booking2;

    @Before
    public void setup() {
        super.setup();

        // create some bookings:
        booking1 = bookingRepository.save(buildBooking(buildSeminarVO()));
        booking2 = bookingRepository.save(buildBooking(buildSeminarVO()));
    }

    @After
    public void cleanup() {
        super.cleanup();
    }

    // TESTS:.......................................................................................................

    @Test
    public void create() {
        Booking newParticipant = buildBooking(buildSeminarVO());
        String id = given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .body(newParticipant)
                .log().body()
                .post(ApiConstants.BOOKING_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().path("id");

        // check that the participant has been saved:
        Optional<Booking> actualOptional = bookingRepository.findById(UUID.fromString(id));
        assertThat(actualOptional.isPresent(), equalTo(true));

        Booking actual = actualOptional.get();
        assertThat(actual, notNullValue());
        assertThat(actual.getCurrentSeminar().getName(), equalTo(newParticipant.getCurrentSeminar().getName()));
        assertThat(actual.getCurrentSeminar().getParticipants().size(),
                equalTo(newParticipant.getCurrentSeminar().getParticipants().size()));
    }

    @Test
    public void listAll() {
        given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .log().body()
                .get(ApiConstants.BOOKING_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("id", containsInAnyOrder(booking1.getId().toString(), booking2.getId().toString()))
                .body("find{it.id=='" + booking1.getId() + "'}.currentSeminar", is(notNullValue()))
                .body("find{it.id=='" + booking2.getId() + "'}.currentSeminar", is(notNullValue()));
    }

     @Test
     public void getOne() {
     given()
     .log().body()
     .get(ApiConstants.BOOKING_ITEM, booking1.getId())
     .then()
     .log().body()
     .statusCode(200)
     .body("id", is(equalTo(booking1.getId().toString())))
     .body("currentSeminar", is(notNullValue()));
     }
    
     @Test
     public void getOneNotFound() {
     given().get(ApiConstants.BOOKING_ITEM,
     UUID.randomUUID()).then().statusCode(404);
     }

}
