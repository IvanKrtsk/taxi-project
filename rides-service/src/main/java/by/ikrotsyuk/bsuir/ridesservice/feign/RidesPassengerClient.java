package by.ikrotsyuk.bsuir.ridesservice.feign;

import by.ikrotsyuk.bsuir.ridesservice.config.FeignConfig;
import by.ikrotsyuk.bsuir.ridesservice.feign.dto.PassengerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PASSENGER-SERVICE", url = "localhost:8082", configuration = {FeignConfig.class})
public interface RidesPassengerClient {
    @GetMapping("/api/v1/passengers/{passengerId}/profile")
    ResponseEntity<PassengerResponseDTO> getPassengerProfile(@PathVariable Long passengerId);
}
