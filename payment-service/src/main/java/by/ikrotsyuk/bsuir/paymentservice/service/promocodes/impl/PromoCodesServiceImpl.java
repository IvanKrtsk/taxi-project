package by.ikrotsyuk.bsuir.paymentservice.service.promocodes.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.PromoCodeRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.PromoCodeFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.PromoCodeEntity;
import by.ikrotsyuk.bsuir.paymentservice.mapper.PromoCodesMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.PromoCodesRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.promocodes.PromoCodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PromoCodesServiceImpl implements PromoCodesService {
    private final PromoCodesRepository promoCodesRepository;
    private final PromoCodesMapper promoCodesMapper;

    @Override
    @Transactional
    public PromoCodeFullResponseDTO createPromoCode(PromoCodeRequestDTO promoCodeRequestDTO) {
        if(promoCodesRepository.existsByCode(promoCodeRequestDTO.code()))
            throw new RuntimeException("already exists");
        else {
            PromoCodeEntity promoCodeEntity = promoCodesMapper.toEntity(promoCodeRequestDTO);
            return promoCodesMapper.toFullDTO(promoCodesRepository.save(promoCodeEntity));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PromoCodeFullResponseDTO getPromoCode(Long id) {
        return promoCodesMapper.toFullDTO(
                promoCodesRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("not found"))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromoCodeFullResponseDTO> getPromoCodes() {
        List<PromoCodeEntity> promoCodeEntityList = promoCodesRepository.findAll();
        if(promoCodeEntityList.isEmpty())
            throw new RuntimeException("not found");
        return promoCodesMapper.toFullDTOList(promoCodeEntityList);
    }

    @Override
    @Transactional
    public PromoCodeFullResponseDTO updatePromoCode(Long id, PromoCodeRequestDTO promoCodeRequestDTO) {
        PromoCodeEntity promoCodeEntity = promoCodesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        promoCodeEntity.setCode(promoCodeRequestDTO.code());
        promoCodeEntity.setDiscountPercentage(promoCodeRequestDTO.discountPercentage());
        promoCodeEntity.setActivationsCount(promoCodeRequestDTO.activationsCount());
        promoCodeEntity.setStartDate(promoCodeRequestDTO.startDate());
        promoCodeEntity.setEndDate(promoCodeRequestDTO.endDate());
        promoCodeEntity.setIsActive(promoCodeRequestDTO.isActive());
        promoCodesRepository.save(promoCodeEntity);
        return promoCodesMapper.toFullDTO(promoCodeEntity);
    }

    @Override
    @Transactional
    public PromoCodeFullResponseDTO changePromoCodeAvailability(Long id, OffsetDateTime endDate) {
        PromoCodeEntity promoCodeEntity = promoCodesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        boolean isActive = promoCodeEntity.getIsActive();
        if(!isActive)
            if(Objects.isNull(endDate) || OffsetDateTime.now().isAfter(endDate))
                promoCodeEntity.setEndDate(OffsetDateTime.now().plusDays(1));
            else
                promoCodeEntity.setEndDate(endDate);
        promoCodeEntity.setIsActive(!isActive);
        promoCodesRepository.save(promoCodeEntity);
        return promoCodesMapper.toFullDTO(promoCodeEntity);
    }

    @Override
    @Transactional
    public PromoCodeFullResponseDTO deletePromoCode(Long id) {
        PromoCodeEntity promoCodeEntity = promoCodesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        promoCodesRepository.deleteById(id);
        return promoCodesMapper.toFullDTO(promoCodeEntity);
    }
}
