package com.example.demo.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.example.demo.constant.StateOfSeminarVO;
import com.example.demo.model.Booking;
import com.example.demo.model.ParticipantVO;
import com.example.demo.model.SeminarVO;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.SeminarVORepository;
import com.example.demo.service.exception.NoParticipantsException;
import com.example.demo.service.exception.NoSeminarException;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@Named
@Transactional(rollbackOn = Exception.class)
public class SeminarService {

    @Inject
    private SeminarVORepository seminarVORepository;
    @Inject
    private BookingRepository bookingRepository;

    
    
    /**
     * TO GET ALL SEMINARS
     * 
     * @return
     */
    public Set<SeminarVO> listAll() {
        return seminarVORepository.findAll().stream().collect(Collectors.toSet());
    }

    /**
     * TO GET ONE SEMINAR BY SEMINAR´S ID
     * 
     * @param seminarId
     * @return
     * @throws NoSeminarException
     */
    public SeminarVO getOne(UUID seminarId) throws NoSeminarException {
        Optional<SeminarVO> seminarOp = seminarVORepository.findById(seminarId);
        if (seminarOp.isPresent()) {
            return seminarOp.get();
        } else {
            throw new NoSeminarException(
                    String.format("There is no seminar with the id: %s.", seminarId.toString()));
        }
    }

    /**
     * TO CREATE A NEW SEMINAR
     * 
     * @param newSeminarVO
     * @return
     */
    public SeminarVO create(SeminarVO newSeminarVO) {
        newSeminarVO.setId(null);
        return seminarVORepository.save(newSeminarVO);
    }

    /**
     * TO UPDATE A NEW SEMINAR
     * 
     * @param seminarId
     * @param newSeminarVO
     * @return
     * @throws NoSeminarException
     */
    public void update(UUID seminarId, SeminarVO newSeminarVO) throws NoSeminarException {
        getOne(seminarId);
        newSeminarVO.setId(seminarId);
        seminarVORepository.save(newSeminarVO);
    }

    /**
     * TO DELETE A SEMINAR
     * 
     * @param seminarId
     * @throws NoSeminarException
     */
    public void delete(UUID seminarId) throws NoSeminarException {
        SeminarVO seminarFound = getOne(seminarId);

        // to check if the current seminar has a booking before deleting it:
        Set<Booking> bookings = bookingRepository.findAllByCurrentSeminarId(seminarId);
        for (Booking booking : bookings) {
            bookingRepository.delete(booking);
        }

        seminarVORepository.delete(seminarFound);
    }

    
    /**
     * TO ADD PARTICIPANTS TO A SEMINAR
     * 
     * @param seminarId
     * @param participants
     * @throws NoSeminarException
     * @throws NullPointerException
     * @throws IllegalStateException
     */
    public void addParticipants(UUID seminarId, Set<ParticipantVO> participants)
            throws NoSeminarException, NullPointerException, IllegalStateException {

        // some Checking before adding participants:
        SeminarVO seminarVO = getOne(seminarId);

        if (participants.isEmpty()) {
            throw new NullPointerException("participants list must not be empty.");
        }
        if (seminarVO.getState() == StateOfSeminarVO.FINISHED) {
            throw new IllegalStateException(
                    String.format("The Seminar whith the id: %s is finished.", seminarId.toString()));
        }
        if (seminarVO.getState() == StateOfSeminarVO.FULL) {
            throw new IllegalStateException(
                    String.format("The Seminar whith the id: %s is full.", seminarId.toString()));
        }
        if (participants.size() > seminarVO.getMax()) {
            throw new IllegalStateException(
                    String.format("Number of participants (%s) higher than the max (%s) for this seminar.",
                            participants.size(), seminarVO.getMax()));
        }

        // to add participant
        if (seminarVO.getState() == StateOfSeminarVO.AVAILABLE) {
            seminarVO.setParticipants(participants);
            if (participants.size() == seminarVO.getMax()) {
                seminarVO.setState(StateOfSeminarVO.FULL);
            }
        }

        seminarVORepository.save(seminarVO);
    }

    /**
     * TO DELETE PARTICIPANTS OF A SEMINAR
     * 
     * @param seminarId
     * @param participants
     * @throws NoParticipantsException
     * @throws NoSeminarException
     */
    public void deleteParticipants(UUID seminarId, Set<ParticipantVO> participants)
            throws NullPointerException, NoSeminarException, IllegalStateException {

        // some Checking:
        SeminarVO seminarVO = getOne(seminarId);

        if (participants.isEmpty()) {
            throw new NullPointerException("participants list must not be empty.");
        }
        if (seminarVO.getState() == StateOfSeminarVO.FINISHED) {
            throw new IllegalStateException(
                    String.format("The Seminar whith the id: %s is finished.", seminarId.toString()));
        }

        // delete process:
        if (seminarVO.getParticipants().size() > 0) {
            for (ParticipantVO participant : participants) {
                if (seminarVO.getParticipants().contains(participant)) {
                    seminarVO.getParticipants().remove(participant);
                }
            }
            if (seminarVO.getParticipants().size() < seminarVO.getMax() &&
                    seminarVO.getState() == StateOfSeminarVO.FULL) {
                seminarVO.setState(StateOfSeminarVO.AVAILABLE);
            }

            seminarVORepository.save(seminarVO);
        }
    }

}
