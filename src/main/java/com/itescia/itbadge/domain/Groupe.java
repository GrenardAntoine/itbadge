package com.itescia.itbadge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Groupe.
 */
@Entity
@Table(name = "groupe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "nom", length = 30, nullable = false)
    private String nom;

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Utilisateur> listEleves = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "groupe_list_cours",
               joinColumns = @JoinColumn(name = "groupes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "list_cours_id", referencedColumnName = "id"))
    private Set<Cours> listCours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Groupe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Utilisateur> getListEleves() {
        return listEleves;
    }

    public Groupe listEleves(Set<Utilisateur> utilisateurs) {
        this.listEleves = utilisateurs;
        return this;
    }

    public Groupe addListEleve(Utilisateur utilisateur) {
        this.listEleves.add(utilisateur);
        utilisateur.setGroupe(this);
        return this;
    }

    public Groupe removeListEleve(Utilisateur utilisateur) {
        this.listEleves.remove(utilisateur);
        utilisateur.setGroupe(null);
        return this;
    }

    public void setListEleves(Set<Utilisateur> utilisateurs) {
        this.listEleves = utilisateurs;
    }

    public Set<Cours> getListCours() {
        return listCours;
    }

    public Groupe listCours(Set<Cours> cours) {
        this.listCours = cours;
        return this;
    }

    public Groupe addListCours(Cours cours) {
        this.listCours.add(cours);
        cours.getListGroupes().add(this);
        return this;
    }

    public Groupe removeListCours(Cours cours) {
        this.listCours.remove(cours);
        cours.getListGroupes().remove(this);
        return this;
    }

    public void setListCours(Set<Cours> cours) {
        this.listCours = cours;
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
        Groupe groupe = (Groupe) o;
        if (groupe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Groupe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
