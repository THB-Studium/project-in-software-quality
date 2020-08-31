package com.example.demo;

import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.constant.StateOfSeminarVO;
import com.example.demo.model.Booking;
import com.example.demo.model.ParticipantVO;
import com.example.demo.model.SeminarVO;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ParticipantVORepository;
import com.example.demo.repository.SeminarVORepository;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItBase {

    protected Random random = new Random();
    protected String authorization;
    protected String username;
    protected String password;

    @LocalServerPort
    protected int port;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    @Autowired
    protected BookingRepository bookingRepository;
    @Autowired
    protected SeminarVORepository seminarVORepository;
    @Autowired
    protected ParticipantVORepository participantVORepository;

    protected MockMvc mockMvc;
    protected MockHttpSession session;

    @Before
    public void setup() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        authorization = String.format("Autho_%s", UUID.randomUUID().toString());

        username = "username";
        password = "password";

        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder().build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    public void cleanup() {
        bookingRepository.deleteAll();
        seminarVORepository.deleteAll();
        participantVORepository.deleteAll();
    }
    
    
    
    // about Booking:
    public Booking buildBooking(SeminarVO currentSeminar) {
        return new Booking(currentSeminar);    
    }    
    

    // about SeminarVO:

    public SeminarVO buildSeminarVO(String name, int max) {
        SeminarVO item = new SeminarVO();

        item.setName(name);
        item.setMax(max);
        item.setState(StateOfSeminarVO.AVAILABLE);
        
        for (int i=0; i < max - 1; i++) {
            item.getParticipants().add(buildParticipantVO());
        }

        return item;
    }

    public SeminarVO buildSeminarVO(String name) {
        return buildSeminarVO(name, random.nextInt(4) + 2);
    }

    public SeminarVO buildSeminarVO() {
        return buildSeminarVO(
                String.format("Name_%s", UUID.randomUUID().toString()), 
                random.nextInt(4) + 2);
    }

    // about Participants:

    public ParticipantVO buildParticipantVO(String name) {
        return new ParticipantVO(name);
    }

    public ParticipantVO buildParticipantVO() {
        return buildParticipantVO(
                String.format("Vorname_%s, Nachname_%s", UUID.randomUUID(), UUID.randomUUID()));
    }
}
