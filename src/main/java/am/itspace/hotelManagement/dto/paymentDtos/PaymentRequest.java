package am.itspace.hotelManagement.dto.paymentDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
    long userId;
    double paymentAmount;
    String cardNumber;
    String cvvNumber;
}