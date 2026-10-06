package com.SpringBoot.jms.repo;

import com.SpringBoot.jms.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
