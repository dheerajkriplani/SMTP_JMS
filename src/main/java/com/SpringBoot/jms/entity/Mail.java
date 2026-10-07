package com.SpringBoot.jms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mails")
public class Mail {

    @Id
    private String id;

    @Indexed
    private List<String> userIds;

    private String subject;

    private String content;

    private List<Attachment> attachments = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean delivered=false;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attachment {
        private String fileId;
        private String fileName;
        private String contentType;
        private long size;
    }
}