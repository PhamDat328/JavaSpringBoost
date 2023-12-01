package com.mvm.springdatajpaexample.conf;

import com.mvm.springdatajpaexample.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String auth = req.getHeader("Authorization");
        if(auth != null && auth.startsWith("Bearer ")) {
            String token = auth.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            boolean success = jwtUtil.validateToken(token, userDetails);

            if(success) {
                UsernamePasswordAuthenticationToken tk = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                tk.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(tk);

                System.out.println("Login Success");
            }
        }

        filterChain.doFilter(req, res);
    }
}
