package com.hiddenpath.experience.services;

import com.hiddenpath.experience.entities.Category;
import com.hiddenpath.experience.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom de la categorie est obligatoire.");
        }

        Category category = new Category();
        category.setName(name.trim());

        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
