package com.knoldus.messagebox.request;

import com.knoldus.messagebox.repository.model.Sms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Request {
    private final String conversationId;
    private final Sms message;
}
