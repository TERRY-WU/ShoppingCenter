package com.qa.shopping.opencart.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class ConfigProvider {

    private static Config config;

    public static Config config(String folderName, String fileName) {
        if (config == null) {
            config = ConfigFactory.load(ConfigFactory.parseFile(new File("./src/test/resources/" + folderName + "/" + fileName)));
            String env = config.getString("env");
            if (!env.equals("")) {
                config = config.getConfig(env.toLowerCase()).withFallback(config);
            }
        }
        return config;
    }
}
