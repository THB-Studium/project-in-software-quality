package com.example.demo.rest;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.demo.ItBase;
import com.example.demo.model.ParticipantVO;
import com.example.demo.model.SeminarVO;

import io.restassured.http.ContentType;

public class ParticipantResourceIT extends ItBase {

    private SeminarVO seminar1;
    private SeminarVO seminar2;

    private ParticipantVO participant1;
    private ParticipantVO participant2;

    @Before
    public void setup() {
        super.setup();

        seminar1 = seminarVORepository.save(buildSeminarVO());
        seminar2 = seminarVORepository.save(buildSeminarVO());

        participant1 = participantVORepository.save(buildParticipantVO(UUID.randomUUID().toString(), seminar1));
        participant2 = participantVORepository.save(buildParticipantVO(UUID.randomUUID().toString(), seminar2));
    }

    @After
    public void cleanup() {
        super.cleanup();
    }

    @Test
    public void list() {
        given()
                .contentType(ContentType.JSON)
                .log().body()
                .get(ApiConstants.PARTICIPANT_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", containsInAnyOrder(participant1.getId().toString(), participant2.getId().toString()))
                .body("name", containsInAnyOrder(participant1.getName(), participant2.getName()));
    }
}
