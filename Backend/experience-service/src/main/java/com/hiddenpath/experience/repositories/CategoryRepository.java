package com.hiddenpath.experience.repositories;

import com.hiddenpath.experience.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
