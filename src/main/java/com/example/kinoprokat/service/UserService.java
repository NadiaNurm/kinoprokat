package com.example.kinoprokat.service;


import com.example.kinoprokat.model.Roles;
import com.example.kinoprokat.model.User;
import com.example.kinoprokat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    FilmService filmService;
    @Autowired
    UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void addUser(String username, String password) {
        List<Roles> userRole = List.of(Roles.user);
        boolean status = true;
        User user = new User(username, password, userRole, status);
        userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public void userBane(Long id) {
        User user = userRepository.getUserById(id);
        user.setStatus(!user.isStatus());
        userRepository.save(user);
    }

    public void updateUser(User user, String username, String password, String role) {
        user.setUsername(username);
        List<Roles> roles = new ArrayList<>();
        roles.add(Roles.valueOf(role));
        user.setRole(roles);
        user.setPassword(password);
        user.setStatus(true);
        userRepository.save(user);
    }
}
