package com.itescia.itbadge.repository;

import com.itescia.itbadge.domain.Cours;
import com.itescia.itbadge.domain.Groupe;
import com.itescia.itbadge.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the Cours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    //@Query("select cours from Cours cours where (?1 in (select listprofes.utilisateurs_id from utilisateur_list_cours listprofes where cours.id=listprofes.list_cours_id)) and (cours.dateDebut > ?2 and cours.dateFin < ?2) ")
    Optional<Cours> findOneByListProfesseursContainsAndDateDebutBeforeAndDateFinAfter(Utilisateur listProfesseur,Instant dateDebut,Instant dateFin);

    Optional<Cours> findOneByListGroupesContainsAndDateDebutBeforeAndDateFinAfter(Groupe groupes, Instant dateDebut, Instant dateFin);

    Page<Cours> findByListProfesseursContains(Utilisateur professeur, Pageable pageable);

    Page<Cours> findDistinctByListProfesseursContains(Utilisateur professeur, Pageable pageable);
}
