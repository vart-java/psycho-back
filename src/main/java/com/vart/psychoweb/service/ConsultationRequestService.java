package com.vart.psychoweb.service;

import com.vart.psychoweb.model.entity.ConsultationRequest;

public interface ConsultationRequestService {
    ConsultationRequest create (Integer phoneNumber, String email, String subject);
    ConsultationRequest findById(long id);
    ConsultationRequest update (long id, String subject);
    boolean delete (long id);
}
