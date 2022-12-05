package com.wallet.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletDaoException;

public class WalletDaoImpl implements WalletDao {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/walletapplication?useSSL=false";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "root";
    private final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private final String INSERT_NEW_WALLET_SQL = "insert into wallets (id, name, balance, password) values (?,?,?,?)";
    private final String GET_WALLET_SQL = "select * from " + "wallets" + " where id =?";
    private final String UPDATE_WALLET_SQL = "update " + "wallets" + " set name=?, balance=?, password=? where id=?";
    private final String DELETE_WALLET_SQL = "delete from " + "wallets" + " where id=?";

    public WalletDaoImpl() {
    }
    private Connection getConnection(){

        Connection connection = null;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }
    @Override
    public Wallet addWallet(Wallet newWallet) throws WalletDaoException {
        try{
            Connection connection =getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_WALLET_SQL);
            preparedStatement.setInt(1, newWallet.getId());
            preparedStatement.setString(2, newWallet.getName());
            preparedStatement.setDouble(3, newWallet.getBalance());
            preparedStatement.setString(4, newWallet.getPassword());

            preparedStatement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new WalletDaoException("Sql Exception Occurred Redundant Id");
        }
        return newWallet;
    }
    @Override
    public Wallet getWalletById(Integer walletId) throws WalletDaoException {
        try {
            Connection connection = this.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(GET_WALLET_SQL);
            preparedStatement.setInt(1, walletId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            Double balance = resultSet.getDouble("balance");
            String password = resultSet.getString("password");

            Wallet wallet = new Wallet(walletId, name, balance, password);
            connection.close();
            return wallet;

        } catch (SQLException e) {
            throw new WalletDaoException("At get, Invalid ID "+ e.getMessage());
        }
    }
    @Override
    public Wallet updateWallet(Wallet updateWallet) throws WalletDaoException {
        Connection connection = this.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WALLET_SQL);
            preparedStatement.setString(1, updateWallet.getName());
            preparedStatement.setDouble(2, updateWallet.getBalance());
            preparedStatement.setString(3, updateWallet.getPassword());
            preparedStatement.setInt(4, updateWallet.getId());

            boolean rowUpdated = preparedStatement.executeUpdate() > 0;
            connection.close();
            if (!rowUpdated)
                throw new WalletDaoException("At update, Invalid ID");
        } catch (Exception e) {
            throw new WalletDaoException("At update "+ e.getMessage());
        }
        return updateWallet;
    }
    @Override
    public Wallet deleteWalletById(Integer walletId) throws WalletDaoException {
        try {
            Wallet walletToDelete = this.getWalletById(walletId);
            if (walletToDelete != null) {
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WALLET_SQL);
                preparedStatement.setInt(1, walletId);
                preparedStatement.executeUpdate();
                connection.close();
                return walletToDelete;
            }

            return new Wallet();

        } catch (Exception e) {
            throw new WalletDaoException("At delete "+ e.getMessage());
        }
    }
}
