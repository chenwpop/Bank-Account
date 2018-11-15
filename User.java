import java.util.*;
import java.util.stream.*;
import java.util.concurrent.atomic.AtomicLong;
import java.lang.Exception;

public class User implements Wallet{
  private String name;
  private int id;
  private Account defAccount;
  private Set<Account> wallet;
  private static AtomicLong count = new AtomicLong();

  public User(String name_, int id_){
    name = name_;
    id = id_;
    createWallet();
    defAccount = null;
  }

  // get user name
  public String getName(){
    return name;
  }

  // get virtual wallet
  public Set<Account> getWallet(){
    return wallet;
  }

  // get user ID
  public String getID(){
    return String.valueOf(id);
  }

  // create a virtual wallet for current user
  public void createWallet(){
    wallet = new HashSet<Account>();
  }

  // create a new account through depositing money
  public Account createAccount(String accName){
    Account account = new Account(this, accName, createAccountID());
    wallet.add(account);
    return account;
  }

  // create a new account through transfering money from owned account
  public Account createAccount(String accName, Account from, double amount){
    if(from == null){
      System.out.println("Empty Source Account!");
      return null;
    }else if(!wallet.contains(from)){
      System.out.println(String.format("Access to Account %s Denied!",
              from.getName()));
    }
    boolean flag = false;
    try{
      from.withdrawl(amount);
    }catch(LowBalanceException e){
      System.out.println(String.format("Need More Funds: $%.02f",
                          e.getAmount()));
      flag = true;
    }
    if(!flag){
      Account account = new Account(this, accName, createAccountID(), amount);
      wallet.add(account);
      account.deposit(amount);
      return account;
    }else{
      return null;
    }
  }

  // create unique account ID
  private static synchronized String createAccountID(){
    return String.valueOf(count.getAndIncrement());
  }

  // deposit money to account
  public void deposit(Account account_, double amount_){
    account_.deposit(amount_);
  }

  // withdraw money from account
  public void withdrawl(Account account_, double amount_) throws LowBalanceException
  {
    account_.withdrawl(amount_);
  }

  // with a account with 0 balance
  public void removeAccount(Account account_)
  {
    if(account_ == null){
      System.out.println("Empty Account Removed!");
      return;
    }else if(!wallet.contains(account_)){
      System.out.println(String.format("Access to Account %s Denied!",
                  account_.getName()));
      return;
    }else if(account_.getBalance()!=0){
      System.out.println(
          String.format("Can't Remove Account %s with Positive Balance!",
          account_.getName()));
    }else{
      wallet.remove(account_);
      if(account_ == defAccount){
        System.out.println(String.format("(Default) Account %s Removed.",
                defAccount.getName()));
        defAccount = null;
        return;
      }else{
        System.out.println(String.format("Account %s Removed.",
                account_.getName()));
      }
    }
  }

  // set default account in order to transfer money between users
  public void setDefaultAccount(Account account_)
  {
    if(account_ == null){
      System.out.println("Can Not Set Default Account as Empty!");
      return;
    }else if(wallet.contains(account_)){
      defAccount = account_;
    }else{
      System.out.println(String.format("Access to Account %s Denied!",
                  account_.getName()));
    }
  }

  // get default account of current user
  public Account getDefaultAccount()
  {
    if(defAccount == null){
      System.out.println("Please Set Default Account First!");
      return null;
    }else{
      return defAccount;
    }
  }

  // get current account balance
  public double getAccountBalance(Account account_){
    if(account_ == null){
      System.out.println("Attention: Empty Account!");
      return 0;
    }else if(wallet.contains(account_)){
      return account_.getBalance();
    }else{
      System.out.println(String.format("Access to Account %s Denied!",
                  account_.getName()));
      return -1;
    }
  }

  // transfer between two accounts of same user
  public void transfer(Account from, Account to, double amount)
  {
    if(from == null || to == null){
      System.out.println("Illegal Transfer: Empty Account! ");
      return;
    }else if(!wallet.contains(from)){
      System.out.println(String.format("Access to Account %s Denied!",
                          from.getName()));
      return;
    }else if(!wallet.contains(to)){
      System.out.println(String.format("Access to Account %s Denied!",
                          to.getName()));
    }else{
      try{
        from.withdrawl(amount);
      }catch(LowBalanceException e){
        System.out.println(String.format("Need More Funds: $%.02f",
                            e.getAmount()));
        return;
      }
      to.deposit(amount);
    }
  }

  // transfer between default accounts of two user
  public void transfer(User anotherUser, double amount)
  {
    if(anotherUser == null){
      System.out.println("Attention: Empty User!");
      return;
    }
    Account from = defAccount;
    Account to = anotherUser.getDefaultAccount();
    if(from == null || to == null){
      System.out.println("Please Set Default Account for Users!");
      return;
    }
    try{
      from.withdrawl(amount);
    }catch(LowBalanceException e){
      System.out.println(String.format("Need More Funds: $%.02f",
                          e.getAmount()));
      return;
    }
    to.deposit(amount);
  }

  // get the most recent N records of account
  public List<String> getAccountRecords(Account account_){
    if(wallet.contains(account_)){
      List<String> result = account_.getRecords().stream().map(
                    x -> x.getSummary()).collect(Collectors.toList());
      return result;
    }else{
      System.out.println(String.format("Access to Account %s Denied!",
                          account_.getName()));
      return null;
    }
  }
}
