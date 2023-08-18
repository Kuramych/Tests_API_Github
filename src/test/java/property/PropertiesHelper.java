package property;

import java.io.*;
import java.util.Properties;


public class PropertiesHelper {
    private static PropertiesHelper instance = null;

    Properties properties = new Properties();

    PropertiesHelper() {
        try {
            InputStream file = new FileInputStream(new File("src/test/resources/local.properties"));
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesHelper getInstance() {
        if (instance == null) {
            instance = new PropertiesHelper();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
