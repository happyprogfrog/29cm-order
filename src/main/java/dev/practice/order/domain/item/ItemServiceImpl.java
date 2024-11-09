package dev.practice.order.domain.item;

import dev.practice.order.domain.item.option.ItemOption;
import dev.practice.order.domain.item.option.ItemOptionStore;
import dev.practice.order.domain.item.optionGroup.ItemOptionGroup;
import dev.practice.order.domain.item.optionGroup.ItemOptionGroupStore;
import dev.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final PartnerReader partnerReader;
    private final ItemStore itemStore;
    private final ItemReader itemReader;
    private final ItemOptionGroupStore itemOptionGroupStore;
    private final ItemOptionStore itemOptionStore;

    @Override
    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        // 1. get partnerId
        var partner = partnerReader.getPartner(partnerToken);
        var partnerId = partner.getId();

        // 2. item store
        var initItem = command.toEntity(partnerId);
        var item = itemStore.store(initItem);

        // 3. itemOptionGroup + itemOption store
        command.getItemOptionGroupRequestList().forEach(requestItemOptionGroup -> {
            // itemOptionGroup store
            var initItemOptionGroup = ItemOptionGroup.builder()
                    .item(item)
                    .ordering(requestItemOptionGroup.getOrdering())
                    .itemOptionGroupName(requestItemOptionGroup.getItemOptionGroupName())
                    .build();
            var itemOptionGroup = itemOptionGroupStore.store(initItemOptionGroup);

            // itemOptionStore
            requestItemOptionGroup.getItemOptionRequestList().forEach(requestItemOption -> {
                var initItemOption = ItemOption.builder()
                        .itemOptionGroup(itemOptionGroup)
                        .ordering(requestItemOption.getOrdering())
                        .itemOptionName(requestItemOption.getItemOptionName())
                        .itemOptionPrice(requestItemOption.getItemOptionPrice())
                        .build();
                itemOptionStore.store(initItemOption);
            });
        });

        // 4. return itemToken
        return item.getItemToken();
    }

    @Override
    @Transactional
    public void changeOnSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeOnSale();
    }

    @Override
    @Transactional
    public void changeEndOfSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeEndOfSale();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        var itemOptionGroupInfoList = itemReader.getItemOptionSeries(item);
        return new ItemInfo.Main(item, itemOptionGroupInfoList);
    }
}
