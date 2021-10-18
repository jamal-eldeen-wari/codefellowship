package com.securitylab.security.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "applicationUser_id")
    private ApplicationUser applicationUser;


    public Post(String body, String createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public Post(String body, String createdAt, ApplicationUser applicationUser) {
        this.body = body;
        this.createdAt = createdAt;
        this.applicationUser = applicationUser;
    }

    public Post() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public Long getId() {
        return id;
    }
}
