package com.epam.crmgym.util.transaction;

import java.util.UUID;

public class TransactionIdGenerator {
    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
