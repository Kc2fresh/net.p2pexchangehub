package george.test.exchange.core.processing.service.bank.provider.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.simple.bank.api.Account;
import org.simple.bank.api.ClientBankApi;
import org.simple.bank.api.JacksonJsonProvider;
import org.simple.bank.api.Transaction;

import george.test.exchange.core.domain.ExternalBankType;
import george.test.exchange.core.domain.entity.TransactionRequestExternal;
import george.test.exchange.core.processing.service.ConfigurationService;
import george.test.exchange.core.processing.service.bank.BankProvider;
import george.test.exchange.core.processing.service.bank.BankProviderBase;
import george.test.exchange.core.processing.service.bank.BankProviderException;

public class TestBankProvider extends BankProviderBase<TestBankAccount, TestBankTransaction, TestBankContext> {
    
    public static final String CONFIG_WS_URL = BankProvider.CONFIG_BANK_PROVIDER_PREFIX + ".test.api_url";
    
    @Inject
    private ConfigurationService configurationService;

    private ClientBankApi client;
    
    public TestBankProvider() {
        super();
    }

    @PostConstruct
    private void init() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder()
                .socketTimeout(3, TimeUnit.SECONDS)
//                .defaultProxy("localhost", 8888)
//                .defaultProxy("jos-repo-server.datacom.co.nz", 3128)
                .establishConnectionTimeout(7, TimeUnit.SECONDS)
                .disableTrustManager()
                .register(JacksonJsonProvider.class)
                .build();
        
        client =  resteasyClient.target(configurationService.getValueString(CONFIG_WS_URL)).proxy(ClientBankApi.class);        
    }
    
    @Override
    public ExternalBankType getType() {
        return ExternalBankType.TEST;
    }

    
    @Override
    protected TestBankContext loginInternal(TestBankAccount bankAccount) throws BankProviderException {
        try {
            String sessionId = client.login(bankAccount.getUsername(), bankAccount.getPassword());
            return new TestBankContext(bankAccount, sessionId);
        } catch (WebApplicationException e) {
            throw new BankProviderException(e);
        }
    }

    @Override
    protected List<TestBankTransaction> listTransactionsInternal(TestBankContext context, Date fromDate, Date toDate) throws BankProviderException {
        List<Transaction> listTransactions;
        try {
            listTransactions = client.listTransactions(context.getSessionId(), context.getBankAccount().getAccountNumber()).stream().filter(i -> i.getDate().after(fromDate) && i.getDate().before(toDate)).collect(Collectors.toList());
        } catch (WebApplicationException e) {
            throw new BankProviderException(e);
        }
        
        List<TestBankTransaction> result = new ArrayList<>();
        for (Transaction tr : listTransactions) {
            TestBankTransaction bankTr = new TestBankTransaction();
            bankTr.setAmount(tr.getAmount());
            bankTr.setBankAccount(context.getBankAccount());
            bankTr.setDate(tr.getDate());
            bankTr.setTbDetail(tr.getDetail());
            bankTr.setTbId(tr.getId());
            bankTr.setTbFromAccount(tr.getFromAccount());
            result.add(bankTr);
        }
        return result;
    }

    @Override
    protected void processTransactionRequestInternal(TestBankContext context, TransactionRequestExternal transactionRequest) throws BankProviderException {
        Transaction transaction = new Transaction();
        transaction.setToAccount(transactionRequest.getRecipientAccountNumber());
        transaction.setDetail(transactionRequest.getDetailInfo());
        transaction.setAmount(transactionRequest.getAmount());
        
        try {
            client.pay(context.getSessionId(), context.getBankAccount().getAccountNumber(), transaction);
        } catch (WebApplicationException e) {
            throw new BankProviderException(e);
        }
    }

    @Override
    protected BigDecimal getBalanceInternal(TestBankContext context) throws BankProviderException {
        try {
            return client.listAccounts(context.getSessionId()).stream().filter(a -> context.getBankAccount().getAccountNumber().equals(a.getNumber())).map(Account::getBalance).findAny().orElse(BigDecimal.ZERO);
        } catch (WebApplicationException e) {
            throw new BankProviderException(e);
        }
    }

}

