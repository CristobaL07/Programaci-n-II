package practicafinal;

import java.io.*;

/**
 *
 * @author Cristobal y Sebas
 */
public class AdicionObjectOutputStream extends ObjectOutputStream { 
    /** Constructor que recibe OutputStream
     * @param out
     * @throws java.io.IOException */
    public AdicionObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    
    /** Constructor
     * @throws java.io.IOException */
    protected AdicionObjectOutputStream() throws IOException, SecurityException {
        super(); //ejecuta el constructor de la clase madre ObjectOutputStream
    }

    /** Redefinición del método de escribir la cabecera para que no haga nada.
     * @throws java.io.IOException */
    @Override
    protected void writeStreamHeader() throws IOException {
    }
}
