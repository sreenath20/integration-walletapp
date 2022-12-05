//package com.wallet.app.controller;
//
//import java.util.Scanner;
//
//import com.wallet.app.dto.Wallet;
//import com.wallet.app.exception.WalletException;
//import com.wallet.app.service.WalletService;
//import com.wallet.app.service.WalletServiceImpl;
//
//public class Controller {
//    static Scanner scanner = new Scanner(System.in);
//    static Integer lastId = 2;
//    public static void main(String[] args) {
//        WalletService walletService = new WalletServiceImpl();
//        System.out.println("************************************************************************************************************");
//
//        char choiceToContinue;
//
//        do {
//            int option = getOptionThroughConsole();
//
//            switch (option){
//                case 1:
//                    String name = getNameThroughConsole();
//                    String password = getPasswordThroughConsole();
//                    String checkPassword = getPasswordThroughConsole();
//
//                    boolean passwordsMatch = password.equals(checkPassword);
//
//                    if (passwordsMatch){
//                        addUserWithGivenDetails(walletService, ++lastId , name, password);
//                    }
//                    else
//                        System.out.println("Passwords did not match. Try again.");
//                    break;
//
//                case 2:
//                    Integer walletId = getWalletIdThroughConsole();
//                    try {
//                            String userEnteredPassword = getPasswordThroughConsole();
//                            boolean userAuthenticated = walletService.login(walletId, userEnteredPassword);
//                            if (userAuthenticated){
//                                addFundToUserGivenId(walletService, walletId);
//                            }
//                            else
//                                System.out.println("Password Incorrect");
//
//                    } catch (WalletException e) {
//                        if (e.getMessage().contains("At get"))
//                            System.out.println("No such id present in database");
//                        else if (e.getMessage().contains("Amount to add must be greater than or equal to 1"))
//                            System.out.println("Amount to add must be greater than or equal to 1");
//                        else
//                            e.printStackTrace();
//                        }
//                    break;
//
//                case 3:
//                    walletId = getWalletIdThroughConsole();
//                    try {
//                            String userEnteredPassword = getPasswordThroughConsole();
//                            boolean userAuthenticated = walletService.login(walletId, userEnteredPassword);
//                            if (userAuthenticated){
//                                Double currentBalance = walletService.showWalletBalance(walletId);
//                                System.out.println("Current Balance in your wallet = " + currentBalance);
//                            }
//                            else
//                                System.out.println("Password Incorrect");
//                    } catch (WalletException e) {
//                        if(e.getMessage().contains("At get"))
//                            System.out.println("No such id present in database");
//                        else
//                            e.printStackTrace();
//                    }
//                    break;
//
//                case 4:
//                    walletId = getWalletIdThroughConsole();
//                    String userEnteredPassword = getPasswordThroughConsole();
//
//                    try {
//                        boolean userAuthenticated = walletService.login(walletId, userEnteredPassword);
//                        if (userAuthenticated) {
//                            Wallet deletedWallet = walletService.unregisterWallet(walletId, userEnteredPassword);
//                            System.out.println("Deleted the following wallet successfully " + deletedWallet);
//                        } else
//                            System.out.println("Wallet id / Password incorrect ");
//                    } catch (WalletException e) {
//                        if (e.getMessage().contains("At get"))
//                            System.out.println("No such id is present in database");
//                        else
//                            e.printStackTrace();
//                        }
//                    break;
//
//                case 5:
//                    walletId = getWalletIdThroughConsole();
//                    try {
//                            userEnteredPassword = getPasswordThroughConsole();
//                            boolean userAuthenticated = walletService.login(walletId, userEnteredPassword);
//                            if (userAuthenticated){
//                                withdrawFromWallet(walletService, walletId);
//                            }
//                            else
//                                System.out.println("Password Incorrect");
//
//                    } catch (WalletException e) {
//                        if (e.getMessage().contains("At get"))
//                            System.out.println("No such id present in database");
//                        else if (e.getMessage().contains("Insuf"))
//                            System.out.println("Insufficient Balance");
//                        else
//                            e.printStackTrace();
//                    }
//                    break;
//
//                case 6:
//                    Integer senderWalletId = getWalletIdThroughConsole();
//                    userEnteredPassword = getPasswordThroughConsole();
//                    try {
//                        boolean userAuthenticated = walletService.login(senderWalletId, userEnteredPassword);
//                        if (userAuthenticated){
//                            System.out.println("Enter details of receiver");
//                            Integer receiverWalletId = getWalletIdThroughConsole();
//                            Double amountToTransfer = getAmountToTransferThroughConsole();
//                            walletService.fundTransfer(senderWalletId,receiverWalletId,amountToTransfer);
//                        }
//                        else
//                            System.out.println("Password Incorrect");
//
//                    } catch (WalletException e) {
//                        if (e.getMessage().contains("From Id Invalid"))
//                            System.out.println("Sender Id Invalid");
//                        else if(e.getMessage().contains("Receiver Id Invalid"))
//                            System.out.println("Receiver Id Invalid");
//                        else if(e.getMessage().contains("Sender wallet Insufficient Balance"))
//                            System.out.println("Sender wallet Insufficient Balance");
//                        else if(e.getMessage().contains("Amount Low For Transfer"))
//                            System.out.println("Enter amount greater than minimum amount to transfer");
//                        else
//                            e.printStackTrace();
//                    }
//                    break;
//
//                default:
//                    System.out.println("Please enter any one of the following numbers only (1,2,3,4,5,6)");
//            }
//            System.out.println("Do you wish to continue(Y/N)");
//            choiceToContinue = scanner.next().charAt(0);
//        }while (choiceToContinue == 'Y' || choiceToContinue == 'y');
//
//        System.out.println();
//        System.out.println("Thank you for using the application ❤❤");
//        System.out.println("************************************************************************************************************");
//
//    }
//
//    private static Double getAmountToTransferThroughConsole() {
//        System.out.println("Enter the amount you want to withdraw to your wallet : ");
//        Double amountToTransfer = scanner.nextDouble();
//        scanner.nextLine();
//        return amountToTransfer;
//    }
//
//    private static void withdrawFromWallet(WalletService walletService, Integer walletId) throws WalletException {
//        Boolean withdrawSuccessful = walletService.withdrawFundsFromWallet(walletId, getAmountToWithdrawThroughConsole());
//        if(withdrawSuccessful)
//            System.out.println("Current Balance = " + walletService.showWalletBalance(walletId));
//    }
//
//    private static Double getAmountToWithdrawThroughConsole() {
//        System.out.println("Enter the amount you want to withdraw to your wallet : ");
//        double amountToWithdraw = scanner.nextDouble();
//        scanner.nextLine();
//        return amountToWithdraw;
//    }
//
//    private static int getOptionThroughConsole() {
//        System.out.println("Enter any one choice from the following : ");
//        System.out.println("1.Add/Register new user ");
//        System.out.println("2.Add funds to wallet ");
//        System.out.println("3.Check current wallet balance ");
//        System.out.println("4.Delete wallet details ");
//        System.out.println("5.Withdraw funds from wallet ");
//        System.out.println("6.Transfer funds from wallet to another person");
//        System.out.println("Enter the corresponding number : ");
//        int option = scanner.nextInt();
//        scanner.nextLine();
//        return option;
//    }
//
//    private static double getAmountToCreditThroughConsole() {
//        System.out.println("Enter the amount you want to add to your wallet : ");
//        double amountToCredit = scanner.nextDouble();
//        scanner.nextLine();
//        return amountToCredit;
//    }
//
//    public static String getNameThroughConsole() {
//        System.out.println("Enter name : ");
//        String name = scanner.nextLine();
//        return name;
//    }
//
//    private static String getPasswordThroughConsole() {
//        System.out.println("Enter your password");
//        String userEnteredPassword = scanner.nextLine();
//        return userEnteredPassword;
//    }
//
//    public static int getWalletIdThroughConsole() {
//        int walletId;
//        System.out.println("Enter your wallet Id : ");
//        walletId = scanner.nextInt();
//        scanner.nextLine();
//        return walletId;
//    }
//
//    private static void addFundToUserGivenId(WalletService walletService, int walletId) {
//        try {
//            double amountToCredit = getAmountToCreditThroughConsole();
//            Double amountAdded = walletService.addFundsToWallet(walletId, amountToCredit);
//            System.out.println("amountAdded = " + amountAdded);
//        } catch (WalletException e) {
//            if (e.getMessage().contains("At get"))
//                System.out.println("No such id present in database");
//        }
//    }
//
//    private static void addUserWithGivenDetails(WalletService walletService, int lastId, String name, String password) {
//        Double balance = 0.0;
//        try {
//            Wallet wallet = walletService.registerWallet(new Wallet(lastId + 1, name, balance, password));
//            System.out.println("Your new wallet details : " + wallet);
//        } catch (WalletException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
