package com.knoldus.messagebox.response;


import com.knoldus.messagebox.repository.model.Sms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Response {

    private final String conversationId;
    private final String senderId;
    private final String recieverId;
    private  List<Sms> message;
}