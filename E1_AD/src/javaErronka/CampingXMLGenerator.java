package javaErronka;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Kanpinak XML fitxategia sortzeko klasea.
 * Klase honek datu-basearen datuak hartzen ditu eta XML fitxategian esportatzen ditu.
 */
public class CampingXMLGenerator {
    private Connection connection;

    /**
     * CampingXMLGenerator klasearen konstruktorea.
     * 
     * @param connection Datu-basearekiko konektibitatea.
     */
    public CampingXMLGenerator(Connection connection) {
        this.connection = connection;
    }

    /**
     * Kanpinak datuak XML fitxategian esportatzen ditu.
     * 
     * @param xmlFileName Sortu nahi den XML fitxategiaren izena.
     */
    public void exportToXML(String xmlFileName) {
        try {
            // Fitxategia non gordeko den adierazten duen bidea sortzen da
            File finalFile = new File(xmlFileName);

            // XML dokumentua sortzeko prestaketa
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Root elementua sortu
            Element rootElement = doc.createElement("kanpinak");
            doc.appendChild(rootElement);

            // SQL kontsulta
            String sql = "SELECT K.KODEA, K.IZENA, K.KOKALEKUA, K.HELBIDEA, K.POSTAKODEA, " +
                         "H.IZENA AS HERRIA, P.IZENA AS PROBINTZIA, K.KATEGORIA, K.EDUKIERA " +
                         "FROM KANPINAK K " +
                         "JOIN HERRIAK H ON K.HERRI_KODEA = H.KODEA " +
                         "JOIN PROBINTZIAK P ON K.PROBINTZIA_KODEA = P.KODEA";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Emaitzak lortu eta XML nodoak sortu
                while (rs.next()) {
                    Element kanpina = doc.createElement("kanpina");
                    kanpina.setAttribute("id", rs.getString("KODEA"));

                    // Subelementuak gehitu
                    addElement(doc, kanpina, "izena", rs.getString("IZENA"));
                    addElement(doc, kanpina, "kokalekua", rs.getString("KOKALEKUA"));
                    addElement(doc, kanpina, "helbidea", rs.getString("HELBIDEA"));
                    addElement(doc, kanpina, "postaKodea", rs.getString("POSTAKODEA"));
                    addElement(doc, kanpina, "herria", rs.getString("HERRIA"));
                    addElement(doc, kanpina, "probintzia", rs.getString("PROBINTZIA"));
                    addElement(doc, kanpina, "kategoria", rs.getString("KATEGORIA"));
                    addElement(doc, kanpina, "edukiera", String.valueOf(rs.getInt("EDUKIERA")));

                    rootElement.appendChild(kanpina);
                }

                // XML dokumentua fitxategian gorde
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(finalFile);

                transformer.transform(source, result);

                System.out.println("XML fitxategia ondo esportatu da: " + finalFile.getAbsolutePath());

            } catch (Exception e) {
                System.err.println("Errore bat egon da datuak XML-ra esportatzerakoan: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Errore bat egon da 'exportazioak' karpetarekin lan egiten: " + e.getMessage());
        }
    }

    /**
     * Subelementu bat gehitzen du XML elementuari.
     * 
     * @param doc      XML dokumentuaren instanzia.
     * @param parent   Guraso elementua.
     * @param tagName  Subelementuaren izena.
     * @param textContent Subelementuaren edukia.
     */
    private void addElement(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parent.appendChild(element);
    }
}
