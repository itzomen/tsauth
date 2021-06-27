package com.omen.tsauth.services;


import java.util.List;

import com.omen.tsauth.domain.Category;
import com.omen.tsauth.exceptions.BadRequestException;
import com.omen.tsauth.exceptions.ResourceNotFoundException;

public interface CategoryService {
    List<Category> fetchAllCategories(Integer userId);

    Category fetchCategoryById(Integer userId, Integer categoryId) throws ResourceNotFoundException;

    Category addCategory(Integer userId, String title, String description) throws BadRequestException;

    void updateCategory(Integer userId, Integer categoryId, Category category) throws BadRequestException;

    void removeCategory(Integer userId, Integer categoryId) throws ResourceNotFoundException;
}
