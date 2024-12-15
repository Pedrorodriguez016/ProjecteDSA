package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"id", "user", "item", "quantity"})
public class Inventory {

    public Integer id;
    public Integer user;
    public Integer item;
    public Integer quantity;

    public Inventory()
    {
        setQuantity(1);
    }

    public Inventory(Integer user, Integer item)
    {
        this();
        this.user = user;
        this.item = item;
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "user")
    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user)
    {
        this.user = user;
    }

    @XmlElement(name = "item")
    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item)
    {
        this.item = item;
    }

    @XmlElement(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }
}
