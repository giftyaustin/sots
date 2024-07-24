package com.memesots.MemesOTS.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.memesots.MemesOTS.ExceptionHandlers.AppException.UserNotFoundException;
import com.memesots.MemesOTS.dto.UserDTO;
import com.memesots.MemesOTS.models.Post;
import com.memesots.MemesOTS.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserRepositoryHQL {

    @Autowired
    private EntityManager entityManager;

    public UserDTO findUserByEmail(String email) throws UserNotFoundException {
        try {
            TypedQuery<User> query = entityManager
                    .createQuery("select u from User u where u.email = :email",
                            User.class);
            User result = query
                    .setParameter("email", email)
                    .getSingleResult();

            if (result == null) {
                return null;
            }

            UserDTO user = new UserDTO();
            user.setId(result.getId());
            user.setUsername(result.getUsername());
            user.setEmail(result.getEmail());
            user.setProfile_pic(result.getProfile_pic());
            user.setSignup_service(result.getSignup_service());
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException();
        }

    }

    public List<Post> findUserPostsById(Integer userID) {
        User user = new User();
        user.setId(userID);
        TypedQuery<Post> query = entityManager.createQuery("select p from Post p where p.userID = :userID", Post.class);
        System.out.println(query);
        return query.setParameter("userID", user).getResultList();
    }
}
