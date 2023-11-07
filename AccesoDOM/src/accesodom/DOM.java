/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesodom;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author David
 */
public class DOM {

    Document doc;

    // Metodo para abrir el DOM
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

    // Metodo que recorre el DOM
    public void recorreDOMyMuestra() {
        int numeroLibro = 0;
        String[] datosLibro = new String[10];
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes();
        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
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
                System.out.println("Libro " + numeroLibro + " con id: " + datosLibro[0] + "\n\tAutor: " + datosLibro[1] + "\n\tTítulo: " + datosLibro[2]
                        + "\n\tGénero: " + datosLibro[3] + "\n\tPrecio: " + datosLibro[4] + "\n\tFecha de publicación: " + datosLibro[5]
                        + "\n\tDescripción: " + datosLibro[6] + "\n");
            }
        }
    }

    // Metodo para insertar
    public int insertLibroEnDOM(String autor, String titulo, String genero, Double precio, String fechap, String descripcion) {
        try {
            System.out.println("Añadir libro al árbol DOM: " + titulo + ";" + autor + ";" + genero + ";" + precio + ";" + fechap + ";"
                    + descripcion + ";");
            //Crea los nodos -> los añade al padre desde las hojas a la raíz
            
            //CREAMOS AUTOR
            Node nAutor = doc.createElement("Autor"); //Crea etiquetas <Autor>...</Autor>
            Node nAutor_text = doc.createTextNode(autor); //Crea el nodo texto para el Autor
            nAutor.appendChild(nAutor_text); //Añade el Autor a las etiquetas <Autor>autor</Autor> 
            
            //CREAMOS tITULO
            Node nTitulo = doc.createElement("Titulo");
            Node nTitulo_text = doc.createTextNode(titulo);
            nTitulo.appendChild(nTitulo_text);            

            //CREAMOS GENERO
            Node nGenero = doc.createElement("Genero");
            Node nGenero_text = doc.createTextNode(genero);
            nGenero.appendChild(nGenero_text);

            //CREAMOS PRECIO
            Node nPrecio = doc.createElement("Precio");
            Node nPrecio_text = doc.createTextNode(Double.toString(precio)); //convierto el precio Double a String
            nPrecio.appendChild(nPrecio_text);

            //CREAMOS DESCRIPCION
            Node nDescripcion = doc.createElement("Descripcion");
            Node nDescripcion_text = doc.createTextNode(descripcion);
            nDescripcion.appendChild(nDescripcion_text);

            //CREA LIBRO con atributo y nodos AUTOR, TITULO, GENERO, PRECIO, DESCRIPCION
            Node nLibro = doc.createElement("Libro");
            ((Element) nLibro).setAttribute("publicado", fechap);
            nLibro.appendChild(nAutor);
            nLibro.appendChild(nTitulo);
            nLibro.appendChild(nGenero);
            nLibro.appendChild(nPrecio);
            nLibro.appendChild(nDescripcion);

            nLibro.appendChild(doc.createTextNode("\n")); //Para insertar saltos de línea

            Node raiz = doc.getFirstChild(); //tb.doc.getChildNodes().item(0)
            raiz.appendChild(nLibro);
            System.out.println("Libro insertado en el DOM correctamente");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    // Metodo para eliminar
    public int borrarNodo(String tituloBorrar) {
        System.out.println("Buscando el libro " + tituloBorrar + " para eliminar");
        try {
            Node raiz = doc.getDocumentElement();
            NodeList nl1 = doc.getElementsByTagName("title");
            Node n1 = null;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);

                if (n1.getNodeType() == Node.ELEMENT_NODE) {
                    if (n1.getChildNodes().item(0).getNodeValue().equals(tituloBorrar)) {
                        System.out.println("Borrando el libro: " + tituloBorrar);
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());
                    }
                }
            }
            return 0;
        } catch (Exception IOException) {
            System.out.println(IOException);
            IOException.printStackTrace();
            return -1;
        }
    }

    // Metodo guardar en un archivo nuvo
    void guardarDOMcomoArchivo(String nuevoArchivo) {
        try {
            Source src = new DOMSource(doc);
            StreamResult rst = new StreamResult(new File(nuevoArchivo));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(src, (javax.xml.transform.Result) rst);
            System.out.println("Archivo creado del DOM con éxito\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
