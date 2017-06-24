package com.zhtian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Skye on 2017/6/24.
 */

@Repository
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public int getNameCount(String name) {
        return userRepository.countByUsername(name);
    }

    public String getPasswordByName(String name) {
        return userRepository.getUserByUsername(name).getPassword();
    }

}
