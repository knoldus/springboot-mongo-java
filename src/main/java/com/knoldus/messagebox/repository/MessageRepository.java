package com.knoldus.messagebox.repository;

import com.knoldus.messagebox.repository.model.Sms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Sms, String> {

    Sms findByMessageId(String id);
}
