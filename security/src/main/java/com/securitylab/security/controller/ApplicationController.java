package com.securitylab.security.controller;

import com.securitylab.security.models.ApplicationUser;
import com.securitylab.security.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
//@SpringBootApplication
//@ComponentScan(basePackageClasses = ApplicationController.class)
@Controller
public class ApplicationController {

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignUp(){
        return "signup";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal){
        ApplicationUser applicationUser = appUserRepo.findApplicationUserByUsername(principal.getName());
        model.addAttribute("username",applicationUser);
        return "profile";
    }

    @PostMapping("/signup")
    public RedirectView signupAttempt(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String firstName,
                                      @RequestParam String lastName,
                                      @RequestParam String dob,
                                      @RequestParam String bio){
        ApplicationUser applicationUser = new ApplicationUser(username,encoder.encode(password),firstName,lastName,dob,bio);
        applicationUser = appUserRepo.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser,null,new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }
//    @GetMapping("/profile/{id}")
//    public String getUserByUsername(Model model, @PathVariable Long id){
//        ApplicationUser applicationUser = appUserRepo.findApplicationUserByID(id);
//        model.addAttribute("username", applicationUser);
//        return "profile";
//    }

    @GetMapping("profile/{id}")
    public String getUserById(@PathVariable Long id , Model model){
        model.addAttribute("username" , appUserRepo.findApplicationUserById(id));
        return ("profile");

    }

}
