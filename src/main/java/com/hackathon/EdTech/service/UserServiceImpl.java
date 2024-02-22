package com.hackathon.EdTech.service;

import com.hackathon.EdTech.dao.UserRepository;
import com.hackathon.EdTech.dto.UserRegisterationDto;
import com.hackathon.EdTech.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User registerNewUser(UserRegisterationDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("ROLE_USER");
        user.setBoard("CBSE");
        user.setStandard("12");
        user.setImageUrl("default.png");
        return userRepository.save(user);
    }
}
