package com.example.proyecto;



public class DatosRegistro {
    public String username;
    public String password;
    public String mail;

    public DatosRegistro(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.mail= mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
