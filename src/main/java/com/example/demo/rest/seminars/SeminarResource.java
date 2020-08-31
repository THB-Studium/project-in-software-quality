package com.example.demo.rest.seminars;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SeminarVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.SeminarService;
import com.example.demo.service.exception.NoSeminarException;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@RestController
@RequestMapping(ApiConstants.SEMINAR_ITEM)
public class SeminarResource {
    private static final Logger log = LoggerFactory.getLogger(SeminarRootResource.class);

    @Autowired
    private SeminarService seminarService;

    
    
    /**
     * TO GET ONE SEMINAR BY HIS ID: path: "/api/seminars/{seminarId}"
     * 
     * @param seminarId
     * @return
     * @throws NoSeminarException
     */
    @RequestMapping(
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SeminarVO getOneSeminar(@PathVariable("seminarId") UUID seminarId) throws NoSeminarException {
        SeminarVO seminarFound = seminarService.getOne(seminarId);
        log.info(
                String.format("The Seminar with the id=%s has been found!", seminarId.toString()));
        return seminarFound;
    }

    /**
     * TO UPDATE A SEMINAR path: "/api/seminars/{seminarId}"
     * 
     * @param seminarId
     * @param update
     * @throws NoSeminarException
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateSeminar(@PathVariable("seminarId") UUID seminarId, @RequestBody SeminarVO update) 
            throws NoSeminarException {
        seminarService.update(seminarId, update);
        log.info(String.format("The Seminar with the id=%s has been successfully updated!", seminarId.toString()));
    }

    /**
     * TO DELETE A SEMINAR
     * 
     * @param seminarId
     * @throws NoSeminarException
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteSeminar(@PathVariable("seminarId") UUID seminarId) throws NoSeminarException {
        seminarService.delete(seminarId);
        log.info(String.format("The Seminar with the id=%s has been successfully updated!", seminarId.toString()));
    }

}
