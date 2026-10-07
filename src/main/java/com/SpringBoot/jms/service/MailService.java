package com.SpringBoot.jms.service;

import com.SpringBoot.jms.dto.MailDto;
import com.SpringBoot.jms.entity.Mail;
import com.SpringBoot.jms.entity.User;
import com.SpringBoot.jms.repo.MailRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class MailService {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailRepository mailRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public Boolean sendMailToUsers(MailDto mailDto,List<String> userIds , List<MultipartFile> files){
        try{
            if(userIds==null||userIds.isEmpty()){
                log.error("User Ids are empty");
                return false;
            }
            boolean delivered=false;
            int count=0;
            for(String userId : userIds){
                User user = userService.getUserById(userId);
                if(user != null){
                    try{
                        sendMail(mailDto, user, files);
                        count++;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(count>0){
                delivered=true;
            }
            saveMailToDatabase(mailDto, userIds, files,delivered);
            return true;
        }catch (Exception e){
            log.error("Cannot Send Mail to Users");
            throw new RuntimeException(e);
        }
    }

    public Boolean sendMail(MailDto mailDto, User user, List<MultipartFile> files){
        try{
            String message = mailDto.getContent()
                    .replace("{{name}}", HtmlUtils.htmlEscape(user.getName()));
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");  // true = multipart
            helper.setTo(user.getEmailId());
            helper.setFrom(fromEmail);
            helper.setSubject(mailDto.getSubject());
            if(mailDto.getHtml()!= null && mailDto.getHtml()){
                helper.setText(message, true);
            } else {
                helper.setText(message, false);
            }
            if (files != null) {
                for (MultipartFile file : files) {
                    if (file.isEmpty()) continue;
                    helper.addAttachment(file.getOriginalFilename(),
                            new ByteArrayResource(file.getBytes()));
                }
            }

            mailSender.send(mimeMessage);
            log.info("Mail sent to >>>>>>>>>>>>>>>>>>" + user.getEmailId());
            return true;
        } catch (Exception e) {
            log.error("Cannot Send Mail to XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+ user.getEmailId());
            throw new RuntimeException(e);
        }
    }
    public void saveMailToDatabase(MailDto mailDto, List<String> userIds, List<MultipartFile> files, boolean delivered){
        try{
            Mail mail = new Mail();
            mail.setUserIds(userIds);
            mail.setSubject(mailDto.getSubject());
            mail.setContent(mailDto.getContent());
            if(files != null){
                for(MultipartFile file : files){
                    if(file.isEmpty()) continue;
                    Mail.Attachment attachment = new Mail.Attachment();
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setContentType(file.getContentType());
                    attachment.setSize(file.getSize());
                    mail.getAttachments().add(attachment);
                }
            }
            mail.setCreatedAt(LocalDateTime.now());
            mail.setDelivered(delivered);
            mailRepository.save(mail);
            log.info("Mail saved to database >>>>>>>>>>>>>>>>>>");
        } catch (Exception e) {
            log.error("Cannot Save Mail to Database");
            throw new RuntimeException(e);
        }
    }
}
