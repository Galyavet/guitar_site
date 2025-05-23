package com.spring.guitarsite.service.impl;

import com.spring.guitarsite.exception.ResourceNotFoundException;
import com.spring.guitarsite.model.Guitar;
import com.spring.guitarsite.model.GuitarType;
import com.spring.guitarsite.model.User;
import com.spring.guitarsite.repository.GuitarRepository;
import com.spring.guitarsite.repository.UserRepository;
import com.spring.guitarsite.service.GuitarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class GuitarServiceImpl implements GuitarService {

    private final GuitarRepository guitarRepository;

    private final UserRepository userRepository;

    @Override
    public Page<Guitar> findByFilters(List<String> types, List<String> brands, int page, int size) {
        Specification<Guitar> spec = Specification.where(null);

        if (types != null && !types.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("type").get("description").in(types));
        }

        if (brands != null && !brands.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("brand").in(brands));
        }

        return guitarRepository.findAll(spec, PageRequest.of(page, size));
    }

    @Override
    public List<String> findAllTypeNames() {
        return guitarRepository.findDistinctTypeNames();
    }

    @Override
    public List<String> findAllBrands() {
        return guitarRepository.findDistinctBrands();
    }

    @Override
    public List<GuitarType> findAllUsedTypes() {
        return guitarRepository.findAllUsedTypes();
    }

    @Override
    public List<Guitar> findAllSorted(Sort sort) {
        return guitarRepository.findAll(sort);
    }

    @Override
    public List<Guitar> getSimilarGuitarsByType(GuitarType type, Long excludeId) {
        return guitarRepository.findByTypeAndIdNot(type, excludeId);
    }

    @Override
    public List<Guitar> getAllGuitars() {
        return guitarRepository.findAll();
    }

    @Override
    public Guitar getGuitarById(Long id) {
        return guitarRepository.findWithTypeById(id)
                .orElseThrow(() -> new EntityNotFoundException("Guitar not found with id: " + id));
    }

    @Override
    public List<Guitar> getByType(GuitarType type) {
        return guitarRepository.findByType(type);
    }

    @Transactional
    @Override
    public void updateGuitar(Guitar guitar) {
        guitarRepository.save(guitar);
    }
    @Transactional
    @Override
    public void deleteGuitar(Long id) {
        guitarRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Guitar saveGuitar(Guitar guitar) {
        return guitarRepository.save(guitar);
    }

    @Override
    @Transactional
    public void toggleFavoriteGuitar(Long guitarId, User user) {
        Guitar guitar = guitarRepository.findById(guitarId)
                .orElseThrow(() -> new EntityNotFoundException("Guitar not found"));

        if (user.getFavoriteGuitars().contains(guitar)) {
            user.getFavoriteGuitars().remove(guitar);
        } else {
            user.getFavoriteGuitars().add(guitar);
        }

        userRepository.save(user);
    }

}
