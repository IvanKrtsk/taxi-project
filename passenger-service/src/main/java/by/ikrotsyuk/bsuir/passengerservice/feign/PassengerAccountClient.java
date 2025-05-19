package by.ikrotsyuk.bsuir.passengerservice.feign;

import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.passengerservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVICE", url = "localhost:8088", configuration = {FeignConfig.class})
public interface PassengerAccountClient {
    @PostMapping("/api/v1/users/{userId}/payments/accounts")
    ResponseEntity<Long> createAccount(@PathVariable Long userId, @RequestParam AccountTypes accountType);
}
