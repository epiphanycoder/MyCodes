package helloworld;

import helloworld.helper.DataHelper;
import helloworld.helper.FileFactory;

import java.util.HashMap;


public class AnswerGiver {
    
    private HashMap<String, String> messages = null;
    
    public AnswerGiver() {
        messages = new HashMap<String,String>();
    }
    
    public void processFile(String source, String fileName) {
        DataHelper helper = FileFactory.getInstance(source);
        helper.run(fileName, messages);
    }
    
    public String getMessages(String text) {
        return messages.get(text);
    }
}
