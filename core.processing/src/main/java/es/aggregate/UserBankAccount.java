package es.aggregate;

public class UserBankAccount {

    private final String id;

    private final String currency;

    private final String country;

    private final String accountNumber;

    public UserBankAccount(String id, String currency, String country, String accountNumber) {
        super();
        this.id = id;
        this.currency = currency;
        this.country = country;
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserBankAccount other = (UserBankAccount) obj;
        if (accountNumber == null) {
            if (other.accountNumber != null)
                return false;
        } else if (!accountNumber.equals(other.accountNumber))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserBankAccount [id=" + id + ", currency=" + currency + ", country=" + country + ", accountNumber=" + accountNumber + "]";
    }

}
