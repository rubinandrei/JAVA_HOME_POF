package otus.processor;

import otus.model.Message;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class ProcessorTimeError implements Processor{

    public int getCurrentSec(){
        return LocalDateTime.now().getSecond();
    }
    @Override
    public Message process(Message message) {
        if(getCurrentSec() % 2 == 0){
            throw new DateTimeException("even second");
        }
        return message;
    }
}
