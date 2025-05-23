package com.spring.guitarsite.repository;

import com.spring.guitarsite.model.Guitar;
import com.spring.guitarsite.model.GuitarType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface GuitarRepository extends JpaRepository<Guitar, Long>, JpaSpecificationExecutor<Guitar> {

    List<Guitar> findByType(GuitarType type);

    @Query("SELECT DISTINCT t.description FROM Guitar g JOIN g.type t")
    List<String> findDistinctTypeNames();

    @Query("SELECT DISTINCT g.brand FROM Guitar g WHERE g.brand IS NOT NULL")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT t FROM GuitarType t JOIN t.guitars g")
    List<GuitarType> findAllUsedTypes();

    @EntityGraph(attributePaths = {"type"})
    Optional<Guitar> findWithTypeById(Long id);

    List<Guitar> findByTypeAndIdNot(GuitarType type, Long id);
}
