package javaErronka;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    
    /**
     * Aplikazioaren sarrera puntu nagusia. Datuak inportatzea eta esportatzea kudeatzen du.
     * @param args Komando lerroko argumentuak: lehenengo argumentua 'import' edo 'export' izan behar da.
     *            'Export' erabiltzen bada, formatu ('csv' edo 'xml') eta datu mota ere eman behar dira.
     * @throws SQLException Datu-basearekin konektatzerakoan errore bat gertatzen bada.
     * @throws IOException Konfigurazio edo XML fitxategiak irakurtzean errore bat gertatzen bada.
     */
    public static void main(String[] args) throws SQLException, IOException {
        // Gutxienez argumentu bat eman behar da: "import" edo "export"
        if (args.length < 1) {
            System.err.println("Mesedez, aukeratu aukera bat argumentu gisa: [import|export].");
            return;
        }

        String basePath = System.getProperty("user.dir");
        String configPath = "fitxategiak" + File.separator + "konfigurazioa.txt";

        // Konfigurazio fitxategia irakurtzen
        ConfigReader config = new ConfigReader(configPath);
        String dbUrl = config.getDbUrl();
        String dbUser = config.getDbUser();
        String dbPassword = config.getDbPassword();
        String xmlFilePath = basePath + File.separator + "fitxategiak" + File.separator + "3PAG2_E1_kanpinak.xml";

        String option = args[0].toLowerCase(); // Lehenengo aukera: 'import' edo 'export'
        String format = args.length > 1 ? args[1].toLowerCase() : null; // Bigarren argumentua, badagoen
        String dataType = args.length > 2 ? args[2].toLowerCase() : null; // Hirugarren argumentua, badagoen

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            System.out.println("Konektatua datu-basearekin!"); // Konektatu da datu-basearekin

            switch (option) {
                case "import":
                    // Datuak XML-tik inportatzen kudeatzen du
                    handleImport(connection, xmlFilePath);
                    break;

                case "export":
                    if (format == null || dataType == null) {
                        System.err.println("Esportatzeko, formatu eta datu mota eman behar dira: [csv|xml] [herria|probintzia].");
                        break;
                    }
                    // Datuak esportatzen kudeatzen du
                    handleExport(connection, format, dataType);
                    break;

                default:
                    System.err.println("Aukera ez baliozkoa. Erabili 'import' edo 'export'.");
            }
        } catch (SQLException e) {
            System.err.println("Konektatzeko errorea: " + e.getMessage());
        }
    }

    /**
     * Datuak XML fitxategitik inportatzeko prozesua kudeatzen du.
     * @param connection Datu-basearekin konektatutako konexioa.
     * @param xmlFilePath XML fitxategiaren bidea, datuak inportatzeko.
     */
    private static void handleImport(Connection connection, String xmlFilePath) {
        System.out.println("Datuak inportatzen XML-tik...");
        try {
            XMLImporter importer = new XMLImporter(connection);
            importer.importHerriak(xmlFilePath); // Herriak inportatzen
            importer.importProbintziak(xmlFilePath); // Probintziak inportatzen
            importer.importEtiketak(xmlFilePath); // Etiketak inportatzen
            importer.importKanpinak(xmlFilePath); // Kanpinak inportatzen
            importer.importKanpinEtiketak(xmlFilePath); // Kanpin etiketa inportatzen
            System.out.println("Inportazioa arrakastaz amaituta.");
        } catch (SQLException e) {
            System.err.println("Errorea datuak inportatzerakoan: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore ezezaguna inportatzerakoan: " + e.getMessage());
        }
    }

    /**
     * Datuak esportatzeko prozesua kudeatzen du, CSV edo XML formatuan.
     * @param connection Datu-basearekin konektatutako konexioa.
     * @param format Esportazio formatuaren tipoa: 'csv' edo 'xml'.
     * @param dataType Esportatu nahi den datu mota: 'herria' edo 'probintzia'.
     */
    private static void handleExport(Connection connection, String format, String dataType) {
        String exportPath = "fitxategiak" + File.separator + dataType + "_export." + format;

        try {
            if ("csv".equals(format)) {
                CSVExporter csvExporter = new CSVExporter(connection);
                csvExporter.exportData(exportPath); // CSV-ra esportatzen
            } else if ("xml".equals(format)) {
                CampingXMLGenerator xmlGenerator = new CampingXMLGenerator(connection);
                xmlGenerator.exportToXML(exportPath); // XML-ra esportatzen
            } else {
                System.err.println("Formato ez baliozkoa. Mesedez, aukeratu 'csv' edo 'xml'.");
                return;
            }
            System.out.println("Datuak arrakastaz esportatu dira: " + exportPath);
        } catch (Exception e) {
            System.err.println("Errorea datuak esportatzerakoan: " + e.getMessage());
        }
    }
}
