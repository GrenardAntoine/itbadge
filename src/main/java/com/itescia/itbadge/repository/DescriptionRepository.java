package com.itescia.itbadge.repository;

import com.itescia.itbadge.domain.Description;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Description entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {

}
