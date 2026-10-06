package com.SpringBoot.jms.controller;

import com.SpringBoot.jms.dto.MailDto;
import com.SpringBoot.jms.entity.Mail;
import com.SpringBoot.jms.entity.User;
import com.SpringBoot.jms.service.MailService;
import com.SpringBoot.jms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mail/")
public class MailController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendMail(@ModelAttribute MailDto mailDto, @RequestParam String name, @RequestParam String emailId, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        Boolean isSent=mailService.sendMailToUser(mailDto, name, emailId, files);
        return isSent?"Mail sent to " + emailId:"Failed to send mail to " + emailId;
    }

}
