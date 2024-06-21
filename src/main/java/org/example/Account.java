package org.example;

import org.mindrot.jbcrypt.BCrypt;
import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    protected final int id;
    protected final String username;

    public Account(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Persistence {
        public static void init() {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String createSQLTable = "CREATE TABLE IF NOT EXISTS account( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL)";
                PreparedStatement statement = conn.prepareStatement(createSQLTable);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static int register(String username, String password) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            try (Connection conn = DatabaseConnection.getConnection()) {
                String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";
                PreparedStatement statement = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve auto-generated key");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static Account authenticate(String username, String password) throws AuthenticationException {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT id, username, password FROM account WHERE username = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);

                ResultSet result = statement.executeQuery();

                if (!result.next()) {
                    throw new AuthenticationException("No such user");
                }

                String hashedPassword = result.getString(3);
                if (!BCrypt.checkpw(password, hashedPassword)) {
                    throw new AuthenticationException("Wrong password");
                }

                return new Account(
                        result.getInt(1),
                        result.getString(2)
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
