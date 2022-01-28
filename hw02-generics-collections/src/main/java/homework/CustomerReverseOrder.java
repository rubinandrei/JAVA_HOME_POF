package homework;


import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    Deque<Customer> customerCollection = new ArrayDeque<>();

    public void add(Customer customer) {
        customerCollection.push(customer);
    }

    public Customer take() {
        return customerCollection.poll();
    }
}
