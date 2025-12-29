package org.project.todo.user.dto;

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

    private String username;
    private String email;
    private String password;

    public UserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

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
