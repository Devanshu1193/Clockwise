package ca.myscc.clockwise.database.pojo;

/**
 * A pojo representation of a user in the database.
 * @date Nov. 7, 2023
 * @author Santio Yousif
 * @version 1.0
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    /**
     * @param id The id of the user
     * @param first_name The first name of the user
     * @param last_name The last name of the user
     * @param username The username of the user
     * @param password The hashed password of the user
     */
    public User(int id, String first_name, String last_name, String username, String password) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String first_name) {
        this.firstName = first_name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String last_name) {
        this.lastName = last_name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
