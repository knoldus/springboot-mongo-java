package com.knoldus.messagebox.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "SMSInfo")
public final class Sms {
    @Id
    private final String messageId;
    private final String originationNumber;
    private final String destinationNumber;
    private final String messageBody;
}
