package dev.practice.order.infrastructure.partner;

import dev.practice.order.domain.partner.Partner;
import dev.practice.order.domain.partner.PartnerStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PartnerStoreImpl implements PartnerStore {
    private final PartnerRepository partnerRepository;

    @Override
    public Partner store(Partner initPartner) {
        if (StringUtils.isEmpty(initPartner.getPartnerName())) throw new RuntimeException("Partner token is null");
        if (StringUtils.isEmpty(initPartner.getPartnerName())) throw new RuntimeException("Partner name is null");
        if (StringUtils.isEmpty(initPartner.getBusinessNo())) throw new RuntimeException("Business no is null");
        if (StringUtils.isEmpty(initPartner.getEmail())) throw new RuntimeException("Email is null");
        if (initPartner.getStatus() == null) throw new RuntimeException("Status is null");

        return partnerRepository.save(initPartner);
    }
}
