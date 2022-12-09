package com.self.company.service;

import com.self.company.model.User;

public interface UserService {
    User findById(String id);
    void deleteById(String id);
}
