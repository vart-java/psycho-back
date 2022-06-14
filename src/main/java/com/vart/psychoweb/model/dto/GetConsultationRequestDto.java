package com.vart.psychoweb.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetConsultationRequestDto {
    private long id;
    private String subject;
    private long userId;
}
