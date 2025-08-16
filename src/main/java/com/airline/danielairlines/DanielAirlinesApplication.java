package com.airline.danielairlines;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableAsync;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableAsync
public class DanielAirlinesApplication {

//   @Autowired
//    private JavaMailSender javaMailSender;

    public static void main(String[] args) {
        SpringApplication.run(DanielAirlinesApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner() {
//        return args -> {
//            try{
//
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//                MimeMessageHelper helper = new MimeMessageHelper(
//                        mimeMessage,
//                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                        StandardCharsets.UTF_8.name()
//                );
//                helper.setTo("danielpinedasalazar19@gmail.com");
//                helper.setSubject("Helo testing");
//                helper.setText("Testing email, hello world");
//
//                System.out.println("About to send email...");
//                javaMailSender.send(mimeMessage);
//            }catch(Exception ex){
//                System.out.println(ex.getMessage());
//            }
//        };
//    }
}
