package com.vart.psychoweb.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostConsultationRequestDto {
    @JsonProperty("phone_number")
    private int phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("subject")
    private String subject;
}
