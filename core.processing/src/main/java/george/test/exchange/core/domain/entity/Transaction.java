package george.test.exchange.core.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import george.test.exchange.core.domain.TransactionState;

@Entity 
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {
    
    @Id
    private String id = UUID.randomUUID().toString();

    @Version
    private long version;
    
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = EXCHANGE_OFFER)
    private ExchangeOffer exchangeOffer;
    public static final String EXCHANGE_OFFER = "exchangeOffer";
    
    @ManyToOne(optional = false)
    private UserAccount owner;
    
    @Column(nullable = false)
    private TransactionState state;
    public static final String STATE = "state";
    
    public Transaction() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ExchangeOffer getExchangeOffer() {
        return exchangeOffer;
    }

    public void setExchangeOffer(ExchangeOffer exchangeOffer) {
        this.exchangeOffer = exchangeOffer;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }
    
}