package calculator;

public class Summator {
    private int sum;
    private int prevValue;
    private int prevPrevValue;
    private int sumLastThreeValues;
    private int someValue;
    private int count;
    //private final List<Data> listValues = new ArrayList<>();


    public void calc(Data data) {
        count++;
        if (count % 6_600_000 == 0) {
            count = 0;
        }
        sum += data.getValue();

        sumLastThreeValues = data.getValue() + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.getValue();

        int calc = (sumLastThreeValues * sumLastThreeValues / (prevValue + 1 ) - sum);
        for (var idx = 0; idx < 3; idx++) {
            someValue += calc;
            someValue = someValue < 0?
                    -someValue + count:
                    someValue + count;
        }
    }

    public int getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public int getSomeValue() {
        return someValue;
    }
}
