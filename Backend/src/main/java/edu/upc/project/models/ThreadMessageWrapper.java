package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"thread", "message"})
public class ThreadMessageWrapper {

    private Thread thread;
    private Message message;

    public ThreadMessageWrapper() {
    }

    public ThreadMessageWrapper(Thread thread, Message message) {
        this.setThread(thread);
        this.setMessage(message);
    }

    @XmlElement(name = "thread")
    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @XmlElement(name = "message")
    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }
}
