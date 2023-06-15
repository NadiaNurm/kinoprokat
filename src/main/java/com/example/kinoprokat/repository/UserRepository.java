package com.example.kinoprokat.repository;

import com.example.kinoprokat.model.Film;
import com.example.kinoprokat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByUsername(String username);

    public User getUserByUsername(String username);

    public User getUserById(Long id);
}
