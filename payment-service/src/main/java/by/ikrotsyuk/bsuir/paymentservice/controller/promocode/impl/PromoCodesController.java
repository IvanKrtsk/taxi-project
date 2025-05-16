package by.ikrotsyuk.bsuir.paymentservice.controller.promocode.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.promocode.PromoCodesOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.PromoCodeRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.PromoCodeFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.promocodes.PromoCodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.Long;
import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/payments/promo-codes/")
public class PromoCodesController implements PromoCodesOperations {
    private final PromoCodesService promoCodesService;

    @Override
    public ResponseEntity<PromoCodeFullResponseDTO> createPromoCode(PromoCodeRequestDTO promoCodeRequestDTO) {
        return new ResponseEntity<>(promoCodesService.createPromoCode(promoCodeRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PromoCodeFullResponseDTO> getPromoCode(Long promoCodeId) {
        return new ResponseEntity<>(promoCodesService.getPromoCode(promoCodeId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PromoCodeFullResponseDTO>> getPromoCodes() {
        return new ResponseEntity<>(promoCodesService.getPromoCodes(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PromoCodeFullResponseDTO> updatePromoCode(Long promoCodeId, PromoCodeRequestDTO promoCodeRequestDTO) {
        return new ResponseEntity<>(promoCodesService.updatePromoCode(promoCodeId, promoCodeRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PromoCodeFullResponseDTO> changePromoCodeAvailability(Long promoCodeId, OffsetDateTime endDate) {
        return new ResponseEntity<>(promoCodesService.changePromoCodeAvailability(promoCodeId, endDate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PromoCodeFullResponseDTO> deletePromoCode(Long promoCodeId) {
        return new ResponseEntity<>(promoCodesService.deletePromoCode(promoCodeId), HttpStatus.NO_CONTENT);
    }
}
