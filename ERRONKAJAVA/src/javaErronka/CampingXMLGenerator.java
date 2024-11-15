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

public class CampingXMLGenerator {
    private Connection connection;

    public CampingXMLGenerator(Connection connection) {
        this.connection = connection;
    }

    public void exportToXML(String xmlFileName) {
        try {
            // Obtener la ruta base donde está ejecutándose el .jar
            String basePath = System.getProperty("user.dir");
            File exportFolder = new File(basePath, "exportazioak");

            // Asegurarse de que la carpeta "exportazioak" existe
            if (!exportFolder.exists()) {
                exportFolder.mkdir();
            }

            // Ruta final para guardar el archivo
            File finalFile = new File(exportFolder, xmlFileName);

            // Crear documento XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Crear el elemento raíz
            Element rootElement = doc.createElement("kanpinak");
            doc.appendChild(rootElement);

            // Consulta SQL
            String sql = "SELECT K.KODEA, K.IZENA, K.KOKALEKUA, K.HELBIDEA, K.POSTAKODEA, " +
                         "H.IZENA AS HERRIA, P.IZENA AS PROBINTZIA, K.KATEGORIA, K.EDUKIERA " +
                         "FROM KANPINAK K " +
                         "JOIN HERRIAK H ON K.HERRI_KODEA = H.KODEA " +
                         "JOIN PROBINTZIAK P ON K.PROBINTZIA_KODEA = P.KODEA";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Iterar sobre los resultados y crear nodos XML
                while (rs.next()) {
                    Element kanpina = doc.createElement("kanpina");
                    kanpina.setAttribute("id", rs.getString("KODEA"));

                    // Añadir subelementos
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

                // Guardar el documento XML en el archivo
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(finalFile);

                transformer.transform(source, result);

                System.out.println("Archivo XML exportado correctamente a: " + finalFile.getAbsolutePath());

            } catch (Exception e) {
                System.err.println("Error al exportar datos a XML: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error al manejar la carpeta exportazioak: " + e.getMessage());
        }
    }

    private void addElement(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parent.appendChild(element);
    }
}
