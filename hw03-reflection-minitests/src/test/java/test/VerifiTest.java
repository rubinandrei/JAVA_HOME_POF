package test;

import homework.runner.annatation.OTUSAfterTest;
import homework.runner.annatation.OTUSBeforeTest;
import homework.runner.annatation.OTUSTest;
import org.junit.jupiter.api.Assertions;

public class VerifiTest {


    @OTUSBeforeTest
    private void beforeTest(){
        System.out.println("VerifiTest.beforeTest");
    }

    @OTUSTest
    void firstTest() {
        System.out.println("42");
    }

    @OTUSTest
    void secondTest(){
        System.out.println("24");
        Assertions.assertTrue(true);
    }



    @OTUSAfterTest
    void afterTest(){
        System.out.println("VerifiTest.afterTest");
    }
}
