import java.util.*;
import java.lang.Exception;
import java.util.concurrent.locks.ReentrantLock;
import java.sql.Timestamp;

public class Account{
  private String name;
  private String id;
  private User holder;
  private List<Transaction> records;
  private ReentrantLock balanceLock;
  private double balance;
  // record the most recent 100 transaction records
  private static final int N = 100;

  // create account without initial balance
  public Account(User holder_, String name_, String id_){
    name = name_;
    holder = holder_;
    id = id_;
    records = new LinkedList<Transaction>();
    balanceLock = new ReentrantLock();
    balance = 0;
  }

  // create account with initial balance
  public Account(User holder_, String name_, String id_, double initBalance){
    name = name_;
    holder = holder_;
    id = id_;
    records = new LinkedList<Transaction>();
    balanceLock = new ReentrantLock();
    balance = initBalance;
  }

  // get unique account id
  public String getID(){
    return id;
  }

  // get account holder
  public User getUser(){
    return holder;
  }

  // get account balance
  public double getBalance(){
    return balance;
  }

  // get account name
  public String getName(){
    return name;
  }

  // get account info
  public String getInfo(){
    return String.format("Name: %s, ID: %s, Holder: %s, Balance: $%.02f.",
            name, id, holder.getName(), balance);
  }

  // set account name
  public void setName(String newName){
    name = newName;
  }

  // create time stamp (in millisecond presicion)
  private String createTimeStamp(){
    return new Timestamp(System.currentTimeMillis()).toString();
  }

  // deposit money to account
  public void deposit(double amount){
    balanceLock.lock();
    try{
      Transaction newTrans = new Transaction(amount, this, createTimeStamp());
      balance += amount;
      records.add(0, newTrans);
    }finally{
      balanceLock.unlock();
    }
  }

  // withdraw money from account
  public void withdrawl(double amount) throws LowBalanceException
  {
    if(balance < amount){
      throw new LowBalanceException(amount - balance);
    }
    balanceLock.lock();
    try{
      long timeStamp = 1;
      Transaction newTrans = new Transaction(-amount, this, createTimeStamp());
      balance -= amount;
      records.add(0, newTrans);
    }finally{
      balanceLock.unlock();
    }
  }

  // get the most recent N transfer records
  public List<Transaction> getRecords(){
    try{
      return records.subList(0, N);
    }catch(IndexOutOfBoundsException e){
      return records;
    }
  }
}
