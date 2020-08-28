package com.example.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.demo.constant.StateOfSeminarVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "seminar")
public class SeminarVO implements Serializable {

    // ATTRIBUTEN:
    private static final long serialVersionUID = 8012589586024669260L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String name;
    private int max;
    private StateOfSeminarVO state;

    @OneToMany(mappedBy = "currentSeminar", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Booking> booking;
    @OneToMany(mappedBy = "currentSeminar", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ParticipantVO> participants;

    // KONSTRUKTOREN:

    public SeminarVO(String name, StateOfSeminarVO state, int max) {
        super();
        setName(name);
        setMax(max);
        setState(state);

        setBooking(new HashSet<>());
        setParticipants(new HashSet<>());
    }

    public SeminarVO(String name) {
        this(name, StateOfSeminarVO.NOTAVAILABLE, 0);
    }

    public SeminarVO() {
        this(null);
    }

    // Setter und Getter

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateOfSeminarVO getState() {
        return state;
    }

    public void setState(StateOfSeminarVO state) {
        this.state = state;
    }

    public void setBooking(Set<Booking> booking) {
        this.booking = booking;
    }

    public Set<Booking> getBooking() {
        return booking;
    }

    public Set<ParticipantVO> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<ParticipantVO> participants) {
        this.participants = participants;
    }

}
