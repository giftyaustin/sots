package com.memesots.MemesOTS.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.memesots.MemesOTS.dao.UserRepository;
import com.memesots.MemesOTS.models.User;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return passwordEncoder.matches(password, user.get().getPassword());
        } else {
            return false;
        }
    }

    public User saveUser(String username) {
        User user = new User();
        user.setUsername(username);
        User user_obj = userRepository.save(user);
        return user_obj;
    }
}
