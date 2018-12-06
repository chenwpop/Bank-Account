import org.JUnit.After;
import org.JUnit.Before;
import org.JUnit.Test;
import org.Junit.BeforeClass;

import org.JUnit.Assert.*;

public class UnitTest
{
  @Before
  public void prepareBank(){
    System.out.println("Start a test case...");
  }

  @AfterClass
  public void endTest(){
    System.out.println("Unit Test End.");
  }

  @Test
  public void testAccount(){
    User usr1 = User("User1", 1);
    User usr2 = User("User2", 2);
    assertEquals(usr1.getName(), "User1");
    assertEquals(usr2.getName(), "User2");
    Account acc11 = usr1.createAccount("User1 Acc1");
    assertSame(acc11.getUser(), usr1);
    assertNotSame(acc11.getUser(), usr2);
    assertEquals(acc11.getBalance(), 0);
    assertEquals(acc11.getName(), "User1 Acc1");
    acc11.deposit(acc11, 10000);
    assertEquals(acc11.getBalance(), 10000);
    acc11.withdrawl(acc11, 1000);
    assertEquals(usr1.getAccountBalance(acc11), 9000);
    acc11.setDefaultAccount(acc11);
    assertSame(usr1.getDefaultAccount, acc11);
    Account acc12 = usr1.createAccount("User1 Acc2", acc11, 10000);
    assertNull(acc12);
    assertEquals(usr1.getAccountBalance(usr1.getDefaultAccount()), 9000);
    acc12 = usr1.createAccount("User1 Acc2", acc1, 5000);
    assertEquals(acc11.getBalance(), 4000);
    assertEquals(acc12.getBalance(), 5000);
    Account acc21 = usr2.createAccount("User2 Acc1");
    Account acc22 = usr2.createAccount("User2 Acc2");
    usr1.transfer(acc12, acc11, 2000);
    assertEquals(acc11.getBalance(), 6000);
    assertEquals(acc12.getBalance(), 3000);
    usr2.deposit(acc22, 500);
    usr2.setDefaultAccount(acc21);
    usr1.transfer(usr2, 2000);
    usr2.transfer(acc21, acc22, 1000);
    assertEquals(usr2.getAccountBalance(usr2.getDefaultAccount()), 1000);
    assertEquals(acc22.getBalance(), 1500);
  }
}
