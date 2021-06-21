package com.omen.tsauth.services;

import com.omen.tsauth.domain.User;
import com.omen.tsauth.exceptions.AuthException;

public interface UserService {
    
    User validateUser(String email, String password) throws AuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws AuthException;
}
