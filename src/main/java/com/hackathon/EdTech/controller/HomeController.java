package com.hackathon.EdTech.controller;

import com.hackathon.EdTech.dao.UserRepository;
import com.hackathon.EdTech.dto.UserRegisterationDto;
import com.hackathon.EdTech.entities.User;
import com.hackathon.EdTech.service.UserService;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.razorpay.*;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home - EdTech");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - EDTech");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Register - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String register( @Valid @ModelAttribute("user") UserRegisterationDto userDto, BindingResult result1, Model model) {
        try {
            if (result1.hasErrors()) {
                System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR" + result1);
                model.addAttribute("user", userDto); // to keep the data in the form
                return "signup";
            }
            userService.registerNewUser(userDto);
            model.addAttribute("user", new UserRegisterationDto()); // to clear the form
            model.addAttribute("success", "User has been registered successfully"); // to show user is successfully registered
            return "signup";

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            model.addAttribute("user", userDto); // to keep the data in the form
            model.addAttribute("duplicateEmail", "Email already exists");
            return "signup";
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An unexpected error occurred!! Please try again later.");
            return "signup";
        }
    }

    @GetMapping("/signin")
    public String customLogin(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }


    @GetMapping("/premium")
    public String premium(Model model){
        model.addAttribute("title", "Donate us!!");

        return "normal/premium";
    }

    // creating order for payment
    @PostMapping("/premium/create_order")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object>data) throws RazorpayException {
        System.out.println("Order function executedj");
        System.out.println(data);
        int amt = Integer.parseInt(data.get("amount").toString());

        var client = new RazorpayClient("rzp_test_N5w4vDygKhUrXc", "Z6wSxS00VRsaVk5HR2F2GzWi");

        JSONObject object = new JSONObject();
        object.put("amount", amt*100);
        object.put("currency", "INR");
        object.put("receipt", "txn_235425");

        // creating new order
        Order order = client.orders.create(object);
        System.out.println(order);

        return order.toString();
    }

}
