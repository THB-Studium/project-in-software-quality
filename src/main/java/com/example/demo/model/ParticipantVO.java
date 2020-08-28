package com.example.demo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "participant")
public class ParticipantVO implements Serializable {

    // ATTRIBUTEN:
    private static final long serialVersionUID = -4441736385404344904L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String name;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "seminar_id", nullable = false)
    private SeminarVO currentSeminar;

    // KONSTRUKTOREN:

    public ParticipantVO(String name, SeminarVO currentSeminar) {
        super();
        setName(name);
        setCurrentSeminar(currentSeminar);
    }

    public ParticipantVO() {
        this(null, null);
    }

    // EXTRA METHODEN:

    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ParticipantVO other = (ParticipantVO) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
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

    public SeminarVO getCurrentSeminar() {
        return currentSeminar;
    }

    public void setCurrentSeminar(SeminarVO currentSeminar) {
        this.currentSeminar = currentSeminar;
    }

}
