package by.ikrotsyuk.bsuir.paymentservice.service.promocodes;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.PromoCodeRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.PromoCodeFullResponseDTO;

import java.time.OffsetDateTime;
import java.util.List;

public interface PromoCodesService {
    PromoCodeFullResponseDTO createPromoCode(PromoCodeRequestDTO promoCodeRequestDTO);
    PromoCodeFullResponseDTO getPromoCode(Long id);
    List<PromoCodeFullResponseDTO> getPromoCodes();
    PromoCodeFullResponseDTO updatePromoCode(Long id, PromoCodeRequestDTO promoCodeRequestDTO);
    PromoCodeFullResponseDTO changePromoCodeAvailability(Long id, OffsetDateTime endDate);
    PromoCodeFullResponseDTO deletePromoCode(Long id);
}
