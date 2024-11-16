package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"id", "username", "password", "email", "birthday", "inventory"})
public class User {

    Integer id;
    String username;
    String password;
    String email;
    String birthday;
    List<Item> inventory;

    public User() {
        this.inventory = new ArrayList<>();
    }

    public User(Integer id, String username, String password, String email, String birthday) {
        this();
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setBirthday(birthday);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement(name = "email")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @XmlElement(name = "birthday")
    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    @XmlElement(name = "inventory")
    public List<Item> getInventory()
    {
        return inventory;
    }

    public void setInventory(List<Item> inventory)
    {
        this.inventory = inventory;
    }

    public void addInventory(Item inventory)
    {
        this.inventory.add(inventory);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", birthday=" + birthday + " inventory=" + inventory + "]";
    }

}