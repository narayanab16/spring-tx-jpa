package com.narayana.jpa.service;

import com.narayana.jpa.model.Account;
import com.narayana.jpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED,
        isolation = Isolation.SERIALIZABLE)
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public AccountService(){
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            //isolation = Isolation.REPEATABLE_READ)
            isolation = Isolation.READ_COMMITTED)
    public Account withdraw(Integer accountNumber, Double amount) throws Exception{
        System.out.println("withdraw - accountNumber : " + accountNumber + ", amount : " + amount);
        try {
            Optional<Account> accountInfo = accountRepository.findById(accountNumber);
            Account account;
            if(accountInfo.isPresent()) {
                account = accountInfo.get();
                Double oldAmount = account.getAmount();
                Double newBalance = oldAmount - amount;
                System.out.println("withdraw - oldBalance : " + oldAmount + ", newBalance : " + newBalance);
                if(newBalance < 0) {
                    throw new Exception("Balance is negative");
                } else {
                    account.setAccountNumber(accountNumber);
                    account.setAmount(newBalance);
                    return accountRepository.save(account);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return new Account();
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public Account deposit(Integer accountNumber, Double amount) throws Exception {
        System.out.println("deposit - accountNumber : " + accountNumber + ", amount : " + amount);
        try {
            Optional<Account> accountInfo = accountRepository.findById(accountNumber);
            Account account;
            if(accountInfo.isPresent()) {
                account = accountInfo.get();
                Double oldAmount = account.getAmount();
                Double newBalance = oldAmount + amount;
                System.out.println("deposit - oldBalance : " + oldAmount + ", newBalance : " + newBalance);
                account.setAccountNumber(accountNumber);
                account.setAmount(newBalance);
                return accountRepository.save(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return new Account();
    }
    @Transactional(propagation = Propagation.SUPPORTS,
            noRollbackFor = {ConcurrencyFailureException.class})
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
