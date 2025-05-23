package com.spring.guitarsite.service.impl;

import com.spring.guitarsite.model.GuitarType;
import com.spring.guitarsite.repository.GuitarTypeRepository;
import com.spring.guitarsite.service.GuitarTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuitarTypeServiceImpl implements GuitarTypeService {

    private final GuitarTypeRepository guitarTypeRepository;

    public GuitarTypeServiceImpl(GuitarTypeRepository guitarTypeRepository) {
        this.guitarTypeRepository = guitarTypeRepository;
    }

    @Override
    public List<GuitarType> findAll() {
        return guitarTypeRepository.findAll();
    }

    @Override
    public GuitarType findById(Long id) {
        return guitarTypeRepository.findById(id).get();
    }

}
