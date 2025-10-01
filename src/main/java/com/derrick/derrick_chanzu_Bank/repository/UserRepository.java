package com.derrick.derrick_chanzu_Bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derrick.derrick_chanzu_Bank.entity.User;

// in here we are going to manipulate user entity

public interface UserRepository extends JpaRepository<User, Long> {
    //THIS GUY WILL PROVIDE ME WITH NETWORKS FOR TALKING TO MY DATABASE
    //we use <> to bind it to a particular repository
    //the primary key is of type long
    //we validate if user exists using the email

    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    User findByAccountNumber(String accountNumber);
    User findByAccountName(String accountNumber);








}
