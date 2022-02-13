package homework.runner.results;

import homework.runner.results.TestResult;

import java.util.List;

public class TestResultStatistic {

    public static void printStatistic(List<TestResult> testResultList){
        System.out.println("=========================== resuls ==========================");
        int totalTests =0;
        int passed = 0;
        int skipped = 0;
        int failed =0;

        for(TestResult result: testResultList){
            totalTests += result.getCountTests();
            passed += result.getCountPass();
            skipped += result.getCountSkip();
            failed += result.getCountTests() - result.getCountPass() - result.getCountSkip();

            System.out.printf(" ==== Test class: %s ==== total: %d == passed: %d == failed: %d == skipped: %d ====%n",
                    result.getTestClassName(),
                    result.getCountTests(),
                    result.getCountPass(),
                    result.getCountTests() - result.getCountPass() - result.getCountSkip(),
                    result.getCountSkip());
        }


        System.out.printf(" ==== total: %d == passed: %d == failed: %d == skipped: %d ====%n",totalTests,passed,failed,skipped);


    }
}
