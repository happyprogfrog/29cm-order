package dev.practice.order.domain.item;

import com.google.common.collect.Lists;
import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import dev.practice.order.domain.item.optionGroup.ItemOptionGroup;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "items")
public class Item extends AbstractEntity {
    private static final String ITEM_PREFIX = "itm_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemToken;
    private Long partnerId;
    private String itemName;
    private Long itemPrice;

    // Item : ItemOptionGroup = 1 : N
    // mappedBy - 연관 관계의 주인인 쪽의 property 작성
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.PERSIST)
    private List<ItemOptionGroup> itemOptionGroupList = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("판매준비중"),
        ON_SALE("판매중"),
        END_OF_SALE("판매종료");

        private final String description;
    }

    @Builder
    public Item(Long partnerId, String itemName, Long itemPrice) {
        if (partnerId == null) throw new InvalidParameterException("Item.partnerId");
        if (StringUtils.isEmpty(itemName)) throw new InvalidParameterException("Item.itemName");
        if (itemPrice == null) throw new InvalidParameterException("Item.itemPrice");

        this.partnerId = partnerId;
        this.itemToken = TokenGenerator.randomCharacterWithPrefix(ITEM_PREFIX);
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.status = Status.PREPARE;
    }

    public void changeOnSale() {
        this.status = Status.ON_SALE;
    }

    public void changeEndOfSale() {
        this.status = Status.END_OF_SALE;
    }

    public boolean availableSales() {
        return this.status == Status.ON_SALE;
    }
}
