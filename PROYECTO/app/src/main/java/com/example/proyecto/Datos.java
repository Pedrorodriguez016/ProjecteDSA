package com.example.proyecto;

public class Datos {
    public String username;
    public String password;
    public String id;


    public Datos(String username, String password) {
        this.username = username;
        this.password = password;
        this.id=id;
    }




    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
