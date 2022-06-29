package com.vart.psychoweb.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetConsultationRequestDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("user_id")
    private long userId;
}
