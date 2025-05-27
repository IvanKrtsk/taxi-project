package by.ikrotsyuk.bsuir.ridesservice.feign;

import by.ikrotsyuk.bsuir.communicationparts.event.FinishRideIncomePaymentDTO;
import by.ikrotsyuk.bsuir.ridesservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "PAYMENT-SERVICE", url = "localhost:8088", configuration = {FeignConfig.class})
public interface RidesPaymentClient {
    @PutMapping("/api/v1/users/{userId}/payments/incomes")
    ResponseEntity<FinishRideIncomePaymentDTO> finishIncomePayment(@PathVariable Long userId);
}
