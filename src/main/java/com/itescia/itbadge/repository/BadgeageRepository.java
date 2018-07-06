package com.itescia.itbadge.repository;

import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the Badgeage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeageRepository extends JpaRepository<Badgeage, Long> {

    Page<Badgeage> findByUtilisateur(Utilisateur utilisateur, Pageable pageable);

    Page<Badgeage> findByUtilisateurAndCurrentDate(Utilisateur utilisateur, LocalDate currentDate, Pageable pageable);

    Optional<Badgeage> findByUtilisateur(Utilisateur utilisateur);

    List<Badgeage> findByUtilisateurAndCurrentDate(Utilisateur utilisateur, LocalDate localNow);
}
