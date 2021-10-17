package com.securitylab.security.repository;

import com.securitylab.security.models.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    AppUserRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = appUserRepo.findApplicationUserByUsername(username);
        if (applicationUser == null){
            throw new UsernameNotFoundException((username + " not found Try Again Later!"));
        }
        return applicationUser;
    }
}
