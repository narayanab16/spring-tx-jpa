package com.narayana.jpa;

import com.narayana.jpa.model.Account;
import com.narayana.jpa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

/*
 Note: if JDK14+     JVM options must : --add-opens java.base/java.lang=ALL-UNNAMED
 */
@RestController
@SpringBootApplication
public class RdbmsApplication {
    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(RdbmsApplication.class, args);
    }

    @RequestMapping(value = "/callMultiThread", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean callMultiThread() throws SQLException {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            List<Map<Integer, Double>> list = Arrays.asList(
                    Map.of(1001, 10.0), Map.of(1001, 20.0), Map.of(1001, 30.0));
            System.out.println(" list : " + list.size());
            System.out.println(" accountService : " + accountService);
            List<Optional<Future<Account>>> optionalList = list.parallelStream()
                    .map(x -> call_map_parallel(x, executorService)).toList();
            executorService.shutdown();
            while (!executorService.isTerminated()){}
            optionalList.forEach(x -> {
                try {
                    System.out.println("New Balance : " + (x.isPresent() ? x.get().get().getAmount() : 0.0));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private Optional<Future<Account>> call_map_parallel(Map<Integer, Double> x,
                                                        ExecutorService executorService) {
        for(Integer key: x.keySet()) {
            Future<Account> submit = null;
            try {
                Callable<Account> task = () -> accountService.withdraw(key, x.get(key));
                submit = executorService.submit(task);
                if (Objects.nonNull(submit))
                    return Optional.of(submit);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.of(submit);
        }
        return Optional.ofNullable(null);
    }

}