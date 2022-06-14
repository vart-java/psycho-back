package com.vart.psychoweb.repository;

import com.vart.psychoweb.model.entity.ConsultationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRequestRepository extends JpaRepository<ConsultationRequest, Long> {
}
