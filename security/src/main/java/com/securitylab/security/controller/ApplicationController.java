package com.securitylab.security.controller;

import com.securitylab.security.models.ApplicationUser;
import com.securitylab.security.models.Post;
import com.securitylab.security.repository.AppUserRepo;
import com.securitylab.security.repository.PostRepo;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@SpringBootApplication
//@ComponentScan(basePackageClasses = ApplicationController.class)
@Controller
public class ApplicationController {

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    PostRepo postRepo;

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

    @GetMapping("profile/{id}")
    public String getUserById(@PathVariable Long id , Model model){
        model.addAttribute("username" , appUserRepo.findApplicationUserById(id));
        return ("profile");
    }

    @GetMapping("/posts")
    public String getPosts(){
        return "posts";
    }

    @PostMapping("/posts")
    public RedirectView createPost(Model model, Principal principal, String body){
        ApplicationUser applicationUser = appUserRepo.findApplicationUserByUsername(principal.getName());
        if (applicationUser != null){
            Date date = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
            Post post= new Post (body, ft.format(date),applicationUser);
            postRepo.save(post);
        }
        model.addAttribute("username",applicationUser.getPosts());
        return new RedirectView("/profile");
    }


    @GetMapping("/following")
    public String getFollowing(Model model, Principal principal){
        ApplicationUser applicationUser = appUserRepo.findApplicationUserByUsername(principal.getName());
        Iterable<ApplicationUser> username = applicationUser.getFollowing();
        model.addAttribute("username", username);
        return "following";
    }

    @PostMapping("/follow/{id}")
    public RedirectView followingUser(Principal principal, @PathVariable Long id){
//        TO get the Logged-In Users
        ApplicationUser applicationUser = appUserRepo.findApplicationUserByUsername(principal.getName());
        ApplicationUser applicationUserFollow = appUserRepo.findApplicationUserById(id);

        applicationUser.getFollowing().add(applicationUserFollow);
        applicationUserFollow.getFollowers().add(applicationUser);
        appUserRepo.save(applicationUser);
        appUserRepo.save(applicationUserFollow);
        return new RedirectView("/profile/{id}");
    }

    @GetMapping("/feed")
    public String getPostFeed(Principal principal, Model model){
        ApplicationUser applicationUser = appUserRepo.findApplicationUserByUsername(principal.getName());
        List<ApplicationUser> following = applicationUser.getFollowing();
        List<Post> posts = postRepo.findByApplicationUserIn(following);
        model.addAttribute("posts",posts);
        model.addAttribute("applicationUser",applicationUser.getPosts());
        return "feed";
    }



}
