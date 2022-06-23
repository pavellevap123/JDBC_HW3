package com.it_academy.query;

import com.it_academy.model.Currency;
import com.it_academy.model.Operation;
import com.it_academy.model.User;

import java.sql.*;

import static java.lang.String.format;

public class UserQueryExecutor {

    private static final String SQL_INSERT_USER = "INSERT INTO Users (name, address) VALUES('%s', '%s')";
    private static final String SQL_INSERT_ACCOUNT = "INSERT INTO Accounts (userId, balance, currency) VALUES('%d', '%d', '%s')";
    private static final String SQL_ACCOUNT_ID = "SELECT * FROM Accounts WHERE accountId = %d;";
    private static final String SQL_UPDATE_ACCOUNT_BALANCE = "UPDATE Accounts SET balance = '%d' WHERE accountId = %d;";
    private static final String SQL_ADD_TRANSACTION = "INSERT INTO Transactions (accountId, amount) VALUES('%d', '%d')";

    public static void addUser(User user, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(format(SQL_INSERT_USER, user.getName(), user.getAddress()));
        statement.close();
    }

    public static void addNewAccount(int id, int balance, Currency currency, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(format(SQL_INSERT_ACCOUNT, id, balance, currency.getName()));
        statement.close();
    }

    public static void editAccountBalance(int id, int amount, Operation operation, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement(format(SQL_ACCOUNT_ID, id));
        ResultSet resultSet = preparedStatement.executeQuery();
        int initialBalance = 0;
        int newBalance = 0;
        while (resultSet.next()) {
            initialBalance = resultSet.getInt("balance");
        }
        if (operation == Operation.ADD) {
            newBalance = initialBalance + amount;
        } else if (operation == Operation.WITHDRAW) {
            newBalance = initialBalance - amount;
        }
        resultSet.close();
        preparedStatement.close();
        statement.executeUpdate(format(SQL_UPDATE_ACCOUNT_BALANCE, newBalance, id));
        statement.executeUpdate(format(SQL_ADD_TRANSACTION, id, amount));
        statement.close();
    }
}
