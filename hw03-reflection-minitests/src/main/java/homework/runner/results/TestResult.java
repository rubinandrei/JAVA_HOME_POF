package homework.runner.results;

public class TestResult {
    String testClassName;
    int countTests;
    int countPass;
    int getCountFailed;
    int countSkip;
    int resultStatus;

    public TestResult(String testClassName, int countTests) {
        this.testClassName = testClassName;
        this.countTests = countTests;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public int getCountTests() {
        return countTests;
    }

    public void setCountTests(int countTests) {
        this.countTests = countTests;
    }

    public int getCountPass() {
        return countPass;
    }

    public void setCountPass(int countPass) {
        this.countPass = countPass;
    }

    public int getGetCountFailed() {
        return getCountFailed;
    }

    public void setGetCountFailed(int getCountFailed) {
        this.getCountFailed = getCountFailed;
    }

    public int getCountSkip() {
        return countSkip;
    }

    public void setCountSkip(int countSkip) {
        this.countSkip = countSkip;
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestResult)) return false;

        TestResult result = (TestResult) o;

        return testClassName.equals(result.testClassName);
    }

    @Override
    public int hashCode() {
        return testClassName.hashCode();
    }
}
