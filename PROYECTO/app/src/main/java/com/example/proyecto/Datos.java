package com.example.proyecto;

public class Datos {
    public String username;
    public String password;


    public Datos(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
