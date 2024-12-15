package edu.upc.project.dao;

import edu.upc.project.models.Inventory;
import edu.upc.project.models.ItemType;
import edu.upc.project.models.Item;
import edu.upc.project.models.User;

import java.sql.SQLException;
import java.util.List;

public interface GameManager {

    User createUser(String name, String password, String email, Integer money) throws SQLException;
    User addUser(User user) throws SQLException;
    User getUser(Integer username) throws SQLException;
    User getUserbyName(String username) throws SQLException;

    Integer addItemInventory(Integer userID, Integer itemID) throws SQLException;
    List<Inventory> getInventory(Integer username) throws SQLException;

    Item getItem(Integer id) throws SQLException;
    List<Item> getItems() throws SQLException;
}
