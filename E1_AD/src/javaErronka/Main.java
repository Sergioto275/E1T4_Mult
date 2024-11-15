/*package javaErronka;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        String basePath = System.getProperty("user.dir");
        String configPath = args.length > 0 ? args[0] : "fitxategiak\\konfigurazioa.txt";
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

                        String exportPath = "fitxategiak\\" + tipo + "_export." + formato;

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
}*/


package javaErronka;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        if (args.length < 1) {
            System.err.println("Por favor, proporciona una opción como argumento: [import|export].");
            return;
        }

        String basePath = System.getProperty("user.dir");
        String configPath = "fitxategiak" + File.separator + "konfigurazioa.txt";

        // Leer el archivo de configuración
        ConfigReader config = new ConfigReader(configPath);
        String dbUrl = config.getDbUrl();
        String dbUser = config.getDbUser();
        String dbPassword = config.getDbPassword();
        String xmlFilePath = basePath + File.separator + "fitxategiak" + File.separator + "3PAG2_E1_kanpinak.xml";

        String option = args[0].toLowerCase(); // Primera opción: import o export
        String format = args.length > 1 ? args[1].toLowerCase() : null; // Segundo argumento, si existe
        String dataType = args.length > 2 ? args[2].toLowerCase() : null; // Tercer argumento, si existe

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            System.out.println("Konektatua datu-basearekin!");

            switch (option) {
                case "import":
                    handleImport(connection, xmlFilePath);
                    break;

                case "export":
                    if (format == null || dataType == null) {
                        System.err.println("Para exportar, proporciona formato y tipo de datos: [csv|xml] [herria|probintzia].");
                        break;
                    }
                    handleExport(connection, format, dataType);
                    break;

                default:
                    System.err.println("Opción no válida. Usa 'import' o 'export'.");
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    private static void handleImport(Connection connection, String xmlFilePath) {
        System.out.println("Importando datos desde XML...");
        try {
            XMLImporter importer = new XMLImporter(connection);
            importer.importHerriak(xmlFilePath);
            importer.importProbintziak(xmlFilePath);
            importer.importEtiketak(xmlFilePath);
            importer.importKanpinak(xmlFilePath);
            importer.importKanpinEtiketak(xmlFilePath);
            System.out.println("Importación completada con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al importar datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado durante la importación: " + e.getMessage());
        }
    }

    private static void handleExport(Connection connection, String format, String dataType) {
        String exportPath = "fitxategiak" + File.separator + dataType + "_export." + format;

        try {
            if ("csv".equals(format)) {
                CSVExporter csvExporter = new CSVExporter(connection);
                csvExporter.exportData(exportPath);
            } else if ("xml".equals(format)) {
                CampingXMLGenerator xmlGenerator = new CampingXMLGenerator(connection);
                xmlGenerator.exportToXML(exportPath);
            } else {
                System.err.println("Formato no válido. Por favor, elige 'csv' o 'xml'.");
                return;
            }
            System.out.println("Datos exportados correctamente a: " + exportPath);
        } catch (Exception e) {
            System.err.println("Error al exportar los datos: " + e.getMessage());
        }
    }
}

