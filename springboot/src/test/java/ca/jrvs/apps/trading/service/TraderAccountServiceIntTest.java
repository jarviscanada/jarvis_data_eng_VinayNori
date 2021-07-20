package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

    private TraderAccountView savedView;
    @Autowired
    private TraderAccountService traderAccountService;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private AccountDao accountDao;

    private Trader trader;
    private Account account;

    @Before
    public void setUp() throws Exception {
        trader = new Trader();
        trader.setId(1);
        trader.setFirstName("Jeff");
        trader.setLastName("Test");
        trader.setCountry("CAN");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("jeff@example.com");
    }

    @After
    public void tearDown() throws Exception {
        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void createTraderAndAccount() {
        savedView = traderAccountService.createTraderAndAccount(trader);
        assertEquals(1, accountDao.count());
        assertEquals(1, traderDao.count());
    }

    @Test
    public void deleteTraderById() {
        savedView = traderAccountService.createTraderAndAccount(trader);
        traderAccountService.deleteTraderById(1);

        assertEquals(0, accountDao.count());
        assertEquals(0, traderDao.count());
    }

    @Test
    public void deposit() {
        savedView = traderAccountService.createTraderAndAccount(trader);

        Account account = traderAccountService.deposit(1, 20d);
        assertEquals(new Double(20), account.getAmount());
    }

    @Test
    public void withdraw() {
        savedView = traderAccountService.createTraderAndAccount(trader);

        Account account = traderAccountService.deposit(1, 20d);
        Account account2 = traderAccountService.withdraw(1, 19d);
        assertEquals(new Double(1), account2.getAmount());
        assertEquals(account.getId(), account2.getId());
    }
}