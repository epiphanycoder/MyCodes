package helloworld.helper;

import java.util.HashMap;
import java.util.Map;

public class FileFactory {
    
    private static final Map<String, DataHelper> helperMap;
    static
    {
       // FileHelper fileHelper = new FileHelper();
       // DBHelper dbHelper= new DBHelper();
       // XmlHelper xmlHelper = new XmlHelper();
        
        helperMap = new HashMap<String, DataHelper>();
        helperMap.put("txt", new FileHelper());
        helperMap.put("sql", new DBHelper());
        helperMap.put("xml", new XmlHelper());
    }

    public static DataHelper getInstance(String source) {
      return helperMap.get(source);  
    }
}
