package com.example.demo.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ParticipantVO;
import com.example.demo.repository.ParticipantVORepository;
import com.example.demo.service.exception.ResourceNotFoundException;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@Transactional(rollbackOn = Exception.class)
@Service
public class ParticipantService {

    @Autowired
    private ParticipantVORepository participantVORepository;

    
    
    /**
     * TO GET ALL PARTICIPANTS
     * 
     * @return
     */
    public Set<ParticipantVO> listAll() {
        return participantVORepository.findAll().stream().collect(Collectors.toSet());
    }

    /**
     * TO GET ONE PARTICIPANT BY PARTICIPANT´S ID
     * 
     * @param participantId
     * @return
     */
    public ParticipantVO getOne(UUID participantId) {
        Optional<ParticipantVO> participantOp = participantVORepository.findById(participantId);
        if (participantOp.isPresent()) {
            return participantOp.get();
        } else {
            throw new ResourceNotFoundException(
                    String.format("A participant with the id %s does not exist", participantId.toString())
                    );
        }
    }

    /**
     * TO CREATE A NEW PARTICIPANT
     * 
     * @param newParticipantVO
     * @return
     */
    public ParticipantVO create(ParticipantVO newParticipantVO) {
        newParticipantVO.setId(null);
        return participantVORepository.save(newParticipantVO);
    }

    /**
     * TO UPDATE A NEW PARTICIPANT
     * 
     * @param participantId
     * @param newParticipantVO
     */
    public void update(UUID participantId, ParticipantVO update) {
        getOne(participantId);
        update.setId(participantId);
        participantVORepository.save(update);
    }

    /**
     * TO DELETE A PARTICIPANT
     * 
     * @param participantId
     */
    public void delete(UUID participantId) {
        ParticipantVO participantFound = getOne(participantId);
        participantVORepository.delete(participantFound);
    }

}
