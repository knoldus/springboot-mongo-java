package com.knoldus.messagebox.helper;

import com.knoldus.messagebox.request.Request;
import com.knoldus.messagebox.repository.model.Conversation;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Mapper {

    public Conversation requestToConversation(final Request request) {
        return Conversation.builder()
                .conversationId(request.getConversationId())
                .messageId(Arrays.asList(request.getMessage().getMessageId()))
                .build();
    }

}
