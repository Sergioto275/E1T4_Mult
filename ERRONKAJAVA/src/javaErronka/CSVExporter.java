package javaErronka;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVExporter {
    private Connection connection;

    public CSVExporter(Connection connection) {
        this.connection = connection;
    }

    public void exportData(String csvFileName) {
        try {
            // Obtener la ruta base donde está ejecutándose el .jar
            String basePath = System.getProperty("user.dir");
            File exportFolder = new File(basePath, "exportazioak");

            // Asegurarse de que la carpeta "exportazioak" existe
            if (!exportFolder.exists()) {
                exportFolder.mkdir();
            }

            // Ruta final para guardar el archivo
            File finalFile = new File(exportFolder, csvFileName);

            // Consulta SQL
            String sql = "SELECT K.KODEA, K.IZENA, K.KOKALEKUA, K.HELBIDEA, K.POSTAKODEA, " +
                         "H.IZENA AS HERRIA, P.IZENA AS PROBINTZIA, K.KATEGORIA, K.EDUKIERA " +
                         "FROM KANPINAK K " +
                         "JOIN HERRIAK H ON K.HERRI_KODEA = H.KODEA " +
                         "JOIN PROBINTZIAK P ON K.PROBINTZIA_KODEA = P.KODEA";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql);
                 FileWriter fileWriter = new FileWriter(finalFile)) {

                // Escribir cabecera
                fileWriter.append("Kodea,Izena,Kokalekua,Helbidea,PostaKodea,Herria,Probintzia,Kategoria,Edukiera\n");

                // Iterar sobre los resultados
                while (resultSet.next()) {
                    String kodea = resultSet.getString("KODEA");
                    String izena = resultSet.getString("IZENA");
                    String kokalekua = resultSet.getString("KOKALEKUA");
                    String helbidea = resultSet.getString("HELBIDEA").replaceAll(",", ""); // Evitar problemas con comas
                    String postaKodea = resultSet.getString("POSTAKODEA");
                    String herria = resultSet.getString("HERRIA");
                    String probintzia = resultSet.getString("PROBINTZIA");
                    String kategoria = resultSet.getString("KATEGORIA");
                    int edukiera = resultSet.getInt("EDUKIERA");

                    // Escribir la fila en el CSV
                    fileWriter.append(String.join(",",
                            kodea,
                            "\"" + izena + "\"",
                            "\"" + kokalekua + "\"",
                            "\"" + helbidea + "\"",
                            postaKodea.trim(),
                            "\"" + herria + "\"",
                            "\"" + probintzia + "\"",
                            kategoria,
                            String.valueOf(edukiera)));
                    fileWriter.append("\n");
                }

                System.out.println("Archivo CSV exportado correctamente a: " + finalFile.getAbsolutePath());

            } catch (SQLException e) {
                System.err.println("Error en la consulta SQL: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error al manejar la carpeta exportazioak: " + e.getMessage());
        }
    }
}
