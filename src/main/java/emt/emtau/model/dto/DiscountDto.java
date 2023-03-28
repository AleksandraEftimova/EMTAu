package emt.emtau.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountDto {

    private LocalDateTime validUntil;

    public DiscountDto(){}

    public DiscountDto(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
