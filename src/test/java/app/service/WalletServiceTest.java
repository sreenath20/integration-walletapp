package app.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;
import com.wallet.app.service.WalletService;
import com.wallet.app.service.WalletServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletServiceTestShould {
    public static final double INVALID_AMOUNT_TO_ADD = 0.0;
    public static final double INVALID_AMOUNT_FOR_TRANSFER = 0.0;
    private final Integer  VALID_ID = 1;
    private final Integer RECEIVER_VALID_ID = 2;
    private final Integer INVALID_ID = 1111;
    private static Double expectedBalanceOfGivenId = 10.0;
    private final Double VALID_TRANSFER_AMOUNT = 10.0;
    private final Double INVALID_WITHDRAW_AMOUNT = 100000.0;
    private WalletService walletService = new WalletServiceImpl();

    @Test
    @Order(1)
    void addedNewUserCorrectly() throws WalletException {
        WalletService walletService = new WalletServiceImpl();

        Wallet testWallet = new Wallet(3, "GPay", 0.0, "root");
        Wallet addedWallet = walletService.registerWallet(testWallet);
        assertEquals(addedWallet.getId(), testWallet.getId());
    }
    @Test
    @Order(2)
    void returnCorrectBalance_When_ValidIdGivenForBalanceCheck() throws WalletException {

        Double returnedBalance = walletService.showWalletBalanceById(VALID_ID);
        assertEquals(expectedBalanceOfGivenId,returnedBalance);
    }
    @Test
    @Order(3)
    void returnAmountAdded_When_ValidIdGiven() throws WalletException{
        expectedBalanceOfGivenId += VALID_TRANSFER_AMOUNT;
        assertEquals(VALID_TRANSFER_AMOUNT, walletService.addFundsToWallet(VALID_ID,VALID_TRANSFER_AMOUNT));

    }
    @Test
    @Order(4)
    void returnTrue_When_TransferFundsSuccessful() throws WalletException{
        assertTrue(walletService.fundTransfer(VALID_ID, RECEIVER_VALID_ID, VALID_TRANSFER_AMOUNT));
        assertTrue(walletService.fundTransfer(RECEIVER_VALID_ID, VALID_ID, VALID_TRANSFER_AMOUNT));
    }
    @Test
    @Order(20)
    void returnDeletedWallet_When_UnRegisterWalletSuccessful() throws WalletException{

        Integer validIdToDelete = 3;
        String correctPassword = "root";

        Wallet deletedWallet = walletService.unregisterWallet(validIdToDelete, correctPassword);

        assertEquals(validIdToDelete, deletedWallet.getId());
    }
    @Test
    @Order(6)
    void returnTrue_When_WithdrawAmountSuccessful() throws WalletException{
        assertTrue(walletService.withdrawFundsFromWallet(VALID_ID, VALID_TRANSFER_AMOUNT));
        expectedBalanceOfGivenId -= VALID_TRANSFER_AMOUNT;
    }
    @Test
    @Order(7)
    void throwWalletException_When_RedundantIdAdded() {
        // given
        Integer alreadyPresentId = 1;

        // when
        Executable executable = () -> walletService.registerWallet(new Wallet(alreadyPresentId,"p", 0.0,"root"));

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Redundant Id"));
    }
    @Test
    @Order(8)
    void throwWalletException_When_InvalidIdForBalanceCheck() {
        // given

        // when
        Executable executable = () -> walletService.showWalletBalanceById(INVALID_ID);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Invalid ID"));
    }
    @Test
    @Order(9)
    void throwWalletException_When_InvalidIdForAddFunds() {
        // given
        Double testAmount = 10.0;

        // when
        Executable executable = () -> walletService.addFundsToWallet(INVALID_ID, testAmount);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Invalid ID"));
    }
    @Test
    @Order(10)
    void throwWalletException_When_InvalidSenderIdForFundTransfer() {
        // given
        Integer receiverTestId = 100;
        Double testAmount = 20.0;

        // when
        Executable executable = () -> walletService.fundTransfer(INVALID_ID, receiverTestId, testAmount);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("From Id Invalid"));
    }
    @Test
    @Order(11)
    void throwWalletException_When_InvalidReceiverIdForFundTransfer() {
        // given
        Double testAmount = 20.0;

        // when
        Executable executable = () -> walletService.fundTransfer(VALID_ID, INVALID_ID, testAmount);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Receiver Id Invalid"));
    }
    @Test
    @Order(12)
    void throwWalletException_When_TransferAmountGreaterThanSenderBalance(){

        Executable executable = () -> walletService.fundTransfer(VALID_ID, RECEIVER_VALID_ID, INVALID_WITHDRAW_AMOUNT);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Sender wallet Insufficient Balance"));
    }
    @Test
    @Order(21)
    void throwWalletException_When_InvalidAmountToAdd(){

        Executable executable = () -> walletService.addFundsToWallet(VALID_ID, INVALID_AMOUNT_TO_ADD);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Amount to add must be greater"));
    }
    @Test
    @Order(13)
    void throwWalletException_When_InvalidIdForDelete() {
        // given

        // when
        Executable executable = () -> walletService.unregisterWallet(INVALID_ID, "");

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Invalid ID"));
    }
    @Test
    @Order(14)
    void throwWalletException_When_InvalidIdForWithdrawFunds() {
        // given
        Double testAmount = 10.0;

        // when
        Executable executable = () -> walletService.withdrawFundsFromWallet(INVALID_ID, testAmount);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Invalid ID"));
    }
    @Test
    @Order(15)
    void throwWalletException_When_InvalidAmountForWithdrawFunds() {
        // given

        // when
        Executable executable = () -> walletService.withdrawFundsFromWallet(VALID_ID, INVALID_WITHDRAW_AMOUNT);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Insufficient Balance"));
    }
    @Test
    @Order(16)
    void throwWalletException_When_InvalidAmountForTransferFunds() {
        // given

        // when
        Executable executable = () -> walletService.fundTransfer(VALID_ID,RECEIVER_VALID_ID, INVALID_AMOUNT_FOR_TRANSFER);

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Amount Low For Transfer"));
    }
    @Test
    @Order(17)
    void throwWalletException_When_InvalidIdForLogin() {
        // given
        // when
        Executable executable = () -> walletService.login(INVALID_ID, "dummy");

        // then
        WalletException thrown = assertThrows(WalletException.class, executable);
        assertTrue(thrown.getMessage().contains("Invalid ID"));
    }
    @Test
    @Order(18)
    void returnFalse_When_WrongPasswordEntered() throws WalletException {
        // given
        String wrongPassword = "1";

        // when
        boolean authenticated = walletService.login(VALID_ID, wrongPassword);

        // then
        assertFalse(authenticated);
    }
    @Test
    @Order(19)
    public void returnTrue_When_LoginSuccessful() throws WalletException {

        String givenPassword = "root";
        assertTrue(walletService.login(VALID_ID, givenPassword));
    }
}