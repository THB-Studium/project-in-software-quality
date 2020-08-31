package com.example.demo.rest.participants;

import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ParticipantVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.ParticipantService;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@RestController
@RequestMapping(ApiConstants.PARTICIPANT_COLLECTION)
public class ParticipantRootResource {
    private static final Logger log = LoggerFactory.getLogger(ParticipantRootResource.class);

    @Autowired
    private ParticipantService participantService;

    
    
    /**
     * TO GET ALL PARTICIPANTS: path: "/api/participants"
     * 
     * @return
     */
    @RequestMapping(
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ParticipantVO> getAllparticipants() {
        Set<ParticipantVO> participants = participantService.listAll();
        log.info("participants have been successfuly listed!");
        return participants;
    }

    /**
     * TO ADD/CREATE A NEW PARTICIPANT: path: "/api/participants"
     * 
     * @param newParticipant
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ParticipantVO addBoking(@RequestBody ParticipantVO newParticipant) {
        ParticipantVO cretedParticipant = participantService.create(newParticipant);
        log.info("participants have been successfuly created!");
        return cretedParticipant;
    }

}
