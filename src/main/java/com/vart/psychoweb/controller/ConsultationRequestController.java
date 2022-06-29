package com.vart.psychoweb.controller;

import com.vart.psychoweb.model.dto.GetConsultationRequestDto;
import com.vart.psychoweb.model.dto.PostConsultationRequestDto;
import com.vart.psychoweb.model.dto.PutConsultationRequestDto;
import com.vart.psychoweb.model.entity.ConsultationRequest;
import com.vart.psychoweb.model.mapper.ConsultationRequestMapper;
import com.vart.psychoweb.service.ConsultationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consultation-requests")
public class ConsultationRequestController {
    private final ConsultationRequestService consultationRequestService;
    private final ConsultationRequestMapper consultationRequestMapper;
    private final KafkaTemplate<Object, Object> template;

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetConsultationRequestDto> getConsultationRequest(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(consultationRequestMapper.fromEntityToGetDto(consultationRequestService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<GetConsultationRequestDto> createConsultationRequest(
            @RequestBody PostConsultationRequestDto postConsultationRequestDto) {
        ConsultationRequest consultationRequest = consultationRequestService.create(
                postConsultationRequestDto.getPhoneNumber(),
                postConsultationRequestDto.getEmail(),
                postConsultationRequestDto.getSubject()
        );
        template.send("consultation", consultationRequestMapper.fromEntityToMobileInterfaceConsultationDto(consultationRequest));
        return ResponseEntity.ok(consultationRequestMapper.fromEntityToGetDto(consultationRequest));
    }

    @PutMapping
    public ResponseEntity<GetConsultationRequestDto> updateConsultationRequest(
            @RequestBody PutConsultationRequestDto putConsultationRequestDto) {
        ConsultationRequest consultationRequest = consultationRequestService.update(
                putConsultationRequestDto.getId(),
                putConsultationRequestDto.getSubject()
        );
        return ResponseEntity.ok(consultationRequestMapper.fromEntityToGetDto(consultationRequest));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteConsultationRequest(@PathVariable(name = "id") long id) {
        boolean isDelete = consultationRequestService.delete(id);
        if (Boolean.TRUE.equals(isDelete)) return ResponseEntity.ok(id);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
    }
}
