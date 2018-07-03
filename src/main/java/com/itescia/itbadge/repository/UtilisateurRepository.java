package com.itescia.itbadge.repository;

import com.itescia.itbadge.domain.Groupe;
import com.itescia.itbadge.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Utilisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query(value = "select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.listCours",
        countQuery = "select count(distinct utilisateur) from Utilisateur utilisateur")
    Page<Utilisateur> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.listCours")
    List<Utilisateur> findAllWithEagerRelationships();

    @Query("select utilisateur from Utilisateur utilisateur left join fetch utilisateur.listCours where utilisateur.id =:id")
    Optional<Utilisateur> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select utilisateur from Utilisateur utilisateur where utilisateur.user.login =:login")
    Optional<Utilisateur> findOneByUserLogin(@Param("login") String login);

    List<Utilisateur> findByGroupeContains(Groupe groupe);
}
