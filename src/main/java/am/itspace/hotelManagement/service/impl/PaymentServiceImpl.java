package am.itspace.hotelManagement.service.impl;

import am.itspace.hotelManagement.dto.paymentDtos.PaymentCreateRequest;
import am.itspace.hotelManagement.dto.paymentDtos.PaymentRequest;
import am.itspace.hotelManagement.dto.paymentDtos.PaymentResponse;
import am.itspace.hotelManagement.dto.paymentDtos.PaymentStatus;
import am.itspace.hotelManagement.entity.Room;
import am.itspace.hotelManagement.enums.RoomStatus;
import am.itspace.hotelManagement.repository.RoomRepository;
import am.itspace.hotelManagement.service.PaymentService;
import am.itspace.hotelManagement.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;

    private final UserService userService;

    private final RoomRepository roomRepository;

    @Value("${payment.service.url}")
    private String paymentUrl;

    @Override
    @Transactional
    public PaymentResponse pay(String email,PaymentCreateRequest paymentCreateRequest) {
        Room room;
        Long userId = userService.findByEmail(email).getId();
        Optional<Room> roomById = roomRepository.findById(paymentCreateRequest.getRoomId());
        if (roomById.isPresent()) {
            room = roomById.get();
        } else {
            throw new RuntimeException("Room not found");
        }
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .userId(userId)
                .cardNumber(paymentCreateRequest.getCardNumber())
                .cvvNumber(paymentCreateRequest.getCvvNumber())
                .paymentAmount(room.getPricePerNight()* paymentCreateRequest.getDays())
                .build();

        ResponseEntity<PaymentResponse> exchange = restTemplate.exchange(paymentUrl, HttpMethod.POST,
                new HttpEntity<>(paymentRequest), PaymentResponse.class);
        if (PaymentStatus.SUCCESS.name().equals(exchange.getBody().getStatus())) {
            room.setRoomStatus(RoomStatus.OCCUPIED);
            roomRepository.save(room);
        }
        return exchange.getBody();
    }
}
