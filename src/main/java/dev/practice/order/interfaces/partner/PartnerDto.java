package dev.practice.order.interfaces.partner;

import dev.practice.order.domain.partner.Partner;
import dev.practice.order.domain.partner.PartnerCommand;
import dev.practice.order.domain.partner.PartnerInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PartnerDto {
    @Getter
    @Setter
    @ToString
    public static class RegisterRequest {
        @NotEmpty(message = "partnerName 는 필수값입니다")
        private String partnerName;

        @NotEmpty(message = "businessNo 는 필수값입니다")
        private String businessNo;

        @Email(message = "email 형식에 맞아야 합니다")
        @NotEmpty(message = "email 는 필수값입니다")
        private String email;

        public PartnerCommand toCommand() {
            return new PartnerCommand(partnerName, businessNo, email);
        }
    }

    public record RegisterResponse(
            String partnerToken,
            String partnerName,
            String businessNo,
            String email,
            Partner.Status status
    ) {
        public RegisterResponse(PartnerInfo partnerInfo) {
            this(
                    partnerInfo.partnerToken(),
                    partnerInfo.partnerName(),
                    partnerInfo.businessNo(),
                    partnerInfo.email(),
                    partnerInfo.status()
            );
        }
    }
}
