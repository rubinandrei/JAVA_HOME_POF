package otus.listener;

import otus.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    static Map<Long, List<Message>> history= new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        Message messageBoxes = msg.clone();
        if(history.containsKey(messageBoxes.getId())){
            history.get(messageBoxes.getId()).add(messageBoxes);
        }else{
            var msArr = new ArrayList<Message>();
            msArr.add(messageBoxes);
            history.put(messageBoxes.getId(),msArr);
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if(history.containsKey(id)){
            return history.get(id).stream().findAny();
        }else {
            throw new UnsupportedOperationException();
        }
    }
}
