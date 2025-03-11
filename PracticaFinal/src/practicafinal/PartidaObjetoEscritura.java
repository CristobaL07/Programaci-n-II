package practicafinal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Cristobal y Sebas
 */
import java.io.*;

public class PartidaObjetoEscritura {
    //ATRIBUTOS
    private ObjectOutputStream fichero1 = null;
    private AdicionObjectOutputStream fichero2 = null;

    //MÉTODO CONSTRUCTOR
    public PartidaObjetoEscritura(String nombreFichero) {
        try {
            //declaración objeto File que enlace con el fichero dado por parámetro
            File F = new File(nombreFichero);
            if (F.exists()) {
                //instanciación objeto AdicionObjectOutputStream con el fichero dado
                //para posibilitar la adición de objetos  en dicho fichero
                fichero2 = new AdicionObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nombreFichero, true)));
            } else {
                //instanciación objeto ObjectOutputStream con el fichero dado
                //para posibilitar la escritura de objetos en dicho fichero que será creado
                //como nuevo
                fichero1 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nombreFichero)));
            }
        } catch (IOException error) {
            System.out.println("ERROR: " + error.toString());
        }
    }

    //MÉTODO escritura lleva a cabo la escritura de un objeto Partida
    public void escribirPartida(Partida partida) {
        try {
            //verificar si el fichero es de nueva creación o es un fichero, ya existente, donde
            //se está añadiendo un nuevo objeto Partida
            if (fichero2 == null) {
                //escritura objeto Partida dado
                fichero1.writeObject(partida);
            } else {
                //escritura objeto Partida dado
                fichero2.writeObject(partida);
            }
        } catch (IOException error) {
            System.out.println("ERROR: " + error.toString());
        }

    }

    //MÉTODO cierre LLEVA A CABO EL CIERRE DEL ENLACE CON EL FICHERO
    public void cerrarFichero() {
        try {
            //verificar si el fichero es de nueva creación o es un fichero, ya existente, donde
            //se está añadiendo un nuevo objeto Palabra
            if (fichero2 == null) {
                fichero1.close();
            } else {
                fichero2.close();
            }
        } catch (IOException error) {
            System.out.println("ERROR: " + error.toString());
        }
    }
}
