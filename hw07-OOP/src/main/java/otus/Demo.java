package otus;

import otus.handler.ComplexProcessor;
import otus.listener.ListenerPrinterConsole;
import otus.model.Message;
import otus.model.ObjectForMessage;
import otus.processor.LoggerProcessor;
import otus.processor.ProcessorConcatFields;
import otus.processor.ProcessorUpperField10;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        var processors = List.of(new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorUpperField10()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        ObjectForMessage field13 = new ObjectForMessage();
        field13.setData(new ArrayList<>(){{
            add("field13");
        }});

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
