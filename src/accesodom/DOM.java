/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesodom;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author David
 */
public class DOM {
    Document doc;
    
    public int abriXMLaDOM(File f) {
        try {
            System.out.println("Abriendo el archivo XML file y generando el DOM");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
            
            System.out.println("DOM creado con éxito.\n");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
    
    public void recorreDOMyMuestra(){
        int numeroLibro = 0;
        String[] datosLibro = new String[10];
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes();
        for (int i = 0; i < nodelist.getLength(); i++){
            nodo = nodelist.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE){
                Node ntemp = null;
                int contador = 1;
                datosLibro[0] = nodo.getAttributes().item(0).getNodeValue();
                NodeList nl2 = nodo.getChildNodes();
                for (int j = 0; j < nl2.getLength(); j++) {
                    ntemp = nl2.item(j);
                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                        datosLibro[contador] = ntemp.getTextContent();
                        contador++;
                    }
                }
                numeroLibro++;
                System.out.println("Libro " + numeroLibro + " con id: " + datosLibro[0] + "\n\tAutor: " + datosLibro[1] + "\n\tTítulo: " + datosLibro[2] +
                        "\n\tGénero: " + datosLibro[3] + "\n\tPrecio: " + datosLibro[4] + "\n\tFecha de publicación: " + datosLibro[5] +
                        "\n\tDescripción: " + datosLibro[6] + "\n");
            }
        }
    }
}