package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "type", "value"})
public class Item {

    Integer id;
    ElementType type;
    Integer value;

    //Constructor with no arguments that allows the serialization of a Item object
    public Item() {
    }

    //Constructor that defines the main characteristics of an item
    public Item(Integer id, ElementType type, Integer value) {
        this.setId(id);
        this.setType(type);
        this.setValue(value);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "type")
    public ElementType getType() {
        return this.type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    @XmlElement(name = "value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", type=" + type + ", value=" + value + "]";
    }

}