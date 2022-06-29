package com.vart.psychoweb.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToMobileInterfaceConsultationDto {
    private long id;
    private String subject;
    private String userName;
    private int phoneNumber;
}
