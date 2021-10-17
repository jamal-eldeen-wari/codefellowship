package com.securitylab.security.repository;
import com.securitylab.security.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findApplicationUserByUsername(String username);
}
