package com.example.demo.rest;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.demo.ItBase;
import com.example.demo.model.ParticipantVO;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
public class ParticipantResourceIT extends ItBase {

    private ParticipantVO participant1;
    private ParticipantVO participant2;

    @Before
    public void setup() {
        super.setup();

        // create some participants:
        participant1 = participantVORepository.save(buildParticipantVO());
        participant2 = participantVORepository.save(buildParticipantVO());
    }

    @After
    public void cleanup() {
        super.cleanup();
    }

    // TESTS:.......................................................................................................

    @Test
    public void create() {
        ParticipantVO newParticipant = buildParticipantVO();
        String id = given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .body(newParticipant)
                .log().body()
                .post(ApiConstants.PARTICIPANT_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().path("id");

        // check that the participant has been saved:
        Optional<ParticipantVO> actualOptional = participantVORepository.findById(UUID.fromString(id));
        assertThat(actualOptional.isPresent(), equalTo(true));

        ParticipantVO actual = actualOptional.get();
        assertThat(actual, notNullValue());
        assertThat(actual.getName(), equalTo(newParticipant.getName()));
    }

    @Test
    public void listAll() {
        given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .log().body()
                .get(ApiConstants.PARTICIPANT_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("id", containsInAnyOrder(participant1.getId().toString(), participant2.getId().toString()))
                .body("find{it.id=='" + participant1.getId() + "'}.name", equalTo(participant1.getName()))
                .body("find{it.id=='" + participant2.getId() + "'}.name", equalTo(participant2.getName()));
    }

    @Test
    public void getOne() {
        given()
                .log().body()
                .get(ApiConstants.PARTICIPANT_ITEM, participant1.getId())
                .then()
                .log().body()
                .statusCode(200)
                .body("id", is(equalTo(participant1.getId().toString())))
                .body("name", is(equalTo(participant1.getName())));
    }

    @Test
    public void getOneNotFound() {
        given().get(ApiConstants.PARTICIPANT_ITEM, UUID.randomUUID()).then().statusCode(404);
    }

    @Test
    public void update() {
        ParticipantVO update = buildParticipantVO();
        given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .body(update)
                .log().body()
                .put(ApiConstants.PARTICIPANT_ITEM, participant1.getId())
                .then()
                .log().body()
                .statusCode(200);

        // check that the participant has been updated:
        Optional<ParticipantVO> actualOptional = participantVORepository.findById(participant1.getId());
        assertThat(actualOptional.isPresent(), is(true));

        ParticipantVO actual = actualOptional.get();
        assertThat(actual, notNullValue());
        assertThat(actual.getId(), equalTo(participant1.getId()));
        assertThat(actual.getName(), equalTo(update.getName()));
        assertNotSame(actual.getName(), participant1.getName());
    }

    @Test
    public void updateNotFound() {
        ParticipantVO update = buildParticipantVO();
        given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .body(update)
                .log().body()
                .put(ApiConstants.PARTICIPANT_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    public void delete() {
        given().delete(ApiConstants.PARTICIPANT_ITEM, participant1.getId()).then().statusCode(200);

        // check that the participant has been deleted
        Optional<ParticipantVO> actualOptional = participantVORepository.findById(participant1.getId());
        assertThat(actualOptional.isPresent(), is(false));
    }

    @Test
    public void deleteNotFound() {
        given().delete(ApiConstants.PARTICIPANT_ITEM, UUID.randomUUID()).then().statusCode(404);
    }

    @Test
    public void participantVOisComparable() {
        Class<ParticipantVO> myParticipantVOClass = ParticipantVO.class;
        StringBuilder sb = new StringBuilder();
        Class<?> interfaces[] = myParticipantVOClass.getInterfaces();

        for (Class<?> i : interfaces) {
            sb.append(i.toString());
        }
        assertTrue("Class should be comparable", sb.toString().contains("interface java.lang.Comparable"));
    }

}
