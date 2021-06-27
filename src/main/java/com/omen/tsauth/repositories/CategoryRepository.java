package com.omen.tsauth.repositories;

import java.util.List;

import com.omen.tsauth.domain.Category;
import com.omen.tsauth.exceptions.BadRequestException;
import com.omen.tsauth.exceptions.ResourceNotFoundException;

public interface CategoryRepository {
    
    List<Category> findAll(Integer userId) throws ResourceNotFoundException;

    Category findById(Integer userId, Integer categoryId) throws ResourceNotFoundException;

    Integer create(Integer userId, String title, String description) throws BadRequestException;

    void update(Integer userId, Integer categoryId, Category category) throws BadRequestException;

    void removeById(Integer userId, Integer categoryId);
}
