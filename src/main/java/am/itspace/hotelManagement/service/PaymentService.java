package am.itspace.hotelManagement.service;

import am.itspace.hotelManagement.dto.paymentDtos.PaymentCreateRequest;
import am.itspace.hotelManagement.dto.paymentDtos.PaymentRequest;
import am.itspace.hotelManagement.dto.paymentDtos.PaymentResponse;

public interface PaymentService {

    PaymentResponse pay(String email, PaymentCreateRequest paymentCreateRequest);

}