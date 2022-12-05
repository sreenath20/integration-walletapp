package app.dto;

import org.junit.jupiter.api.Test;

import com.wallet.app.dto.Wallet;

import static org.junit.jupiter.api.Assertions.*;

class WalletTestShould {
    private Wallet testWallet = new Wallet();

    @Test
    void returnCorrectWalletDetailsPassedThroughConstructor(){
        Wallet testWalletWithConstructorDetails =
                new Wallet(2,"Arun", 10.0, "root");
        assertTrue(testWalletWithConstructorDetails.getId() == 2);
        assertTrue(testWalletWithConstructorDetails.getName().equals("Arun"));
        assertTrue(testWalletWithConstructorDetails.getBalance() == 10.0);
        assertTrue(testWalletWithConstructorDetails.getPassword().equals("root"));

    }
    @Test
    void setAndReturnCorrectName(){
        testWallet.setName("phonepe");
        assertTrue(testWallet.getName().equals("phonepe"));
    }
    @Test
    void setAndReturnCorrectId(){
        testWallet.setId(1);
        assertTrue(testWallet.getId() == 1);
    }
    @Test
    void setAndReturnCorrectBalance(){
        testWallet.setBalance(10.0);
        assertTrue(testWallet.getBalance() == 10.0);
    }
    @Test
    void setAndReturnCorrectPassword(){
        testWallet.setPassword("root");
        assertTrue(testWallet.getPassword().equals("root"));
    }



}