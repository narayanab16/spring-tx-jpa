package com.narayana.jpa.repository;

import com.narayana.jpa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface AccountRepository extends JpaRepository<Account, Serializable> {
}
