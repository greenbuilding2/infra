package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.nested.*;
import java.util.*;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public boolean verifyLogin(User user) {
        List<User> userList = loginRepository.findByUsernameandPsw(user.getUsername(), user.getPsw());
        return userList.size() > 0;
    }
}
