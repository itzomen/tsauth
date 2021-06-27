package com.omen.tsauth.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;

import com.omen.tsauth.domain.Category;
import com.omen.tsauth.exceptions.BadRequestException;
import com.omen.tsauth.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImp implements CategoryRepository {

    private static final String SQL_FIND_ALL = "SELECT * FROM CATEGORIES WHERE USER_ID = ? GROUP BY CATEGORY_ID";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM CATEGORIES WHERE USER_ID = ? AND CATEGORY_ID = ? GROUP BY CATEGORY_ID";
    private static final String SQL_CREATE = "INSERT INTO CATEGORIES (CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) VALUES(NEXTVAL('CATEGORIES_SEQ'), ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE CATEGORIES SET TITLE = ?, DESCRIPTION = ? "
            + "WHERE USER_ID = ? AND CATEGORY_ID = ?";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM CATEGORIES WHERE USER_ID = ? AND CATEGORY_ID = ?";
    // private static final String SQL_DELETE_ALL_TRANSACTIONS = "DELETE FROM
    // TRANSACTIONS WHERE CATEGORY_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) throws ResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[] { userId }, categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws ResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId}, categoryRowMapper);
        }catch (Exception e) {
            throw new ResourceNotFoundException("Category not found" + e);
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws BadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");
        }catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws BadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE,
                    new Object[] { category.getTitle(), category.getDescription(), userId, categoryId });
        } catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {
        // this.removeAllCatTransactions(categoryId);
        jdbcTemplate.update(SQL_DELETE_CATEGORY, new Object[] { userId, categoryId });
    }

    // private void removeAllCatTransactions(Integer categoryId) {
    // jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTIONS, new Object[]{categoryId});
    // }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(rs.getInt("CATEGORY_ID"), rs.getInt("USER_ID"), rs.getString("TITLE"),
                rs.getString("DESCRIPTION"));
    });

}