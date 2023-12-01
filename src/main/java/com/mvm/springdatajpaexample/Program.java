package com.mvm.springdatajpaexample;

import com.mvm.springdatajpaexample.model.product.Product;
import com.mvm.springdatajpaexample.model.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Program {
    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }

}
