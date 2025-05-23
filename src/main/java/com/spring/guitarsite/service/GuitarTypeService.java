package com.spring.guitarsite.service;

import com.spring.guitarsite.model.GuitarType;

import java.util.List;
import java.util.Optional;

public interface GuitarTypeService {
    List<GuitarType> findAll();

    GuitarType findById(Long id);
}
