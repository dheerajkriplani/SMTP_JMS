package com.SpringBoot.jms.repo;

import com.SpringBoot.jms.entity.Mail;
import com.SpringBoot.jms.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MailRepository extends MongoRepository<Mail,String> {
}
