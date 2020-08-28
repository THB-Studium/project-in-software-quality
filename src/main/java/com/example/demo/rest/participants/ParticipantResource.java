package com.example.demo.rest.participants;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ParticipantVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.ParticipantService;

@RestController
@RequestMapping(ApiConstants.PARTICIPANT_ITEM)
public class ParticipantResource {
    private static final Logger log = LoggerFactory.getLogger(ParticipantRootResource.class);

    @Autowired
    private ParticipantService participantService;

    /**
     * TO GET ONE PARTICIPANT BY HIS ID path:
     * "/api/participants/{participantId}"
     * 
     * @param participantId
     * @return
     */
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.GET)
    public ParticipantVO getOneParticipant(@PathVariable("participantId") UUID participantId) {
        ParticipantVO participantFound = participantService.getOne(participantId);
        log.info(
                String.format("The participant with the id=%s has been found!"), participantId.toString());
        return participantFound;
    }

    /**
     * TO UPDATE A PARTICIPANT path: "/api/participants/{participantId}"
     * 
     * @param participantId
     * @param update
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.PUT)
    public void updateParticipant(@PathVariable("participantId") UUID participantId, ParticipantVO update) {
        participantService.update(participantId, update);
        log.info(String.format("The participant with the id=%s has been successfully updated!"), participantId.toString());
    }

    /**
     * TO DELETE A PARTICIPANT
     * 
     * @param participantId
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteParticipant(@PathVariable("participantId") UUID participantId) {
        participantService.delete(participantId);
        log.info(String.format("The participant with the id=%s has been successfully updated!"), participantId.toString());
    }

}
