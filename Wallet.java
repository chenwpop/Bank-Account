import java.util.*;

public interface Wallet{

  // create a virtual wallet for current user
  public void createWallet();
  // get current account balance
  public double getAccountBalance(Account account_);
  // transfer between two accounts of same user
  public void transfer(Account from, Account to, double amount);
  // transfer between default accounts of two user
  public void transfer(User anotherUser, double amount);
  // deposit money to account
  public void deposit(Account account, double amount);
  // withdraw money from account
  public void withdrawl(Account account, double amount) throws LowBalanceException;
  // get most recent N records of account
  public List<String> getAccountRecords(Account account_);

  // get user name
  public String getName();
  // get wallet (a set of accounts linked to current user)
  public Set<Account> getWallet();
  // get user ID
  public String getID();
  // create new account through depositing money
  public Account createAccount(String accName);
  // create new account through transfering money from owned account
  public Account createAccount(String accName, Account from, double amount);
  // remove a account with 0 balance
  public void removeAccount(Account account_);
  // set default account in order to transfer between users
  public void setDefaultAccount(Account account_);
  // get default account of current user
  public Account getDefaultAccount();
}
