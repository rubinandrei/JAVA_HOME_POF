package test;

import homework.runner.annatation.OTUSAfterClass;
import homework.runner.annatation.OTUSAfterTest;
import homework.runner.annatation.OTUSBeforeClass;
import homework.runner.annatation.OTUSBeforeTest;
import homework.runner.annatation.OTUSTest;
import org.junit.jupiter.api.Assertions;

public class VerifiTest {

    @OTUSBeforeClass
    public void setup(){
        System.out.println("VerifiTest.setup");

    }

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

    @OTUSAfterClass
    void afterClass(){
        System.out.println("VerifiTest.afterClass");
    }

    @OTUSAfterTest
    void afterTest(){
        System.out.println("VerifiTest.afterTest");
    }
}
