package com.example.proyecto;

public class Datos {
    public String name;
    public String password;


    public Datos(String username, String password) {
        this.name = username;
        this.password = password;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.name = username;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return name;
    }
}
