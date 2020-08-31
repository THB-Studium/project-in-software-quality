package com.example.demo.rest.seminars;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SeminarVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.SeminarService;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
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
    @RequestMapping(
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SeminarVO> getAllSeminars() {
        Set<SeminarVO> seminars = seminarService.listAll();
        log.info("Seminars have been successfuly listed!");
        return seminars;
    }

    /**
     * TO ADD/CREATE A NEW SEMINAR: path: "/api/seminars"
     * 
     * @param newSeminar
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SeminarVO addBoking(@RequestBody SeminarVO newSeminar) {
        SeminarVO createdSeminar = seminarService.create(newSeminar);
        log.info("Seminar successfully created!");
        return createdSeminar;
    }

}
