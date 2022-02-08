package test;

import homework.runner.annatation.OTUSBeforeClass;
import homework.runner.annatation.OTUSBeforeTest;
import homework.runner.annatation.OTUSTest;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;

public class TaskTest {

    int count;

    @OTUSBeforeClass
    void setup(){
       count = 300;
    }

    @OTUSBeforeTest
    void preparationBeforeTest(){
        count = 300>>1;
    }

    @OTUSTest
    void testCheckerZero(){
        count =count/0;
        Assertions.assertEquals(0,count, "should have 0");
    }

    @OTUSTest
    void testCheckerSeven(){
        count = 35/5;
        Assertions.assertEquals(7,count, "should have 0");
    }

}
