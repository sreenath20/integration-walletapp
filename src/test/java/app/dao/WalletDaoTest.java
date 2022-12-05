package app.dao;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.wallet.app.dao.WalletDao;
import com.wallet.app.dao.WalletDaoImpl;
import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletDaoException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletDaoTestShould {

    public static final int VALID_ID = 3;
    public static final int INVALID_ID = 100;
    private Wallet testNewWallet = new Wallet(VALID_ID, "Arun", 0.0, "root");
    private WalletDao testWalletDao = new WalletDaoImpl();
    @Test
    @Order(1)
    public void returnWallet_When_walletAddedCorrectly() throws WalletDaoException {
        Wallet addedWallet = testWalletDao.addWallet(testNewWallet);
        assertEquals(testNewWallet.getId(), addedWallet.getId());
    }
    @Test
    @Order(2)
    public void returnWallet_When_walletRetrievedCorrectly() throws WalletDaoException {
        Wallet retrievedWallet = testWalletDao.getWalletById(VALID_ID);
        assertEquals(VALID_ID, retrievedWallet.getId());
    }
    @Test
    @Order(3)
    public void returnWallet_When_walletUpdatedCorrectly() throws WalletDaoException {
        String testUpdateName = "test";
        Double testUpdateBalance = 10.0;
        String testUpdatePassword = "root";
        Wallet newWalletToUpdate = new Wallet(VALID_ID, testUpdateName, testUpdateBalance, testUpdatePassword);
        Wallet updatedWallet = testWalletDao.updateWallet(newWalletToUpdate);
        assertEquals(VALID_ID, updatedWallet.getId());
        assertEquals(testUpdateName, updatedWallet.getName());
        assertEquals(testUpdatePassword, updatedWallet.getPassword());
        assertEquals(testUpdateBalance, updatedWallet.getBalance());
    }
    @Test
    @Order(4)
    public void returnWallet_When_walletDeletedCorrectly() throws WalletDaoException {
        Wallet deletedWallet = testWalletDao.deleteWalletById(VALID_ID);
        assertEquals(VALID_ID, deletedWallet.getId());
    }
    @Test
    @Order(5)
    public void throwWalletDaoException_When_redundantIdGivenForAdding(){

        int redundantId = 1;
        WalletDaoException thrown = assertThrows(WalletDaoException.class
                , () -> testWalletDao.addWallet(new Wallet(redundantId, "Gpay", 0.0, "root")));

        assertTrue(thrown.getMessage().contains("Sql Exception Occurred Redundant Id"));

    }
    @Test
    @Order(6)
    public void throwWalletDaoException_When_invalidIdGivenForRetrieving(){

        WalletDaoException thrown = assertThrows(WalletDaoException.class
                , () -> testWalletDao.getWalletById(INVALID_ID));

        assertTrue(thrown.getMessage().contains("At get, Invalid ID"));

    }
    @Test
    @Order(7)
    public void throwWalletDaoException_When_invalidIdGivenForUpdating(){

        WalletDaoException thrown = assertThrows(WalletDaoException.class
                , () -> testWalletDao.updateWallet(new Wallet(INVALID_ID, "Gpay", 0.0, "root")));

        assertTrue(thrown.getMessage().contains("At update, Invalid ID"));

    }

}