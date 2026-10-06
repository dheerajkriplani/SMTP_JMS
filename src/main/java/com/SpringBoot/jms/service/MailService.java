package com.SpringBoot.jms.service;

import com.SpringBoot.jms.dto.MailDto;
import com.SpringBoot.jms.entity.Mail;
import com.SpringBoot.jms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendMailToUser(MailDto mailDto, String name, String emailId, List<MultipartFile> files){
        User user = userService.getAllUsers().stream()
                .filter(u -> u.getName().equals(name) && u.getEmailId().equals(emailId))
                .findFirst()
                .orElse(null);

        if(user == null){
            user= User.builder()
                    .name(name)
                    .emailId(emailId)
                    .build();
            user=userService.createUser(user);
        }

        return true;
    }
}
