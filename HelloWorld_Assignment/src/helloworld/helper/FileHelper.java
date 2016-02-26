package helloworld.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileHelper extends DataHelper {

    @Override
    public void run(String filePath, HashMap<String, String> messages) {

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = reader.readLine();

            while (line != null) {

                String[] split = line.split("=");
                String key = split[0].toLowerCase();
                String value = split[1];

                if (!messages.containsKey(key)) {
                    messages.put(key, value);
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
        }
    }
}
