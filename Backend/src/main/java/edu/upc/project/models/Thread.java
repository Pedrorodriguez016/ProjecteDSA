package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "title", "date", "creator"})
public class Thread {

    Integer id;
    String title;
    String date;
    Integer creator;

    //Constructor with no arguments that allows the serialization of a thread object
    public Thread() {
    }

    //Constructor that defines the main characteristics of a thread
    public Thread(String title, String date, Integer creator) {
        this.setTitle(title);
        this.setDate(date);
        this.setCreator(creator);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "creator")
    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Thread [id=" + id + ", title=" + title + ", date=" + date + ", creator=" + creator + "]";
    }

}