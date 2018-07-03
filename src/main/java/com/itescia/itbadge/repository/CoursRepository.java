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

    //@Query("select cours from Cours cours where cours.listProfesseurs =?1 and cours.dateDebut like ?2%");
    Optional<Cours> findOneByListProfesseursContainsAndDateDebut(Utilisateur professeur, Instant dateDebut);

    Optional<Cours> findOneByListGroupesContainsAndDateDebut(Groupe groupes, Instant dateDebut);

    Page<Cours> findByListProfesseurs(Set<Utilisateur> professeur, Pageable pageable);

    @Query("select distinct cours from Cours cours where cours.listProfesseurs =?1 ")
    Page<Cours> findByListProfesseursUnique(Set<Utilisateur> professeur, Pageable pageable);
}
