package com.spring.guitarsite.service;

import com.spring.guitarsite.model.Guitar;
import com.spring.guitarsite.model.GuitarType;
import com.spring.guitarsite.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface GuitarService {

    Page<Guitar> findByFilters(List<String> types, List<String> brands, int page, int size);

    List<String> findAllTypeNames();

    List<String> findAllBrands();

    List<GuitarType> findAllUsedTypes();

    List<Guitar> findAllSorted(Sort sort);

    List<Guitar> getSimilarGuitarsByType(GuitarType type, Long excludeId);

    Guitar getGuitarById(Long id);

    List<Guitar> getAllGuitars();

    List<Guitar> getByType(GuitarType type);

    Guitar saveGuitar(Guitar guitar);

    void updateGuitar(Guitar guitar);

    void deleteGuitar(Long id);

    void toggleFavoriteGuitar(Long guitarId, User user);
}
