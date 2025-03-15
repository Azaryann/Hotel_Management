package am.itspace.hotelManagement.dto.paymentDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentResponse {

  private PaymentStatus status;

  private List<String> errorReasons;

}