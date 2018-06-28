package com.itescia.itbadge.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Badgeage.
 */
@Entity
@Table(name = "badgeage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Badgeage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_current_date", nullable = false)
    private LocalDate currentDate;

    @Column(name = "badgeage_eleve")
    private Instant badgeageEleve;

    @Column(name = "badgeage_corrige")
    private Instant badgeageCorrige;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public Badgeage currentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
        return this;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public Instant getBadgeageEleve() {
        return badgeageEleve;
    }

    public Badgeage badgeageEleve(Instant badgeageEleve) {
        this.badgeageEleve = badgeageEleve;
        return this;
    }

    public void setBadgeageEleve(Instant badgeageEleve) {
        this.badgeageEleve = badgeageEleve;
    }

    public Instant getBadgeageCorrige() {
        return badgeageCorrige;
    }

    public Badgeage badgeageCorrige(Instant badgeageCorrige) {
        this.badgeageCorrige = badgeageCorrige;
        return this;
    }

    public void setBadgeageCorrige(Instant badgeageCorrige) {
        this.badgeageCorrige = badgeageCorrige;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Badgeage badgeage = (Badgeage) o;
        if (badgeage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), badgeage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Badgeage{" +
            "id=" + getId() +
            ", currentDate='" + getCurrentDate() + "'" +
            ", badgeageEleve='" + getBadgeageEleve() + "'" +
            ", badgeageCorrige='" + getBadgeageCorrige() + "'" +
            "}";
    }
}
