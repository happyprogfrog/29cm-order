package dev.practice.order.domain.order.payment.validator;

import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(value = 1)
@Component
public class PayAmountValidator implements PaymentValidator {

    @Override
    public void validate(Order order, OrderCommand.PaymentRequest paymentRequest) {

    }
}
