package dev.practice.order.infrastructure.partner;

import dev.practice.order.common.exception.InvalidParamException;
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
        if (StringUtils.isEmpty(initPartner.getPartnerToken())) throw new InvalidParamException("initPartner.getPartnerToken()");
        if (StringUtils.isEmpty(initPartner.getPartnerName())) throw new InvalidParamException("initPartner.getPartnerName()");
        if (StringUtils.isEmpty(initPartner.getBusinessNo())) throw new InvalidParamException("initPartner.getBusinessNo()");
        if (StringUtils.isEmpty(initPartner.getEmail())) throw new InvalidParamException("initPartner.getEmail()");
        if (initPartner.getStatus() == null) throw new InvalidParamException("initPartner.getStatus()");

        return partnerRepository.save(initPartner);
    }
}
