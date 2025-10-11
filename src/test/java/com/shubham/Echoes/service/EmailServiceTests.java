package com.shubham.Echoes.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("prod")
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;


    @Test
    void testSendMail() {
        System.out.println("Starting testSendMail...");
        emailService.sendEmail(
                "shubham2006621@gmail.com",
                "Testing Java mail sender",
                "he kaal h ?"
        );
        System.out.println("Finished sending mail!");
    }

}