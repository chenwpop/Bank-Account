import org.JUnit.runner.JUnitCore;
import org.JUnit.runner.Result;
import org.JUnit.runner.notification.Failure;

public class RunTest{
  public static void main(String[] args){
    Result result = JUnitCore.runClass(UnitTest.class);
    for(Failure failed : result.getFailure()){
      System.out.println(failed.toString());
    }
    System.out.println("Test Result: "+result.wasSuccessful());
  }
}
