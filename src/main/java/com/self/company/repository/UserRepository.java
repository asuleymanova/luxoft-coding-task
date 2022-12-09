package com.self.company.repository;

import com.self.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Override
    void deleteById(String userId);
}
