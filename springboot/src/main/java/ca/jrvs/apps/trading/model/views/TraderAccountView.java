package ca.jrvs.apps.trading.model.views;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;

public class TraderAccountView {

    private Integer traderId;
    private Integer accountId;
    private Double amount;
    private String firstName;
    private String lastName;


    public TraderAccountView(Trader trader, Account account) {

        this.traderId = trader.getId();
        this.accountId = account.getId();
        this.amount = account.getAmount();
        this.firstName = trader.getFirstName();
        this.lastName = trader.getLastName();

    }

    public Integer getTraderId() {
        return traderId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}