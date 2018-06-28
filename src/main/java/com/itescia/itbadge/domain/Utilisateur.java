package com.itescia.itbadge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @NotNull
    @Column(name = "is_professeur", nullable = false)
    private Boolean isProfesseur;

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Badgeage> listBageages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("listEleves")
    private Groupe groupe;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "utilisateur_list_cours",
               joinColumns = @JoinColumn(name = "utilisateurs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "list_cours_id", referencedColumnName = "id"))
    private Set<Cours> listCours = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAdmin() {
        return isAdmin;
    }

    public Utilisateur isAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean isIsProfesseur() {
        return isProfesseur;
    }

    public Utilisateur isProfesseur(Boolean isProfesseur) {
        this.isProfesseur = isProfesseur;
        return this;
    }

    public void setIsProfesseur(Boolean isProfesseur) {
        this.isProfesseur = isProfesseur;
    }

    public Set<Badgeage> getListBageages() {
        return listBageages;
    }

    public Utilisateur listBageages(Set<Badgeage> badgeages) {
        this.listBageages = badgeages;
        return this;
    }

    public Utilisateur addListBageage(Badgeage badgeage) {
        this.listBageages.add(badgeage);
        badgeage.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeListBageage(Badgeage badgeage) {
        this.listBageages.remove(badgeage);
        badgeage.setUtilisateur(null);
        return this;
    }

    public void setListBageages(Set<Badgeage> badgeages) {
        this.listBageages = badgeages;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Utilisateur groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Set<Cours> getListCours() {
        return listCours;
    }

    public Utilisateur listCours(Set<Cours> cours) {
        this.listCours = cours;
        return this;
    }

    public Utilisateur addListCours(Cours cours) {
        this.listCours.add(cours);
        cours.getListProfesseurs().add(this);
        return this;
    }

    public Utilisateur removeListCours(Cours cours) {
        this.listCours.remove(cours);
        cours.getListProfesseurs().remove(this);
        return this;
    }

    public void setListCours(Set<Cours> cours) {
        this.listCours = cours;
    }

    public User getUser() {
        return user;
    }

    public Utilisateur user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Utilisateur utilisateur = (Utilisateur) o;
        if (utilisateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), utilisateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", isAdmin='" + isIsAdmin() + "'" +
            ", isProfesseur='" + isIsProfesseur() + "'" +
            "}";
    }
}
