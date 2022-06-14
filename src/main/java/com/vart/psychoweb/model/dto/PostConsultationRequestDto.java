package com.vart.psychoweb.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostConsultationRequestDto {
    private Integer phoneNumber;
    private String email;
    private String subject;
}
