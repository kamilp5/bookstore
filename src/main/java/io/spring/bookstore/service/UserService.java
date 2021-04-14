package io.spring.bookstore.service;

import io.spring.bookstore.model.User;
import io.spring.bookstore.model.UserRole;
import io.spring.bookstore.repository.UserRepository;
import io.spring.bookstore.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository,UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public boolean isEmailAvailable(String email){
        User user = userRepository.findByEmail(email);
        return user == null;

    }
    public User registerUser(User user){
        if(!isEmailAvailable(user.getEmail())){
            return user;
        }
        if(user.getId() == null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserRole userRole = userRoleRepository.findByRole("ROLE_USER");
            user.getRoles().add(userRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
        return user;
    }
    public User updateUser(User user){
        return userRepository.save(user);
    }
    public User findUserByUsername(String name){
        return userRepository.findByEmail(name);
    }

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }

    public Boolean isUserLogged(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !auth.getName().equals("anonymousUser");
    }


}
