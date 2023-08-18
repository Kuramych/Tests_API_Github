package helpermanager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class PropertiesHelper {
    private Properties properties;
    private static PropertiesHelper instance;


    public PropertiesHelper() {
        properties = new Properties();

    }

    public static PropertiesHelper getInstance() {
        if (instance == null) {
            instance = new PropertiesHelper();
        }
        return instance;
    }

    public void loadProperties() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
