package com.itescia.itbadge.repository;

import com.itescia.itbadge.domain.Cours;
import com.itescia.itbadge.domain.Groupe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data  repository for the Groupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {

    @Query(value = "select distinct groupe from Groupe groupe left join fetch groupe.listCours",
        countQuery = "select count(distinct groupe) from Groupe groupe")
    Page<Groupe> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct groupe from Groupe groupe left join fetch groupe.listCours")
    List<Groupe> findAllWithEagerRelationships();

    @Query("select groupe from Groupe groupe left join fetch groupe.listCours where groupe.id =:id")
    Optional<Groupe> findOneWithEagerRelationships(@Param("id") Long id);

    Set<Groupe> findByListCoursContains(Cours cours);

    @Query(value ="select groupe from Groupe groupe left join fetch groupe.listEleves as eleve left join fetch eleve.listBageages badgeage " +
        "where badgeage.currentDate =:day and groupe.id =:groupeid",
        countQuery = "select count(distinct groupe) from Groupe groupe")
    Page<Groupe> findBadgeageGroupe(Pageable pageable, @Param("day") LocalDate day, @Param("groupeid") Long groupeId);
}
