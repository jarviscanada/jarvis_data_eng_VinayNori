package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TraderDao traderDao;

    private Account account;
    private Trader trader;

    @Before
    public void setUp() throws Exception {

        trader = new Trader();
        trader.setId(1);
        trader.setFirstName("Jeff");
        trader.setLastName("Test");
        trader.setCountry("CAN");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("jeff@example.com");
        traderDao.save(trader);

        account = new Account();
        account.setId(1);
        account.setTrader_id(trader.getId());
        account.setAmount(10d);
        accountDao.save(account);
    }

    @After
    public void tearDown() throws Exception {
        accountDao.deleteAll();
        traderDao.deleteById(trader.getId());
    }

    @Test
    public void getTableName() {
        assertEquals("account", accountDao.getTableName());
    }

    @Test
    public void getIdColumnName() {
        assertEquals("id", accountDao.getIdColumnName());
    }

    @Test
    public void getEntityClass() {
        assertEquals(Account.class, accountDao.getEntityClass());
    }

    @Test
    public void saveAll() {
        Account account1 = new Account();
        account1.setId(2);
        account1.setTrader_id(trader.getId());
        account1.setAmount(10d);

        Account account2 = new Account();
        account2.setId(3);
        account2.setTrader_id(trader.getId());
        account2.setAmount(10d);

        List<Account> accounts = (List<Account>) accountDao.saveAll(Arrays.asList(account1, account2));
        Account rq1 = accountDao.findById(2).get();
        Account rq2 = accountDao.findById(3).get();

        assertEquals(3, accountDao.count());
        assertEquals(accounts.get(0).getId(), rq1.getId());
        assertEquals(accounts.get(1).getId(), rq2.getId());
    }

    @Test
    public void save() {
        Account account1 = new Account();
        account1.setId(2);
        account1.setTrader_id(trader.getId());
        account1.setAmount(10d);

        Account acc = accountDao.save(account1);
        assertEquals(account1.getId(), acc.getId());
        assertEquals(trader.getId(), acc.getTrader_id());
        assertEquals(account.getAmount(), acc.getAmount());
    }

    @Test
    public void findById() {
        Account acc = accountDao.findById(account.getId()).get();
        assertEquals(account.getId(), acc.getId());
        assertEquals(trader.getId(), acc.getTrader_id());
        assertEquals(account.getAmount(), acc.getAmount());
    }

    @Test
    public void existsById() {
        assertTrue(accountDao.existsById(account.getId()));
    }

    @Test
    public void findAll() {
        List<Account> accounts = accountDao.findAll();
        assertEquals(1, accountDao.count());
        assertEquals(account.getId(), accounts.get(0).getId());
    }

    @Test
    public void findAllById() {
        Account account1 = new Account();
        account1.setId(2);
        account1.setTrader_id(trader.getId());
        account1.setAmount(10d);
        accountDao.save(account1);

        List<Account> accounts = accountDao
                .findAllById(Arrays.asList(account.getId(), account1.getId()));
        assertEquals(2, accounts.size());
        assertEquals(account.getId(), accounts.get(0).getId());
        assertEquals(account1.getId(), accounts.get(1).getId());
    }

    @Test
    public void deleteById() {
        assertEquals(1, accountDao.count());
        accountDao.deleteById(account.getId());
        assertEquals(0, accountDao.count());
    }

    @Test
    public void count() {
        assertEquals(1, accountDao.count());
    }

    @Test
    public void deleteAll() {
        accountDao.deleteAll();
        assertEquals(0, accountDao.count());
    }
}