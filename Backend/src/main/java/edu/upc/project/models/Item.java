package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "type", "value", "description"})
public class Item {

    Integer id;
    ItemType type;
    Integer value;
    String description;

    //Constructor with no arguments that allows the serialization of an Item object
    public Item() {
    }

    //Constructor that defines the main characteristics of an item
    public Item(Integer id, ItemType type, Integer value, String description) {
        this.setId(id);
        this.setType(type);
        this.setValue(value);
        this.setDescription(description);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "type")
    public ItemType getType() {
        return this.type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @XmlElement(name = "value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", type=" + type + ", value=" + value + ", description=" + description + "]";
    }

}