package by.ikrotsyuk.bsuir.paymentservice.dto.request;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
        @Schema(description = "account user id")
        @Min(1)
        @NotNull
        Long userId,

        @Schema(description = "account type(DRIVER, PASSENGER)")
        @NotNull
        AccountTypes accountType
) {
}
