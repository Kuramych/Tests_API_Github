package appmanager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class PropertiesHelper {
    private Properties properties;

    public PropertiesHelper() {
        properties = new Properties();

    }
    public void loadProperties() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
