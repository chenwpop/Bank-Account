public class LowBalanceException extends Exception{
  private double amount;

  public LowBalanceException(double amount_){
    amount = amount_;
  }
  // get the balance difference
  public double getAmount(){
    return amount;
  }
}
