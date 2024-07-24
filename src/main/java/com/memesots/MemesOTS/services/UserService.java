package com.memesots.MemesOTS.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.memesots.MemesOTS.ExceptionHandlers.AppException.UserNotFoundException;
import com.memesots.MemesOTS.dao.UserRepository;
import com.memesots.MemesOTS.dao.UserRepositoryHQL;
import com.memesots.MemesOTS.dto.UserDTO;
import com.memesots.MemesOTS.models.Post;
import com.memesots.MemesOTS.models.User;

import lombok.Data;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryHQL userRepositoryHQL;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
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

    public boolean doesUserExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    // ============ Gets user details ==============
    public GetUserDTO getUser(String email) throws UserNotFoundException {
        Optional<UserDTO> user = Optional.ofNullable(userRepositoryHQL.findUserByEmail(email));
        if (!user.isPresent()) {
            return null;
        }
        UserDTO currUser = user.get();
        List<Post> userPosts = userRepositoryHQL.findUserPostsById(currUser.getId());
        GetUserDTO result = new GetUserDTO();
        result.setUser(currUser);
        result.setUser_posts(userPosts);
        return result;

    }

    @Data
    public static class GetUserDTO {
        private UserDTO user;
        private List<Post> user_posts;
    }

    // =====================================
}
