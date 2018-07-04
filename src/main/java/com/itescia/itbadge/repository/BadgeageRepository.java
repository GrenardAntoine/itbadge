package com.itescia.itbadge.repository;

import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Badgeage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeageRepository extends JpaRepository<Badgeage, Long> {

    Page<Badgeage> findByUtilisateur(Utilisateur utilisateur, Pageable pageable);

    Page<Badgeage> findByUtilisateurAndCurrentDate(Utilisateur utilisateur, LocalDate currentDate, Pageable pageable);

    Optional<Badgeage> findByUtilisateurAndCurrentDate(Utilisateur utilisateur, LocalDate currentDate);

    Optional<Badgeage> findByUtilisateur(Utilisateur utilisateur);

    @Query("select badgeage from Badgeage badgeage where badgeage.utilisateur = ?1 and (badgeage.badgeageEleve > ?2 or badgeage.badgeageCorrige > ?2)")
    Optional<Badgeage> findIfBadgeageExist(Utilisateur utilisateur, Instant currentDate);
}
