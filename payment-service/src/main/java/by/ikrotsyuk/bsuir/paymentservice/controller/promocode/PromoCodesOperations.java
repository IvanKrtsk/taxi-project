package by.ikrotsyuk.bsuir.paymentservice.controller.promocode;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.PromoCodeRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.PromoCodeFullResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.List;

public interface PromoCodesOperations {
    @PostMapping
    ResponseEntity<PromoCodeFullResponseDTO> createPromoCode(@RequestBody PromoCodeRequestDTO promoCodeRequestDTO);

    @GetMapping("/{promoCodeId}")
    ResponseEntity<PromoCodeFullResponseDTO> getPromoCode(@PathVariable Long promoCodeId);

    @GetMapping
    ResponseEntity<List<PromoCodeFullResponseDTO>> getPromoCodes();

    @PutMapping("/{promoCodeId}")
    ResponseEntity<PromoCodeFullResponseDTO> updatePromoCode(@PathVariable Long promoCodeId, @RequestBody PromoCodeRequestDTO promoCodeRequestDTO);

    @PatchMapping("/{promoCodeId}")
    ResponseEntity<PromoCodeFullResponseDTO> changePromoCodeAvailability(@PathVariable Long promoCodeId, @RequestParam OffsetDateTime endDate);

    @DeleteMapping("/{promoCodeId}")
    ResponseEntity<PromoCodeFullResponseDTO> deletePromoCode(@PathVariable Long promoCodeId);
}
