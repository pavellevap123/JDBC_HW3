package com.it_academy.demo;
import com.it_academy.model.Currency;
import com.it_academy.model.Operation;
import com.it_academy.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static com.it_academy.query.UserQueryExecutor.*;
import static com.it_academy.service.UserInput.enterUser;

public class Application {
    private static final String JDBC_DRIVER_PATH = "org.sqlite.JDBC";
    private static final String DATABASE_URL =
            "jdbc:sqlite:/Users/pavelpaddubotski/IdeaProjects/Operations/src/Operations.db";

    public static void main(String[] args) throws SQLException {
        if (isDriverExists()) {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            String actionCode;
            do {
                printMenu();
                actionCode = new Scanner(System.in).nextLine();
                switch (actionCode) {
                    case "1" -> {
                        User user = enterUser();
                        addUser(user, connection);
                    }
                    case "2" -> {
                        System.out.println("Enter the user's ID: ");
                        int userID = new Scanner(System.in).nextInt();
                        System.out.println("Enter the user's balance: ");
                        int balance = new Scanner(System.in).nextInt();
                        System.out.println("Enter the user's currency(1-USD, 2-EUR, 3-RUB): ");
                        int currencyCode = new Scanner(System.in).nextInt();
                        Currency currency = null;
                        switch (currencyCode) {
                            case 1 -> currency = Currency.USD;
                            case 2 -> currency = Currency.EUR;
                            case 3 -> currency = Currency.RUB;
                            default -> System.out.println("You've entered wrong currency code");
                        }
                        if (null == currency) {
                            break;
                        }
                        addNewAccount(userID, balance, currency, connection);
                    }
                    case "3" -> {
                        System.out.println("Enter account ID to add to account: ");
                        int idForUpdate = new Scanner(System.in).nextInt();
                        System.out.println("Enter amount to add: ");
                        int amount = new Scanner(System.in).nextInt();
                        editAccountBalance(idForUpdate, amount, Operation.ADD, connection);
                    }
                    case "4" -> {
                        System.out.println("Enter account ID to withdraw from account: ");
                        int idForUpdate = new Scanner(System.in).nextInt();
                        System.out.println("Enter amount to add: ");
                        int amount = new Scanner(System.in).nextInt();
                        editAccountBalance(idForUpdate, amount, Operation.WITHDRAW, connection);
                    }
                    case "5" -> {
                        System.out.println("Thanks for using the program!");
                        actionCode = "5";
                    }
                    default -> System.out.println("Unknown option. Please enter again");
                }

            } while (!"5".equals(actionCode));
            connection.close();
        }
    }

    private static boolean isDriverExists() {
        try {
            Class.forName(JDBC_DRIVER_PATH);
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.println("JDBC Driver not found");
            return false;
        }
    }

    public static void printMenu() {
        System.out.println("\nPlease select an action");
        System.out.println("1 - register new user");
        System.out.println("2 - add new account to user");
        System.out.println("3 - add to existing account");
        System.out.println("4 - withdraw from existing account");
        System.out.println("5 - quit\n");
    }
}
