package com.vart.psychoweb.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PutConsultationRequestDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("phone_number")
    private Integer phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("subject")
    private String subject;
}
