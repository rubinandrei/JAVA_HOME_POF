package homework.runner;

import homework.runner.annatation.OTUSAfterClass;
import homework.runner.annatation.OTUSAfterTest;
import homework.runner.annatation.OTUSBeforeClass;
import homework.runner.annatation.OTUSBeforeTest;
import homework.runner.annatation.OTUSTest;
import homework.runner.results.TestResult;
import homework.runner.results.TestResultStatistic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestRunner {

    private static final List<TestResult> RESULTS = new ArrayList<>();

    public static void run(List<Class> data) {
        for (Class clazz : data) {
            Method[] methods = clazz.getDeclaredMethods();
            TestRunner testRunner = new TestRunner();
            List<Method> testMethods = testRunner.getTestMethods(methods);
            if (testMethods.size() > 0) {
                Object object = testRunner.initClass(clazz);
                if (Objects.isNull(object)) {
                    break;
                }
                TestResult result = new TestResult(clazz.getName(), testMethods.size());
                if (!testRunner.invokeByAnnotations(OTUSBeforeClass.class, object)) {
                    result.setCountSkip(result.getCountTests());
                    RESULTS.add(result);
                    continue;
                }
                for (Method method : testMethods) {
                    if (testRunner.invokeByAnnotations(OTUSBeforeTest.class, object)) {
                        int passCount = testRunner.invokeTestMethod(method, object) ? result.getCountPass() + 1 : result.getCountPass();
                        result.setCountPass(passCount);
                        if (!testRunner.invokeByAnnotations(OTUSAfterTest.class, object)) {
                            result.setResultStatus(1);
                        }
                    } else {
                        result.setCountSkip(result.getCountSkip() + 1);
                        result.setResultStatus(-1);
                    }
                }
                if (!testRunner.invokeByAnnotations(OTUSAfterClass.class, object)) {
                    result.setResultStatus(1);
                }
                RESULTS.add(result);
            }

        }

      TestResultStatistic.printStatistic(RESULTS);
    }

    private boolean invokeTestMethod(Method method, Object testInstanceClass) {
        try {
            method.setAccessible(true);
            method.invoke(testInstanceClass);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

    }

    private List<Method> getTestMethods(Method[] methods) {
        return Arrays.stream(methods).filter(m ->
                m.isAnnotationPresent(OTUSTest.class)
                        && !m.isAnnotationPresent(OTUSBeforeTest.class)
                        && !m.isAnnotationPresent(OTUSBeforeClass.class)
                        && !m.isAnnotationPresent(OTUSAfterTest.class)
                        && !m.isAnnotationPresent(OTUSAfterClass.class)
                        && !(m.getParameterCount() > 0)
        ).toList();
    }

    private boolean invokeByAnnotations(Class annotation, Object testInstanceClass) {
        Method[] methods = testInstanceClass.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation) && (method.getParameterCount()) < 1) {
                try {
                    method.setAccessible(true);
                    method.invoke(testInstanceClass);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.getCause().printStackTrace();
                    return false;
                }

            }
        }
        return true;
    }

    private Object initClass(Class clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            return null;
        }
    }
}
