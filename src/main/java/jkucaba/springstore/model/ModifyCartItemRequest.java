package jkucaba.springstore.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ModifyCartItemRequest(
        @NotNull UUID cartItemId,
        @Positive int quantity
) {
}
