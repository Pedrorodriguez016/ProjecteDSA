package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "sender", "date", "question", "answer"})
public class FAQ {

    Integer id;
    Integer sender;
    String date;
    String question;
    String answer;

    //Constructor with no arguments that allows the serialization of an FAQ object
    public FAQ() {
    }

    //Constructor that defines the main characteristics of an FAQ
    public FAQ(Integer sender, String date, String question, String answer) {
        this.setSender(sender);
        this.setDate(date);
        this.setQuestion(question);
        this.setAnswer(answer);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "sender")
    public Integer getSender() {
        return this.sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    @XmlElement(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @XmlElement(name = "answer")
    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "FAQ [id=" + id + ", sender=" + sender + ", date=" + date + ", question=" + question + ", answer=" + answer + "]";
    }
}