package practicafinal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Cristobal y Sebas
 */
public class PartidaObjetoLectura {
    //ATRIBUTOS
    private FileInputStream fis = null;
    private ObjectInputStream ois = null;

    //MÉTODO CONSTRUCTOR
    public PartidaObjetoLectura(String nombrefichero) throws IOException {
        fis = new FileInputStream(nombrefichero);
        ois = new ObjectInputStream(fis);
    }

    //Metodo que lleva a cabo el cierre del fichero
    public void cerrarFichero() throws IOException {
        ois.close();
    }

    //Metodo que lee el objeto partida y lo devuelve
    public Partida leerPartida() throws IOException, ClassNotFoundException {
        return (Partida) ois.readObject();

    }
}
