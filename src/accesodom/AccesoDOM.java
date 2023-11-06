/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package accesodom;

import java.io.File;

/**
 *
 * @author David
 */
public class AccesoDOM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DOM a = new DOM();
        File f = new File("books.xml");//necesitamos Libros.xml en la ruta correcta

        a.abriXMLaDOM(f);
        a.recorreDOMyMuestra();
    }
    
}
