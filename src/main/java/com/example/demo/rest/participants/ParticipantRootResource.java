package com.example.demo.rest.participants;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.TransferOpject.IdTO;
import com.example.demo.model.ParticipantVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.ParticipantService;

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
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.GET)
    public Set<ParticipantVO> getAllparticipants() {
        Set<ParticipantVO> participants = participantService.listAll();
        log.info("participants have been successfuly listed!");
        return participants;
    }

    /**
     * TO ADD/CREATE A NEW PARTICIPANT: path: "/api/participants"
     * 
     * @param uriInfo
     * @param newParticipant
     * @return
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public Response addBoking(@Context UriInfo uriInfo, ParticipantVO newParticipant) {
        ParticipantVO createdParticipant = participantService.create(newParticipant);

        // build of the response:
        URI uri = uriInfo.getRequestUriBuilder().path(createdParticipant.getId().toString()).build();
        log.info("Participant successfully created!");
        return Response.created(uri).entity(new IdTO(createdParticipant.getId())).build();
    }

}
