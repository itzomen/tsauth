package com.omen.tsauth.repositories;

import com.omen.tsauth.domain.User;
import com.omen.tsauth.exceptions.AuthException;

public interface UserRepository {

    Integer create(String firstName, String lastName, String email, String password) throws AuthException;

    User findByEmailAndPassword(String email, String password) throws AuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);
    
}
