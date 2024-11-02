package dev.practice.order.domain.partner;

public record PartnerCommand(
        String partnerName,
        String businessNo,
        String email
) {
    public Partner toEntity() {
        return Partner.builder()
                .partnerName(partnerName)
                .businessNo(businessNo)
                .email(email)
                .build();
    }
}
