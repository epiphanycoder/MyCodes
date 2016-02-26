package helloworld.fileManager;

import helloworld.AnswerGiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileSelector {

    private AnswerGiver answerGiver = null;

    public void loadConfig(String filePath) {
        try {
            
            if(!new File(filePath).exists()) {
                return;
            }
            
            answerGiver = new AnswerGiver();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
            String fileName = reader.readLine();

            while (fileName != null) {
                String extension = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
                answerGiver.processFile(extension, fileName);
                fileName = reader.readLine();
            }
        } catch (Exception e) {
        }
    }
    
    public AnswerGiver getAnswerGiver() {
        return answerGiver;
    }
}
