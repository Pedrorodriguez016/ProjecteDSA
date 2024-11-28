package com.example.proyecto;



public class DatosRegistro {
    public String username;
    public String password;
    public String email;

    public DatosRegistro(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.email = mail;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
