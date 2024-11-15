package javaErronka;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        String basePath = System.getProperty("user.dir");
        String configPath = args.length > 0 ? args[0] : "C:\\Users\\Ikaslea\\Desktop\\ERRONKAJAVA\\fitxategiak\\konfigurazioa.txt";
        // Leer el archivo de configuración
        ConfigReader config = new ConfigReader(configPath);
        String dbUrl = config.getDbUrl();
        String dbUser = config.getDbUser();
        String dbPassword = config.getDbPassword();
        String xmlFilePath = args.length > 0 
                ? new File(args[0]).getAbsolutePath() // Si viene como argumento
                : new File(basePath + "/fitxategiak/3PAG2_E1_kanpinak.xml").getAbsolutePath(); // Por defecto

        Connection connection=null; // Conexión a la base de datos
        Scanner scanner = new Scanner(System.in); // Para leer datos del usuario
        int choice = 0; // Opción del usuario en el menú

        try {
            // Registrar el driver de Oracle y conectar a la base de datos
        	// Datu-basearekin konektatu
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("Konektatua datu-basearekin!");

            while (choice != 3) { // Leer la opción del menú
                // Mostrar el menú
                System.out.println("**************************************");
                System.out.println("1) Importar datos desde XML");
                System.out.println("2) Exportar datos (CSV o XML)");
                System.out.println("3) Salir");
                System.out.print("Elige una opción: ");
                choice = scanner.nextInt();
                System.out.println("**************************************");

                // Procesar la opción elegida
                switch (choice) {
                    case 1:
                        // Importar datos desde XML
                        System.out.println("Importando datos...");
                        try {
                            XMLImporter importer = new XMLImporter(connection);
                            importer.importHerriak(xmlFilePath);
                            importer.importProbintziak(xmlFilePath);
                            importer.importEtiketak(xmlFilePath);
                            importer.importKanpinak(xmlFilePath);
                            importer.importKanpinEtiketak(xmlFilePath);
                        } catch (SQLException e) {
                            System.err.println("Error al importar datos: " + e.getMessage());
                        } catch (Exception e) {
                            System.err.println("Error inesperado durante la importación: " + e.getMessage());
                        }
                        break;

                    case 2:
                        // Exportar datos (CSV o XML)
                        System.out.println("¿En qué formato deseas exportar los datos? (csv/xml): ");
                        String formato = scanner.next().toLowerCase();

                        System.out.println("¿Qué tipo de datos deseas exportar? (herria/probintzia): ");
                        String tipo = scanner.next().toLowerCase();

                        String exportPath = "..\\ERRONKAJAVA\\fitxategiak\\" + tipo + "_export." + formato;

                        try {
                            if ("csv".equals(formato)) {
                                CSVExporter csvExporter = new CSVExporter(connection);
                                csvExporter.exportData(exportPath);
                            } else if ("xml".equals(formato)) {
                                CampingXMLGenerator xmlGenerator = new CampingXMLGenerator(connection);
                                xmlGenerator.exportToXML(exportPath);
                            } else {
                                System.out.println("Formato no válido. Por favor, elige 'csv' o 'xml'.");
                            }
                        } catch (Exception e) {
                            System.err.println("Error al exportar los datos: " + e.getMessage());
                        }
                        break;

                    case 3:
                        // Salir
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opción no válida, por favor intenta de nuevo.");
                }
            }
        } finally {
            // Cerrar la conexión a la base de datos
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexión cerrada con éxito.");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
            scanner.close(); // Cerrar el scanner
        }
    }
}
