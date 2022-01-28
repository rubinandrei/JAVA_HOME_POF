package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = customers.firstEntry();
        return Objects.nonNull(entry)?
                new Entry(entry.getKey(),entry.getValue()):
                null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
            Map.Entry<Customer, String> entry = customers.higherEntry(customer);
            return Objects.nonNull(entry)?
                    new Entry(entry.getKey(),entry.getValue()):
                    null;
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private static final class Entry implements Map.Entry<Customer, String> {

        private final Customer key;
        private String value;

        public Entry(Customer key, String value) {
            this.key = Customer.copyOf(key);
            this.value = value;
        }

        @Override
        public Customer getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(String value) {
            this.value = value;
            return value;
        }
    }
}
