package by.ikrotsyuk.bsuir.ridesservice.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public interface RidePassengerOperations {
    ResponseEntity<BigDecimal> viewCostOfRide(@PathVariable Long id, @RequestParam @NotBlank @Size(max = 255) String startLocation, @RequestParam @NotBlank @Size(max = 255) String endLocation);

}
