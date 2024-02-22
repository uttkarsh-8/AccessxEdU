package com.hackathon.EdTech.service;

import com.hackathon.EdTech.dto.UserRegisterationDto;
import com.hackathon.EdTech.entities.User;

public interface UserService {
    User registerNewUser(UserRegisterationDto userDto);
}
