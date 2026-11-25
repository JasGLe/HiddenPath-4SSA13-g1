package com.hiddenpath.experience.controllers;

import com.hiddenpath.experience.dto.CategoryRequest;
import com.hiddenpath.experience.dto.CategoryResponse;
import com.hiddenpath.experience.entities.Category;
import com.hiddenpath.experience.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryService.create(request.name());
        return toResponse(category);
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
