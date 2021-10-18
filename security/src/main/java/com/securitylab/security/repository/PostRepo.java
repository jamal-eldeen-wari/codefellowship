package com.securitylab.security.repository;

import com.securitylab.security.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
//   Post findPostByUsername(String username);

}
