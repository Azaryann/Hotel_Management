package am.itspace.hotelManagement.dto.paymentDtos;

import lombok.Data;

@Data
public class PaymentCreateRequest {
    int days;
    String cardNumber;
    String cvvNumber;
    long roomId;
}