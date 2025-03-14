package am.itspace.hotelManagement.controller;

import am.itspace.hotelManagement.dto.paymentDtos.PaymentCreateRequest;
import am.itspace.hotelManagement.dto.paymentDtos.PaymentResponse;
import am.itspace.hotelManagement.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/pay/{roomId}")
    public String payPage(@PathVariable String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "payment";
    }

    @PostMapping("/pay")
    public String pay(@ModelAttribute PaymentCreateRequest paymentCreateRequest, Model model) {
        String authenticatedUser = (String) model.getAttribute("authenticatedUser");
        PaymentResponse pay = paymentService.pay(authenticatedUser, paymentCreateRequest);
        model.addAttribute("paymentResponse", pay);
        model.addAttribute("paymentStatus", pay.getStatus().name());
        return "redirect:/";
    }

}