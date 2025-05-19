package by.ikrotsyuk.bsuir.ratingservice.feign;

import by.ikrotsyuk.bsuir.ratingservice.config.FeignConfig;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.RideFullResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RIDES-SERVICE", url = "localhost:8086", configuration = {FeignConfig.class})
public interface RideClient {
    @GetMapping("/api/v1/rides/{rideId}")
    ResponseEntity<RideFullResponseDTO> getRideById(@PathVariable Long rideId);
}
