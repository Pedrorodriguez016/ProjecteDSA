package edu.upc.project.dao;

import edu.upc.project.models.*;
import edu.upc.project.models.Thread;

import java.sql.SQLException;
import java.util.List;

public interface GameManager {

    User createUser(String name, String password, String email, Integer money) throws SQLException;
    User addUser(User user) throws SQLException;
    User getUser(String username) throws SQLException;

    Integer addItemInventory(String userID, Integer itemID) throws SQLException;
    List<Inventory> getInventory(String username) throws SQLException;

    Item getItem(Integer id) throws SQLException;
    List<Item> getItems() throws SQLException;

    FAQ addFAQ(FAQ faq) throws SQLException;
    FAQ createFAQ(Integer sender, String date, String question, String answer) throws SQLException;
    List<FAQ> getFAQS() throws SQLException;

    Thread addThread(Thread thread) throws SQLException;
    Thread createThread(String title, String date, Integer creator) throws SQLException;
    List<Thread> getThreads() throws SQLException;
    Thread getThread(Integer id) throws SQLException;

    Message createMessage(Integer sender, Integer thread, String message, String date,
                          Integer parent_message) throws SQLException;
    Message addMessage(Message message) throws SQLException;
    List<Message> getMessages(Integer thread) throws SQLException;
}
