package com.example.demo.rest.seminars;

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
import com.example.demo.model.SeminarVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.SeminarService;

@RestController
@RequestMapping(ApiConstants.SEMINAR_COLLECTION)
public class SeminarRootResource {
    private static final Logger log = LoggerFactory.getLogger(SeminarRootResource.class);

    @Autowired
    private SeminarService seminarService;

    /**
     * TO GET ALL SEMINARS: path: "/api/seminars"
     * 
     * @return
     */
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.GET)
    public Set<SeminarVO> getAllSeminars() {
        Set<SeminarVO> seminars = seminarService.listAll();
        log.info("Seminars have been successfuly listed!");
        return seminars;
    }

    /**
     * TO ADD/CREATE A NEW SEMINAR: path: "/api/seminars"
     * 
     * @param uriInfo
     * @param newSeminar
     * @return
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public Response addBoking(@Context UriInfo uriInfo, SeminarVO newSeminar) {
        SeminarVO createdSeminar = seminarService.create(newSeminar);

        // build of the response:
        URI uri = uriInfo.getRequestUriBuilder()
                .path(createdSeminar.getId().toString()).build();

        log.info("Seminar successfully created!");
        return Response.created(uri).entity(new IdTO(createdSeminar.getId())).build();
    }

}
