package edu.upc.project.dao;

import edu.upc.project.db.FactorySession;
import edu.upc.project.db.Session;
import edu.upc.project.models.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }
    //Function that adds the generated user from the function addUser to the list of users
    public User addUser(User user) throws SQLException {
        logger.info("Petition for a new user: " + user);
        Session session = null;
        try {
            session = FactorySession.openSession();
            session.save(user);
            logger.info("User added successfully");
            return user;
        }
        catch (Exception e) {
            logger.warn("User not added correctly because of: " + e.getMessage());
            return null;
        }
        finally {
            session.close();
        }
    }

    //Functions that takes all the values necessaries to construct a new user and
    //passes them as a user object to be initialized
    public User createUser(String name, String password, String email, Integer money) throws SQLException {
        if (money == null)
            return this.addUser(new User(name, password, email));
        else
            return this.addUser(new User(name, password, email, money));
    }

    //Function that returns the user with a specific ID
    public User getUserbyID(Integer id) throws SQLException {
        Session session = null;
        User user = null;
        try {
            session = FactorySession.openSession();
            user = (User)session.get(User.class, new String[]{"id"}, new Integer[]{id});
        }
        catch (Exception e) {
            logger.warn(e.getCause());
        }
        finally {
            session.close();
        }
        if (user.getUsername() != null)
        {
            logger.info("Obtained User with ID " + id + ": " + user);
                return user;
        }
        else
        {
            logger.warn("User with ID " + id + " not found");
            return null;
        }
    }

    //Function that returns the user with a specific username
    public User getUser(String username) throws SQLException {
        Session session = null;
        User user = null;
        try {
            session = FactorySession.openSession();
            user = (User)session.get(User.class, new String[]{"username"}, new String[]{username});
        }
        catch (Exception e) {
            logger.warn(e.getCause());
        }
        finally {
            session.close();
        }
        if (user.getUsername() != null)
        {
            logger.info("Obtained User with username " + username + ": " + user);
            return user;
        }
        else
        {
            logger.warn("User with username " + username + " not found");
            return null;
        }
    }

    //Function that adds an item to the inventory of an user
    public Integer addItemInventory(String username, Integer itemID) throws SQLException {
        Session session = null;
        User user = null;
        Item item = null;
        Inventory inventory = null;

        try {
            session = FactorySession.openSession();
            user = getUser(username);
            item = getItem(itemID);
            logger.info("Petition to add to the inventory of user " + username + " the item with ID " + itemID);
            if (user == null)
                return -1;
            //Checks if the item selected exists
            if (item == null)
            {
                logger.warn("Item not added to the inventory of user " + username + " because the item does not exist");
                return -1;
            }
            Integer usermoney = user.getMoney();
            Integer itemvalue = item.getValue();

            //Check if the user has enough money to buy the item
            if (itemvalue > usermoney)
            {
                logger.warn("Item with ID " + itemID + " not added to user " + username + " because of a lack of funds");
                return -2;
            }
            else
            {
                inventory = (Inventory)session.get(Inventory.class, new String[]{"user", "item"}, new Integer[]{user.getId(), itemID});
                if (inventory == null)
                {
                    inventory = new Inventory(user.getId(), itemID);
                    session.save(inventory);
                }
                else
                {
                    int CurrentQuantity = inventory.getQuantity();
                    inventory.setQuantity(CurrentQuantity + 1);
                    session.update(inventory);
                }
                user.setMoney(usermoney - itemvalue);
                session.update(user);
                logger.info("Item with ID " + itemID + " added to user " + username);
                return 0;
            }
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
        }
        finally {
            session.close();
        }

        return -1;
    }

    //Function that returns the inventory of an user
    public List<Inventory> getInventory(String username) throws SQLException {
        logger.info("Requested list of items for user " + username);
        Session session = null;
        List<Inventory> itemList = null;

        try {
            session = FactorySession.openSession();
            // Retrieve all inventory entries
            List<Inventory> unfiltereditemList = session.getAll(Inventory.class);
            User user = getUser(username);

            // Filter the list based on the username parameter
            itemList = unfiltereditemList.stream()
                    .filter(obj -> obj instanceof Inventory) // Ensure it is an Inventory object
                    .map(obj -> (Inventory) obj) // Cast to Inventory
                    .filter(inventory -> inventory.getUser().equals(user.getId())) // Filter by user ID
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Error retrieving inventory for user: " + username, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return itemList;
    }

    //Function that returns an item based on its ID
    public Item getItem(Integer id) throws SQLException {
        Session session = null;
        Item item = null;
        try {
            session = FactorySession.openSession();
            item = (Item)session.get(Item.class, new String[]{"id"}, new Integer[]{id});
        }
        catch (Exception e) {
            logger.warn(e.getCause());
        }
        finally {
            session.close();
        }
        if (item.getId() != null)
        {
            logger.info("Obtained item with ID " + id + ": " + item);
            return item;
        }
        else
        {
            logger.warn("Item with ID " + id + " not found");
            return item;
        }
    }

    //Function that returns all the items
    public List<Item> getItems() throws SQLException {
        logger.info("Requested list of items in the shop");
        Session session = null;
        List<Item> itemList = null;
        try {
            session = FactorySession.openSession();
            List<Item> objectList = session.getAll(Item.class);
            itemList = objectList.stream()
                    .filter(obj -> obj instanceof Item)
                    .map(obj -> (Item) obj)
                    .collect(Collectors.toList());
        } catch (Exception e) {

        } finally {
            session.close();
        }
        return itemList;
    }

    //Function that adds the generated FAQ from the function createFAQ to the list of questions
    public FAQ addFAQ(FAQ faq) throws SQLException {
        logger.info("Petition for a new FAQ: " + faq);
        Session session = null;
        try {
            session = FactorySession.openSession();
            session.save(faq);
            logger.info("FAQ added successfully");
            return faq;
        }
        catch (Exception e) {
            logger.warn("FAQ not added correctly because of: " + e.getMessage());
            return null;
        }
        finally {
            session.close();
        }
    }

    //Functions that takes all the values necessaries to construct a new FAQ and
    //passes them as a FAQ object to be initialized
    public FAQ createFAQ(Integer sender, String date, String question, String answer) throws SQLException {
        return this.addFAQ(new FAQ(sender, date, question, answer));
    }

    //Function that returns all the FAQs
    public List<FAQ> getFAQS() throws SQLException {
        logger.info("Requested list of FAQs in the game");
        Session session = null;
        List<FAQ> faqList = null;
        try {
            session = FactorySession.openSession();
            List<FAQ> objectList = session.getAll(FAQ.class);
            faqList = objectList.stream()
                    .filter(obj -> obj instanceof FAQ)
                    .map(obj -> (FAQ) obj)
                    .collect(Collectors.toList());

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return faqList;
    }
}