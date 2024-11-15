package javaErronka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Konfigurazio fitxategiak irakurtzeko klasea.
 * Klase honek konfigurazio fitxategiak (adibidez, db.url, db.user, db.password eta xml.file.path) irakurtzen ditu.
 */
public class ConfigReader {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String xmlFilePath;

    /**
     * ConfigReader klasearen konstruktorea.
     * Konfigurazio fitxategiaren bidea ematen du eta fitxategia kargatzen du.
     * 
     * @param configPath Konfigurazio fitxategiaren bidea.
     * @throws IOException Fitxategia irakurtzeko errore bat gertatzen bada.
     */
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

    /**
     * Datubasearen URL-a lortzeko metodoa.
     * 
     * @return Datubasearen URL-a.
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * Datubasearen erabiltzaile izena lortzeko metodoa.
     * 
     * @return Datubasearen erabiltzaile izena.
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * Datubasearen pasahitza lortzeko metodoa.
     * 
     * @return Datubasearen pasahitza.
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * XML fitxategiaren bidea lortzeko metodoa.
     * 
     * @return XML fitxategiaren bidea.
     */
    public String getXmlFilePath() {
        return xmlFilePath;
    }
}
