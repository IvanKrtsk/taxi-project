package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideDriverOperations {
    @GetMapping("/{driverId}/rides/available")
    ResponseEntity<Page<RideResponseDTO>> getAvailableRides(@PathVariable Long driverId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @PatchMapping("/{driverId}/rides/{rideId}/accept")  // driverId буду брать из jwt и будет ресурсоориентировано
    ResponseEntity<RideFullResponseDTO> acceptRide(@PathVariable Long driverId, @PathVariable Long rideId);

    @PatchMapping("/{driverId}/rides/{rideId}/refuse")
    ResponseEntity<RideFullResponseDTO> refuseRide(@PathVariable Long driverId, @PathVariable Long rideId);

    @PatchMapping("/{driverId}/rides/{rideId}/begin")
    ResponseEntity<RideFullResponseDTO> beginRide(@PathVariable Long driverId, @PathVariable Long rideId);

    @PatchMapping("/{driverId}/rides/{rideId}/end")
    ResponseEntity<RideFullResponseDTO> endRide(@PathVariable Long driverId, @PathVariable Long rideId);

    @GetMapping("/{driverId}/rides")
    ResponseEntity<Page<RideFullResponseDTO>> getRidesHistory(@PathVariable Long driverId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @GetMapping("/{driverId}/rides/current")
    ResponseEntity<RideFullResponseDTO> getCurrentRide(@PathVariable Long driverId);
}
