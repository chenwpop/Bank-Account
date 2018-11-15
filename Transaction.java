import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class Transaction{
  private double amount;
  private String timestamp;
  private String uuid;
  private Account fromAccount;

  public Transaction(double amount_, Account from_, String timestamp_){
    amount = amount_;
    fromAccount = from_;
    uuid = UUID.randomUUID().toString();
    timestamp = timestamp_;
  }

  // get the transaction amount
  public double getAmount(){
    return amount;
  }

  // get the transaction summary
  public String getSummary(){
    if(amount >= 0){
      return String.format("Time: %s, UUID: %s, Amount: $%.02f.",
              timestamp, uuid, amount);
    }else{
      return String.format("Time: %s, UUID: %s, Amount: $(%.02f).",
              timestamp, uuid, amount);
    }
  }
}
