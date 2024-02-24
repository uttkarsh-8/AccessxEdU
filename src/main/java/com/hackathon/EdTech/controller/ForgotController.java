package com.hackathon.EdTech.controller;

import com.hackathon.EdTech.dao.UserRepository;
import com.hackathon.EdTech.entities.User;
import com.hackathon.EdTech.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgotController {

    Random random = new Random(1000);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // email id form open handler
    @GetMapping("/forgot")
    public String openEmailForm(){
        return "forgot_email_form";
    }

    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email")String email, HttpSession httpSession, Model model){

        model.addAttribute("title", "Forgot Password - EdTech");
        System.out.println("EMAIL: "+ email);

        //generating OTP of 4 digits
        int otp = random.nextInt(99999);

        System.out.println("OTP: "+otp);

        // code for sending OTP to email
        String subject = "OTP From SCM";
        String message = " OTP = " + otp + ".";
        String to = email;

        boolean flag = emailService.sendEmail(subject, message, to);

        if (flag){

            httpSession.setAttribute("myotp",otp);
            httpSession.setAttribute("email", email);
            return "verify-otp";
        }else{
            httpSession.setAttribute("message", "Check your email id!!");
            return "forgot_email_form";

        }
    }

    // verify otp
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp")int otp, HttpSession httpSession){

        int myotp = (int)httpSession.getAttribute("myotp");
        String email = (String)httpSession.getAttribute("email");
        if (myotp==otp){
            // password change form
            User user = userRepository.getUserByUserName(email);

            if (user==null){
                // error message
                httpSession.setAttribute("message", "No user with this email!!");
                return "forgot_email_form";
            }else{
                //send change password form
                return "password_change_form";
            }
        }else{
            httpSession.setAttribute("message", "You have entered wrong otp");
            return "verify-otp";
        }

    }
    // change password
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword")String newpassword, HttpSession httpSession, Model model){

        model.addAttribute("title", "Change Password - EdTech");

        String email = (String)httpSession.getAttribute("email");
        User user = userRepository.getUserByUserName(email);
        user.setPassword(bCryptPasswordEncoder.encode(newpassword));
        userRepository.save(user);

        return "redirect:/signin?change=new password created";
    }
}
