package homework.runner;

import homework.runner.annatation.OTUSAfterTest;
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
                List<Method> beforeMethods = testRunner.getBeforeMethods(methods);
                List<Method> afterMethods = testRunner.getAfterMethods(methods);
                TestResult result = new TestResult(clazz.getName(), testMethods.size());
                for (Method method : testMethods) {
                    boolean skipTests = false;
                    Object object = testRunner.initClass(clazz);
                    if (Objects.isNull(object)) {
                        result.setCountSkip(result.getCountSkip() + 1);
                        continue;
                    }
                    if (!beforeMethods.isEmpty()) {
                        if (testRunner.invokeMethodsHasError(beforeMethods, object)) {
                            result.setCountSkip(result.getCountSkip() + 1);
                            result.setResultStatus(-1);
                            skipTests = true;
                        }
                    }
                    if (!skipTests) {
                        int passCount = testRunner.invokeMethod(method, object) ? result.getCountPass() + 1 : result.getCountPass();
                        result.setCountPass(passCount);
                    }
                    if (!afterMethods.isEmpty()) {
                        if (testRunner.invokeMethodsHasError(afterMethods, object)) {
                            result.setResultStatus(1);
                        }
                    }
                }
                RESULTS.add(result);
            }

        }

        TestResultStatistic.printStatistic(RESULTS);
    }

    private boolean invokeMethod(Method method, Object testInstanceClass) {
        try {
            method.setAccessible(true);
            method.invoke(testInstanceClass);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.getCause().printStackTrace();
            return false;
        }

    }

    private boolean invokeMethodsHasError(List<Method> methods, Object testInstanceClass) {
        for (Method method : methods) {
            try {
                method.setAccessible(true);
                method.invoke(testInstanceClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.getCause().printStackTrace();
                return true;
            }
        }

        return false;


    }

    private List<Method> getTestMethods(Method[] methods) {
        return Arrays.stream(methods).filter(m ->
                m.isAnnotationPresent(OTUSTest.class)
                        && !m.isAnnotationPresent(OTUSBeforeTest.class)
                        && !m.isAnnotationPresent(OTUSAfterTest.class)
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

    private List<Method> getBeforeMethods(Method[] methods) {
        return Arrays.stream(methods).filter(m ->
                m.isAnnotationPresent(OTUSBeforeTest.class)
                        && !m.isAnnotationPresent(OTUSTest.class)
                        && !m.isAnnotationPresent(OTUSAfterTest.class)
                        && !(m.getParameterCount() > 0)
        ).toList();
    }

    private List<Method> getAfterMethods(Method[] methods) {
        return Arrays.stream(methods).filter(m ->
                m.isAnnotationPresent(OTUSAfterTest.class)
                        && !m.isAnnotationPresent(OTUSTest.class)
                        && !m.isAnnotationPresent(OTUSBeforeTest.class)
                        && !(m.getParameterCount() > 0)
        ).toList();
    }

    private Object initClass(Class clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
