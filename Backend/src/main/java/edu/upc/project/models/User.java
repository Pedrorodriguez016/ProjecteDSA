package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"id", "username", "password", "email", "money", "inventory"})
public class User {

    Integer id;
    String username;
    String password;
    String email;
    Integer money;
    List<Item> inventory;

    public User() {
        this.money = 0;
        this.inventory = new ArrayList<>();
    }

    public User(Integer id, String username, String password, String email) {
        this();
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
    }

    public User(Integer id, String username, String password, String email, Integer money) {
        this();
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setMoney(money);
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

    @XmlElement(name = "money")
    public Integer getMoney()
    {
        return money;
    }

    public void setMoney(Integer money)
    {
        this.money = money;
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
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", money=" + money + ", inventory=" + inventory + "]";
    }

}