package com.epam.trainerworkload.util.transaction;


import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionIdGeneratorTest {

    @Test
    void generateTransactionId_validUUID() {
        String transactionId = TransactionIdGenerator.generateTransactionId();

        assertNotNull(transactionId, "Transaction ID should not be null");
        assertDoesNotThrow(() -> UUID.fromString(transactionId), "Transaction ID should be a valid UUID");

        UUID uuid = UUID.fromString(transactionId);
        assertTrue(transactionId.equals(uuid.toString()), "Generated transaction ID should match the UUID format");
    }
}
