package org.project.todo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**

 Represents a user registration request.*
 @param username the unique username chosen by the user
 @param email the user's email address
 @param password the user's raw password (should be hashed before storage)*/
//public record UserRequest(
//        String username,
//        String email,
//        String password
//) {}



public class UserRequest {

    @NotBlank
    private String username;

    @Email(message = "Invalid email go poop yourself")
    @NotBlank
    private String email;

    @Size(min = 4)
    private String password;

    public UserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    protected UserRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
