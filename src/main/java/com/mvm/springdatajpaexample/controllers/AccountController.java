package com.mvm.springdatajpaexample.controllers;

import com.mvm.springdatajpaexample.model.account.Account;
import com.mvm.springdatajpaexample.model.account.AccountService;
import com.mvm.springdatajpaexample.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    AuthenticationManager auth;

    @Autowired
    UserDetailsService userService;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * Method này chỉ phục vụ cho mục đích kiểm thử
     * Truy cập: http://localhost:8080/api/accounts/ để xem danh sách các tài khoản đang có trong hệ thống
     */
    @GetMapping("")
    public List<Account> showAll()
    {
        return service.getAllAccount();
    }

    /**
     * Đăng ký (tạo) một tài khoản mới
     * Cần cung cấp json gồm các field: username, password và fullName
     */
    @PostMapping("/register")
    public Account register(@RequestBody Account account)
    {
        return service.addAccount(account);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest account)
    {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
        try {
            auth.authenticate(token);
            System.out.println("Login success, now create jwt.");
            return jwtUtil.generateToken(userService.loadUserByUsername(account.getUsername()));
        } catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class LoginRequest {
    private String username;
    private String password;
}
