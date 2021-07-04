package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraderAccountService {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
                                PositionDao positionDao, SecurityOrderDao securityOrderDao){
        this.accountDao = accountDao;
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     *
     * Assumption to simplify the logic, each trader has only one account where traderId == accountId
     *
     * @param trader cannot be null. All fields cannot be null except for id (auto-generated by db)
     * @return traderAccountView
     * @throws IllegalArgumentException if a trader has null fields or id is not null.
     */
    public TraderAccountView createTraderAndAccount(Trader trader){
        if(trader==null){
            throw new IllegalArgumentException("Trader cannot be null");
        }
        Trader savedTrader = traderDao.save(trader);
        Account account = new Account();
        account.setTrader_id(trader.getId());
        account.setAmount(0.0);
        Account savedAccount = accountDao.save(account);
        return new TraderAccountView(savedTrader,savedAccount);
    }

    /**
     * A trader can be deleted if it has no open position and 0 cash balance
     * - validate traderId
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId must not be null
     * @throws IllegalArgumentException if traderId is null or not found or unable to delete
     */
    public void deleteTraderById(Integer traderId){
        if(traderId == null || !traderDao.existsById(traderId)){
            throw new IllegalArgumentException("TraderID is null or not found");
        }

        Account account = accountDao.findByTraderId(traderId).get();
        Optional<Position> position = positionDao.findById(account.getId());
        if(account.getAmount()==0 && !position.isPresent()){
            securityOrderDao.deleteByAccountId(account.getId());
            accountDao.deleteById(account.getId());
            traderDao.deleteById(traderId);
        }
    }

    /**
     * Deposit a fund to an account by traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId must not be null
     * @param fund must be greater than 0
     * @return updated Account
     * @throws IllegalArgumentException if traderId is null or not found, and fund is less or equal to 0
     */
    public Account deposit(Integer traderId, Double fund) {
        if(traderId == null || !traderDao.existsById(traderId) || fund <= 0){
            throw new IllegalArgumentException("Cannot deposit funds. Invalid inputs");
        }
        Account account = accountDao.findByTraderId(traderId).get();
        account.setAmount(account.getAmount() + fund);
        accountDao.save(account);
        return account;
    }

    /**
     * Withdraw a fund to an account by traderId
     *
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader ID
     * @param fund amount can't be 0
     * @return updated Account
     * @throws IllegalArgumentException if traderId is null or not found, fund is less or equal to 0, and insufficient fund
     */
    public Account withdraw(Integer traderId, Double fund){
        if(traderId == null || !traderDao.existsById(traderId) || fund <= 0){
            throw new IllegalArgumentException("Cannot deposit funds. Invalid inputs");
        }
        Account account = accountDao.findByTraderId(traderId).get();
        if(account.getAmount() < fund){
            throw new IllegalArgumentException("Insufficient Funds");
        }
        account.setAmount(account.getAmount() - fund);
        accountDao.save(account);
        return account;
    }

}