package com.memesots.MemesOTS.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.memesots.MemesOTS.models.User;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private com.memesots.MemesOTS.dao.UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userObj = UserRepository.findByUsername(username);
        if(!userObj.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = userObj.get();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        return userDetails;
    }


    public UserDetails loadUserByEmail (String email){
        Optional<User> userObj = UserRepository.findByEmail(email);
        if(!userObj.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = userObj.get();
       AppUserDetails userDetails = new AppUserDetails();
       userDetails.setId(user.getId());
       userDetails.setUsername(user.getUsername());
       userDetails.setPassword(user.getPassword());
       userDetails.setEmail(email);
       return userDetails;
    }
    
}
