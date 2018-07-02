package com.itescia.itbadge.repository;

import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Badgeage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeageRepository extends JpaRepository<Badgeage, Long> {

    Page<Badgeage> findByUtilisateur(Utilisateur utilisateur, Pageable pageable);
}
