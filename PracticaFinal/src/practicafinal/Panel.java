package practicafinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Cristobal y Sebas
 */
public class Panel extends JPanel {
    //DECLARACIÓN DE ATRIBUTOS
    private int dimX;
    private int dimY;
    private Image imagen;
    
    //MÉTODO CONSTRUCTOR
    public Panel(SubImagen image, int columna, int fila) {
        dimX = 1095 / columna;
        dimY = 767 / fila;
        imagen = image.getImagen();
        redimensionarImagen();
        setSize(dimX, dimY); 
        setBackground(Color.red);
        setBorder(BorderFactory.createLineBorder(Color.black,1));    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Utilizamos Graphics2D para la visualización
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(imagen,0,0,this);
    }    
    
    //lleva a cabo la actualización del cuadrado asignándole un
    //borde de color RED
    public void seleccionarPanel() {
        setBorder(BorderFactory.createLineBorder(Color.red,5));     
    } 

    //lleva a cabo la actualización del cuadrado quitandole el 
    //borde de color RED
    public void deseleccionarPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black,1));     
    } 

    //Escala la imagen en las dimensiones seleccionadas
    public void redimensionarImagen() {
        imagen = imagen.getScaledInstance(dimX,
            dimY, java.awt.Image.SCALE_DEFAULT);
    }
    
    //Cambia la imagen del objeto
    public void cambiarImagen(Image imagen) {
        this.imagen = imagen;
        redimensionarImagen();
        setSize(dimX, dimY); 
        setBackground(Color.red);
        setBorder(BorderFactory.createLineBorder(Color.black,1));
    }
    
    //Devuelve la imagen
    public Image getImagen() {
        return imagen;
    }
}
