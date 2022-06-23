package com.it_academy.service;

import com.it_academy.model.User;

import java.util.Scanner;

public class UserInput {
    public static User enterUser() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        do {
            try {
                System.out.println("Enter user's name: ");
                user.setName(scanner.nextLine());
                System.out.println("Enter user's address: ");
                user.setAddress(scanner.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid data, try again");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
        } while (!valid);
        return user;
    }
}
