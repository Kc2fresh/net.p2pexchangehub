package net.p2pexchangehub.core.api.offer;

import java.math.BigDecimal;

public class CreateOfferCommand {

    private final String offerId;
    
    private final String userAccountId;
    
    private final String currencyOffered;
    
    private final BigDecimal amountOfferedMin;

    private final BigDecimal amountOfferedMax;
    
    private final String currencyRequested;

    private final BigDecimal requestedExchangeRate;

    public CreateOfferCommand(String offerId, String userAccountId, String currencyOffered, BigDecimal amountOfferedMin, BigDecimal amountOfferedMax, String currencyRequested,
            BigDecimal requestedExchangeRate) {
        super();
        this.offerId = offerId;
        this.userAccountId = userAccountId;
        this.currencyOffered = currencyOffered;
        this.amountOfferedMin = amountOfferedMin;
        this.amountOfferedMax = amountOfferedMax;
        this.currencyRequested = currencyRequested;
        this.requestedExchangeRate = requestedExchangeRate;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public String getCurrencyOffered() {
        return currencyOffered;
    }

    public BigDecimal getAmountOfferedMin() {
        return amountOfferedMin;
    }

    public BigDecimal getAmountOfferedMax() {
        return amountOfferedMax;
    }

    public String getCurrencyRequested() {
        return currencyRequested;
    }

    public BigDecimal getRequestedExchangeRate() {
        return requestedExchangeRate;
    }
    
}