package com.example.demo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@Entity
@Table(name = "participant")
public class ParticipantVO implements Serializable, Comparable<ParticipantVO> {

    // ATTRIBUTEN:
    private static final long serialVersionUID = -4441736385404344904L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String name;

    
    
    // KONSTRUKTOREN:

    public ParticipantVO(String name) {
        super();
        setName(name);
    }

    public ParticipantVO() {
        this(null);
    }
    
    
    // Setter und Getter:

    public UUID getId() {
        return id;
    }

    public void setId(UUID iD) {
        this.id = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    // EXTRA METHODE:

    @Override
    public int compareTo(ParticipantVO participant) {
        return getName().compareTo(participant.getName());
    }

}
