import java.util.*;

public class Main{
    public static void main(String[] args){
        System.out.println("--------------------System Start-----------------");
        System.out.println("User 1 login.");
        User usr1 = new User("Chen", 1);
        System.out.println(String.format("Create Wallet for %s, uid: %s.",
                    usr1.getName(), usr1.getID()));
        usr1.createWallet();
        System.out.println(String.format("Create Account for %s, uid: %s.",
                    usr1.getName(), usr1.getID()));
        Account account1 = usr1.createAccount("chen1");
        System.out.println(String.format(String.format("Balance for %s: %f",
                    account1.getName(), usr1.getAccountBalance(account1))));
        System.out.println(String.format("Top up for %s", account1.getName()));
        account1.deposit(10000);
        System.out.println(String.format(String.format("Balance for %s: %f",
                    account1.getName(),
                    account1.getUser().getAccountBalance(account1))));
        System.out.println(String.format("Create 2 new account for %s, from %s",
                    usr1.getName(), account1.getName()));

        Account account2 = usr1.createAccount("chen2", account1, 1000);
        Account account3 = usr1.createAccount("chen3", account1, 1000);
        System.out.println(String.format(String.format("Balance for %s: %f",
                    account1.getName(),
                    usr1.getAccountBalance(account1))));
        System.out.println(String.format(String.format("Balance for %s: %f",
                    account2.getName(),
                    usr1.getAccountBalance(account2))));
        System.out.println(String.format(String.format("Balance for %s: %f",
                    account3.getName(),
                    usr1.getAccountBalance(account3))));
        Account account4 = usr1.createAccount("account_4", account1, 9000);
        Account default1 = usr1.getDefaultAccount();
        usr1.setDefaultAccount(account1);
        default1 = usr1.getDefaultAccount();
        usr1.removeAccount(account1);
        usr1.setDefaultAccount(account3);

        System.out.println(String.format("Get Wallet Balance for User %s",
                    usr1.getName()));
        for(Account account: usr1.getWallet()){
          System.out.println(String.format("Account: %s, Balance: $%.02f",
                      account.getName(), usr1.getAccountBalance(account)));
        }

        System.out.println("User 2 login. Start to mess up.");
        User usr2 = new User("Zoe", 2);
        System.out.println(String.format("Create Wallet for %s, uid: %s.",
                    usr2.getName(), usr2.getID()));
        usr2.createWallet();
        Account zoe1 = usr2.createAccount("zoe_account_1");
        usr2.setDefaultAccount(account2);
        usr2.setDefaultAccount(zoe1);
        usr2.getDefaultAccount();
        usr2.transfer(usr1, 1000);
        usr1.deposit(zoe1, 5000);
        usr2.transfer(usr1, 1000);
        Account zoe2 = usr2.createAccount("zoe_account_2", zoe1, 2000);
        usr2.transfer(usr1, 1000);
        usr1.transfer(account2, account3, 200);
        usr1.transfer(account1, account2, 500);

        for(Account account: usr1.getWallet()){
          System.out.println("===============================================");
          System.out.println(String.format("Transfer Records for User %s Account %s",
                      usr1.getName(), account.getName()));
          List<String> record = usr1.getAccountRecords(account);
          for(String string: record){
            System.out.println(string);
          }
          System.out.println("try another users' account");
          record = usr1.getAccountRecords(usr2.getDefaultAccount());
          System.out.println(String.format("End for User %s Account %s",
                      usr1.getName(), account.getName()));
        }

        for(Account account: usr2.getWallet()){
          System.out.println("===============================================");
          System.out.println(String.format("Transfer Records for User %s Account %s",
                      usr2.getName(), account.getName()));
          List<String> record = usr2.getAccountRecords(account);
          for(String string: record){
            System.out.println(string);
          }
          System.out.println("try another users' account");
          record = usr2.getAccountRecords(usr2.getDefaultAccount());
          System.out.println(String.format("End for User %s Account %s",
                      usr2.getName(), account.getName()));
        }
    }
}
