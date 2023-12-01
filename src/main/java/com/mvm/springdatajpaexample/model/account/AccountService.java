package com.mvm.springdatajpaexample.model.account;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository repo;
    @Autowired
    private PasswordEncoder encoder;

    public Account addAccount(@RequestBody Account account) {
        account.setDisabled(false);
        if (account.getRole() == null) {
            account.setRole("user");
        }
        account.setPassword(encoder.encode(account.getPassword())); // hash
        repo.save(account);
        return account;
    }

    public List<Account> getAllAccount() {
        return repo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = repo.findByUsername(username);
        if(acc == null) {
            throw new UsernameNotFoundException("No account with " + username);
        }

        return User.withUsername(acc.getUsername())
                .password(acc.getPassword())
                .roles(acc.getRole())
                .disabled(acc.isDisabled())
                .build();
    }
}
