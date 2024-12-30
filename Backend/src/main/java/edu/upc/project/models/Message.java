package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"id", "sender", "thread", "message", "date", "parent_message"})
public class Message {

    Integer id;
    Integer sender;
    Integer thread;
    String message;
    String date;
    Integer parent_message;

    public Message() {
    }

    public Message(Integer sender, Integer thread, String message, String date, Integer parent_message) {
        this.setSender(sender);
        this.setThread(thread);
        this.setMessage(message);
        this.setDate(date);
        this.setParent_message(parent_message);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "sender")
    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    @XmlElement(name = "thread")
    public Integer getThread() {
        return thread;
    }

    public void setThread(Integer thread) {
        this.thread = thread;
    }

    @XmlElement(name = "message")
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @XmlElement(name = "date")
    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    @XmlElement(name = "parent_message")
    public Integer getParent_message()
    {
        return parent_message;
    }

    public void setParent_message(Integer parent_message)
    {
        this.parent_message = parent_message;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", sender=" + sender + ", thread=" + thread + ", message="
                + message + ", date=" + date + ", parent_message=" + parent_message + "]";
    }
}