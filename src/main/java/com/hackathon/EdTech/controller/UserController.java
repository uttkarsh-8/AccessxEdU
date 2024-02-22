package com.hackathon.EdTech.controller;

import com.hackathon.EdTech.dao.UserRepository;
import com.hackathon.EdTech.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @ModelAttribute //method to add common data to response
    public void addCommonData(Model model, Principal principal){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        model.addAttribute("user", user);
    }

    @GetMapping("/index")
    public String dashboard(Model model){

        return "normal/user_dashboard";
    }

    @GetMapping("/premium")
    public String premium(Model model){
        model.addAttribute("title", "Premium Subscription");

        return "normal/premium";
    }
}
