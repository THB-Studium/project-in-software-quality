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
import com.example.demo.constant.StateOfSeminarVO;
import com.example.demo.model.ParticipantVO;
import com.example.demo.model.SeminarVO;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
public class SeminarResourceIT extends ItBase {
    
    private SeminarVO seminar1;
    private SeminarVO seminar2;
    private SeminarVO seminar3;

    @Before
    public void setup() {
        super.setup();
        
        // create some seminars:
        seminar1 = seminarVORepository.save(buildSeminarVO());
        seminar2 = seminarVORepository.save(buildSeminarVO());
        seminar3 = seminarVORepository.save(buildSeminarVO());
        
        // add some participants:
        seminar2 = seminarVORepository.save(seminar2);
        seminar3 = seminarVORepository.save(seminar3);
    }

    @After
    public void cleanup() {
        super.cleanup();
    }

    // TESTS:.......................................................................................................

    @Test
    public void create() {
        SeminarVO newSeminar = buildSeminarVO();
        String id = given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .body(newSeminar)
                .log().body()
                .post(ApiConstants.SEMINAR_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().path("id");

        // check that the participant has been saved:
        Optional<SeminarVO> actualOptional = seminarVORepository.findById(UUID.fromString(id));
        assertThat(actualOptional.isPresent(), equalTo(true));

        SeminarVO actual = actualOptional.get();
        assertThat(actual, notNullValue());
        assertThat(actual.getName(), equalTo(newSeminar.getName()));
        assertThat(actual.getMax(), equalTo(newSeminar.getMax()));
        assertThat(actual.getState(), equalTo(newSeminar.getState()));
        assertThat(actual.getParticipants().size(), equalTo(newSeminar.getParticipants().size()));
    }

    @Test
    public void listAll() {
        given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .log().body()
                .get(ApiConstants.SEMINAR_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", equalTo(3))
                .body("id", containsInAnyOrder(seminar1.getId().toString(), seminar2.getId().toString(), seminar3.getId().toString()))
                .body("find{it.id=='" + seminar1.getId() + "'}.name", equalTo(seminar1.getName()))
                .body("find{it.id=='" + seminar1.getId() + "'}.max", equalTo(seminar1.getMax()))
                .body("find{it.id=='" + seminar1.getId() + "'}.state", equalTo(StateOfSeminarVO.AVAILABLE.toString()))
                .body("find{it.id=='" + seminar2.getId() + "'}.name", equalTo(seminar2.getName()))
                .body("find{it.id=='" + seminar2.getId() + "'}.max", equalTo(seminar2.getMax()))
                .body("find{it.id=='" + seminar2.getId() + "'}.state", equalTo(StateOfSeminarVO.AVAILABLE.toString()))
                ;
    }

    @Test
    public void getOne() {
        given()
                .log().body()
                .get(ApiConstants.SEMINAR_ITEM, seminar2.getId())
                .then()
                .log().body()
                .statusCode(200)
                .body("id", is(equalTo(seminar2.getId().toString())))
                .body("name", is(equalTo(seminar2.getName())))
                .body("max", is(equalTo(seminar2.getMax())))
                .body("participants", is(notNullValue()))
                ;
    }

    @Test
    public void getOneNotFound() {
        given().get(ApiConstants.SEMINAR_ITEM, UUID.randomUUID()).then().statusCode(404);
    }

//    @Test
//    public void update() {
//        given()
//                .header("Content-Type", "application/json;charset=UTF-8;")
//                .body(seminar2)
//                .log().body()
//                .put(ApiConstants.SEMINAR_ITEM, seminar3.getId())
//                .then()
//                .log().body()
//                .statusCode(200);
//
//        // check that the participant has been updated:
//        Optional<SeminarVO> actualOptional = seminarVORepository.findById(seminar3.getId());
//        assertThat(actualOptional.isPresent(), is(true));
//
//        SeminarVO actual = actualOptional.get();
//        assertThat(actual, notNullValue());
//        assertNotSame(actual.getName(), seminar3.getName());
//        assertThat(actual.getId(), equalTo(seminar2.getId()));
//        assertThat(actual.getName(), equalTo(seminar2.getName()));
//        assertThat(actual.getMax(), equalTo(seminar2.getMax()));
//        assertThat(actual.getState(), equalTo(seminar2.getState()));
//        assertThat(actual.getParticipants().size(), equalTo(seminar2.getParticipants().size()));
//    }

    @Test
    public void updateNotFound() {
        given()
                .header("Content-Type", "application/json;charset=UTF-8;")
                .body(buildSeminarVO())
                .log().body()
                .put(ApiConstants.SEMINAR_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    public void delete() {
        given().delete(ApiConstants.SEMINAR_ITEM, seminar3.getId()).then().statusCode(200);

        // check that the participant has been deleted
        Optional<ParticipantVO> actualOptional = participantVORepository.findById(seminar3.getId());
        assertThat(actualOptional.isPresent(), is(false));
    }

    @Test
    public void deleteNotFound() {
        given().delete(ApiConstants.SEMINAR_ITEM, UUID.randomUUID()).then().statusCode(404);
    }

}
