package com.vart.psychoweb.model.mapper;

import com.vart.psychoweb.model.dto.GetConsultationRequestDto;
import com.vart.psychoweb.model.dto.ToMobileInterfaceConsultationDto;
import com.vart.psychoweb.model.entity.ConsultationRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsultationRequestMapper {
    public GetConsultationRequestDto fromEntityToGetDto(ConsultationRequest consultationRequest) {
        return GetConsultationRequestDto.builder()
                .id(consultationRequest.getId())
                .subject(consultationRequest.getSubject())
                .userId(consultationRequest.getUser().getId())
                .build();
    }

    public List<GetConsultationRequestDto> fromEntityListToDtoList(List<ConsultationRequest> consultationRequestList) {
        return consultationRequestList.stream().map(this::fromEntityToGetDto).collect(Collectors.toList());
    }

    public ToMobileInterfaceConsultationDto fromEntityToMobileInterfaceConsultationDto(ConsultationRequest consultationRequest) {
        return ToMobileInterfaceConsultationDto.builder()
                .id(consultationRequest.getId())
                .subject(consultationRequest.getSubject())
                .userName(consultationRequest.getUser().getUsername())
                .phoneNumber(consultationRequest.getUser().getPhoneNumber())
                .build();
    }

    public List<ToMobileInterfaceConsultationDto> toMobileInterfaceConsultationDtoList(List<ConsultationRequest> consultationRequestList) {
        return consultationRequestList.stream().map(this::fromEntityToMobileInterfaceConsultationDto).collect(Collectors.toList());
    }
}
