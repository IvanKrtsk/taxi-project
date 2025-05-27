package by.ikrotsyuk.bsuir.ridesservice.feign;

import by.ikrotsyuk.bsuir.ridesservice.config.FeignConfig;
import by.ikrotsyuk.bsuir.ridesservice.feign.dto.DriverResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DRIVER-SERVICE", url = "localhost:8084", configuration = {FeignConfig.class})
public interface RidesDriverClient {
    @GetMapping("/api/v1/drivers/{driverId}/profile")
    ResponseEntity<DriverResponseDTO> getDriverProfile(@PathVariable Long driverId);
}
