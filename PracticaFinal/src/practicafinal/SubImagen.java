package practicafinal;

import java.awt.Image;

/**
 *
 * @author Cristobal y Sebas
 */
public class SubImagen {
    //ATRIBUTOS
    private Image imagen;
    
    //MÉTODO CONSTRUCTOR
    public SubImagen(Image imagen) {
        this.imagen = imagen;
    }
    
    //Getters y Setters
    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
    public Image getImagen() {
        return imagen;
    }
}
