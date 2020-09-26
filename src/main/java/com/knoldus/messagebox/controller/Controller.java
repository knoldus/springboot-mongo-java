package com.knoldus.messagebox.controller;

import com.knoldus.messagebox.request.Request;
import com.knoldus.messagebox.service.Service;
import com.knoldus.messagebox.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    Service service;

    @GetMapping("/conversation/{id}")
    public ResponseEntity<Response> getConversationById(@PathVariable("id") String convId) {

        return service.getConversationById(convId);
    }

    @PostMapping("/conversation")
    public ResponseEntity<String> createConversation(@RequestBody Request request) {

        return service.createConversation(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteConversation(@PathVariable("id") String id) {

        return service.deleteConversation(id);
    }
}
