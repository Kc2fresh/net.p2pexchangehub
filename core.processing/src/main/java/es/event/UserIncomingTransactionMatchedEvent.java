package es.event;

import java.math.BigDecimal;

public class UserIncomingTransactionMatchedEvent {

    private final String id;
    
    private final String transactionId;
    
    private final BigDecimal amount;
    
    private final String currency;
    
    private final BigDecimal newBalance;

    public UserIncomingTransactionMatchedEvent(String id, String transactionId, BigDecimal amount, String currency, BigDecimal newBalance) {
        super();
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.newBalance = newBalance;
    }

    public String getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

}
