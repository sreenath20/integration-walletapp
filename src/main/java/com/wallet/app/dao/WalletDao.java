package com.wallet.app.dao;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletDaoException;
import com.wallet.app.exception.WalletException;

public interface WalletDao {

    Wallet addWallet(Wallet newWallet) throws WalletDaoException;
    Wallet getWalletById(Integer walletId) throws WalletDaoException;
    Wallet updateWallet(Wallet updateWallet) throws WalletDaoException;
    Wallet deleteWalletById(Integer walletId) throws WalletDaoException;


}
