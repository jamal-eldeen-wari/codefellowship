package com.securitylab.security.repository;

import com.securitylab.security.models.ApplicationUser;
import com.securitylab.security.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
//   Post findPostByUsername(String username);
//List<Post> findByAppUserIn(List<ApplicationUser> applicationUserList);
    List<Post> findByApplicationUserIn(List<ApplicationUser> applicationUserList);
}
