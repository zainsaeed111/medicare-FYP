package com.example.mediworld;

public class UserHelperClass {

    String username,email,password,phoneno;

    public UserHelperClass() {
    }

    public UserHelperClass(String username, String email, String password, String phoneno) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneno = phoneno;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
