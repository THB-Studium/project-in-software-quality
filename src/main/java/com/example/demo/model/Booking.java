package com.example.demo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    // ATTRIBUTEN:
    private static final long serialVersionUID = -1067422671019089077L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "seminarId", nullable = false)
    private SeminarVO currentSeminar;

    // KONSTRUKTOREN:

    public Booking(SeminarVO currentSeminar) {
        super();
        this.setCurrentSeminar(currentSeminar);
    }

    public Booking() {
        this(null);
    }

    // Setter/Getter:

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SeminarVO getCurrentSeminar() {
        return currentSeminar;
    }

    public void setCurrentSeminar(SeminarVO currentSeminar) {
        this.currentSeminar = currentSeminar;
    }

}
