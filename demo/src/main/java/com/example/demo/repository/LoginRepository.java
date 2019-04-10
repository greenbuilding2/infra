package com.example.demo.repository;


import com.example.demo.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

@Repository
public interface LoginRepository extends CrudRepository<Sensor, Long> {

    @Transactional
    @Modifying
    @Query("SELECT user FROM User user WHERE user.username = :username AND user.psw = :password")
    List<User> findByUsernameandPsw(@Param("username") String username, @Param("password") String password);
}
