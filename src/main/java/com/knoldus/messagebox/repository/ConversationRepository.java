package com.knoldus.messagebox.repository;

import com.knoldus.messagebox.repository.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {

    Conversation findByConversationId(String id);
}
