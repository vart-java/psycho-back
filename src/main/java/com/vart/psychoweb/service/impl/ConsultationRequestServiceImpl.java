package com.vart.psychoweb.service.impl;

import com.vart.psychoweb.exception.ConsultationRequestNotFoundException;
import com.vart.psychoweb.model.entity.ConsultationRequest;
import com.vart.psychoweb.model.security.User;
import com.vart.psychoweb.repository.ConsultationRequestRepository;
import com.vart.psychoweb.service.ConsultationRequestService;
import com.vart.psychoweb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.vart.psychoweb.utils.Constants.CONSULTATION_NOT_FOUND_EXCEPTION;


@Slf4j
@RequiredArgsConstructor
@Service
public class ConsultationRequestServiceImpl implements ConsultationRequestService {
    private final ConsultationRequestRepository consultationRequestRepository;
    private final UserService userService;

    @Override
    public ConsultationRequest create(Integer phoneNumber, String email, String subject) {
        User user = userService.create(phoneNumber, email);
        ConsultationRequest consultationRequest = ConsultationRequest.builder()
                .user(user)
                .subject(subject)
                .build();
        return consultationRequestRepository.save(consultationRequest);
    }

    @Override
    public ConsultationRequest findById(long id) {
        return consultationRequestRepository.findById(id).orElseThrow(
                ()->new ConsultationRequestNotFoundException(CONSULTATION_NOT_FOUND_EXCEPTION+id));
    }

    @Override
    public ConsultationRequest update(long id, String subject) {
        ConsultationRequest consultationRequest = findById(id);
        consultationRequest.setSubject(subject);
        return consultationRequestRepository.save(consultationRequest);
    }

    @Override
    public boolean delete(long id) {
        ConsultationRequest consultationRequest = findById(id);
        consultationRequestRepository.delete(consultationRequest);
        return true;
    }
}
