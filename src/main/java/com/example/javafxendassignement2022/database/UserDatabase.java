package com.example.javafxendassignement2022.database;

import com.example.javafxendassignement2022.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    public List<User> users = new ArrayList<>();

    public UserDatabase() {
        users.add(new User("munatsimike", "RukudzoM7*"));
        users.add(new User("someuser", "RukudzoM7*"));
    }

    public void findUser(User user) throws Exception {
        for (User user1 : users) {
            if (!user1.getUsername().equals(user.getUsername())) {
                return;
            }
        }
        throw new Exception("User not registered");
    }
}
