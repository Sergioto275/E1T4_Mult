package javaErronka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String xmlFilePath;

    public ConfigReader(String configPath) throws IOException {
        File configFile = new File(configPath);
        System.out.println("Buscando konfigurazioa.txt en: " + configFile.getAbsolutePath());

        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            properties.load(reader);
            dbUrl = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
            xmlFilePath = properties.getProperty("xml.file.path");
        }
    }


    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }
}
