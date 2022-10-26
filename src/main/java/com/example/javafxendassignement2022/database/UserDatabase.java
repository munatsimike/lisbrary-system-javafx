package com.example.javafxendassignement2022.database;

import com.example.javafxendassignement2022.exception.WrongUsernamePasswordCombination;
import com.example.javafxendassignement2022.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    public  final List<User> users = new ArrayList<>();

    public UserDatabase() {
        users.add(new User("munatsimike", "RukudzoM7*", "John", "Poke"));
        users.add(new User("someuser", "RukudzoM7*", "Michael", "Munatsi"));
    }

    public User getUser(User user) throws WrongUsernamePasswordCombination {
        for (User user1 : users) {
            if (user1.getUsername().equals(user.getUsername()) && user1.getPassword().equals(user.getPassword())) {
                return user1;
            }
        }
        throw new WrongUsernamePasswordCombination();
    }
}
