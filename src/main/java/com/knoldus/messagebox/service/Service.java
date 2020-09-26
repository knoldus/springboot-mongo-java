package com.knoldus.messagebox.service;

import com.knoldus.messagebox.helper.Mapper;
import com.knoldus.messagebox.repository.ConversationRepository;
import com.knoldus.messagebox.repository.MessageRepository;
import com.knoldus.messagebox.repository.model.Conversation;
import com.knoldus.messagebox.repository.model.Sms;
import com.knoldus.messagebox.request.Request;
import com.knoldus.messagebox.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    Mapper mapper;
    @Autowired
    MongoTemplate mongoTemplate;

    public ResponseEntity<Response> getConversationById(String convId) {

        try {
            Conversation conversation = conversationRepository.findByConversationId(convId);
            Response response = Response.builder().conversationId(conversation.getConversationId())
                    .message(conversationRepository.findByConversationId(convId).getMessageId().stream()
                            .map(msgId -> messageRepository.findByMessageId(msgId)).collect(Collectors.toList()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> createConversation(Request request) {

        try {
            if (checkConversationIdExists(request.getConversationId())) {
                createMessage(request.getMessage(), request.getConversationId());
                return new ResponseEntity<>("Converstion already exists but msg has been added", HttpStatus.OK);
            } else {
                conversationRepository.save(mapper.requestToConversation(request));
                messageRepository.save(request.getMessage());
                return new ResponseEntity<>("Conversation has been created", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> createMessage(Sms sms, String convId) {
        try {
            if (checkConversationIdExists(convId)) {
                Query query = new Query();
                query.addCriteria(Criteria.where("conversationId").is(convId));
                Update update = new Update();
                update.addToSet("messageId", sms.getMessageId());
                mongoTemplate.updateMulti(query, update, Conversation.class);
                messageRepository.save(sms);
                return new ResponseEntity<>("Message has been added", HttpStatus.CREATED);
            } else {
                createConversation(Request.builder().conversationId(convId).message(sms).build());
                return new ResponseEntity<>("Conversation does not exits so created new", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteConversation(String id) {


        try {
            if (checkConversationIdExists(id)) {
                conversationRepository.findByConversationId(id).getMessageId()
                        .stream().forEach(msgId -> messageRepository.deleteById(msgId));
                conversationRepository.deleteById(id);
                return new ResponseEntity<>("Conversation is deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Conversation Id does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean checkConversationIdExists(String convId) {
        return !(mongoTemplate.find(Query.query(Criteria.where("conversationId").is(convId)),
                Conversation.class).isEmpty());
    }

    public boolean checkMessageIdExists(String msgId) {
        return !(mongoTemplate.find(Query.query(Criteria.where("messageId").is(msgId)),
                Sms.class).isEmpty());
    }
}
