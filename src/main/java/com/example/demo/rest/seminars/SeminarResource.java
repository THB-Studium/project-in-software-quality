package com.example.demo.rest.seminars;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SeminarVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.SeminarService;
import com.example.demo.service.exception.NoSeminarException;

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
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.GET)
    public SeminarVO getOneSeminar(@PathParam("seminarId") UUID seminarId) throws NoSeminarException {
        SeminarVO seminarFound = seminarService.getOne(seminarId);
        log.info(
                String.format("The Seminar with the id=%s has been found!"), seminarId.toString());
        return seminarFound;
    }

    /**
     * TO UPDATE A SEMINAR path: "/api/seminars/{seminarId}"
     * 
     * @param seminarId
     * @param update
     * @throws NoSeminarException
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.PUT)
    public void updateSeminar(@PathParam("seminarId") UUID seminarId, SeminarVO update) throws NoSeminarException {
        seminarService.update(seminarId, update);
        log.info(String.format("The Seminar with the id=%s has been successfully updated!"), seminarId.toString());
    }

    /**
     * TO DELETE A SEMINAR
     * 
     * @param seminarId
     * @throws NoSeminarException
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteSeminar(@PathParam("seminarId") UUID seminarId) throws NoSeminarException {
        seminarService.delete(seminarId);
        log.info(String.format("The Seminar with the id=%s has been successfully updated!"), seminarId.toString());
    }

}
