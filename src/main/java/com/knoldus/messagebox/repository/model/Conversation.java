package com.knoldus.messagebox.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document(collection = "Conversation")
@Getter
@Setter
@AllArgsConstructor
@Builder
public final class Conversation {
    @Id
    private final String conversationId;
    private List<String> messageId;
}
