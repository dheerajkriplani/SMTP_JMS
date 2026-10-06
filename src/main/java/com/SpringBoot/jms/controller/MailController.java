package com.SpringBoot.jms.controller;

import com.SpringBoot.jms.entity.User;
import com.SpringBoot.jms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mail/")
public class MailController {

    @Autowired
    private UserService userService;


}
