package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RidePassengerOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/rides/passenger")
public class RidePassengerControllerImpl implements RidePassengerOperations {
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BigDecimal> viewCostOfRide(@PathVariable Long id, String startLocation, String endLocation) {
        return new ResponseEntity<>(BigDecimal.TEN, HttpStatus.OK);
    }
}
