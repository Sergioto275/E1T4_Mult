package javaErronka;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CSV fitxategiak esportatzeko klasea.
 * Klase honek SQL kontsultak exekutatzen ditu eta emaitzak CSV fitxategi batean gordetzen ditu.
 */
public class CSVExporter {
    private Connection connection;

    /**
     * CSVExporter klasearen konstruktorea.
     * SQL konexioa jaso eta gordetzen du.
     * 
     * @param connection Datubasearekin egindako konexioa.
     */
    public CSVExporter(Connection connection) {
        this.connection = connection;
    }

    /**
     * Datuak CSV fitxategi batean esportatzeko metodoa.
     * SQL kontsulta bat exekutatzen du eta emaitzak CSV fitxategian idazten ditu.
     * 
     * @param csvFileName CSV fitxategiaren izena eta bidea.
     */
    public void exportData(String csvFileName) {
        try {
            // Ruta final para guardar el archivo
            File finalFile = new File(csvFileName);

            // SQL kontsulta
            String sql = "SELECT K.KODEA, K.IZENA, K.KOKALEKUA, K.HELBIDEA, K.POSTAKODEA, " +
                         "H.IZENA AS HERRIA, P.IZENA AS PROBINTZIA, K.KATEGORIA, K.EDUKIERA " +
                         "FROM KANPINAK K " +
                         "JOIN HERRIAK H ON K.HERRI_KODEA = H.KODEA " +
                         "JOIN PROBINTZIAK P ON K.PROBINTZIA_KODEA = P.KODEA";

            // SQL kontsulta exekutatu eta CSV fitxategian idatzi
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql);
                 FileWriter fileWriter = new FileWriter(finalFile)) {

                // Idatzi goiburua
                fileWriter.append("Kodea,Izena,Kokalekua,Helbidea,PostaKodea,Herria,Probintzia,Kategoria,Edukiera\n");

                // Emaitzak iteratu eta CSV-ra idatzi
                while (resultSet.next()) {
                    String kodea = resultSet.getString("KODEA");
                    String izena = resultSet.getString("IZENA");
                    String kokalekua = resultSet.getString("KOKALEKUA");
                    String helbidea = resultSet.getString("HELBIDEA").replaceAll(",", ""); // Komak saihesteko
                    String postaKodea = resultSet.getString("POSTAKODEA");
                    String herria = resultSet.getString("HERRIA");
                    String probintzia = resultSet.getString("PROBINTZIA");
                    String kategoria = resultSet.getString("KATEGORIA");
                    int edukiera = resultSet.getInt("EDUKIERA");

                    // Idatzi CSV fitxategian
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

                System.out.println("CSV fitxategia esportatu da ondo: " + finalFile.getAbsolutePath());

            } catch (SQLException e) {
                System.err.println("SQL kontsulta errorea: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("CSV fitxategia idazteko errorea: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Errorea exportazioak karpeta kudeatzerakoan: " + e.getMessage());
        }
    }
}
