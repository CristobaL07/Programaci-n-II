package practicafinal;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Cristobal y Sebas
 */
public class PanelSubImagen extends JPanel {
    //DECLARACIÓN DE ATRIBUTOS
    private Panel[] paneles;
    private SubImagen[] image;
    private int fila, columna;
    private int panelSeleccionado = 0;
    private int panelPrevio = 0;
    private int[] solucion;
    private int doble = 0;
    private boolean correct = false;
    
    //MÉTODO CONSTRUCTOR
    public PanelSubImagen(SubImagen[] image, int fila, int columna) {
        super();
        this.image = image;
        this.fila = fila;
        this.columna = columna;
        setSize(1095, 767);
        paneles = new Panel[image.length];
        setLayout(new GridLayout(fila, columna, 2, 2));
        setBackground(Color.black);
        inicializacion();
    }
    
    //Crea un array de numeros aleatorios poniendo en el array de paneles la imagen
    //que esta en la posicion de numeros alatorios
    public void inicializacion() {
        for (int i = 0; i < image.length; i++) {
            paneles[i] = new Panel(image[i], columna, fila);
            paneles[i].addMouseListener(eventosRaton());
        }
        int aleatorio;
        boolean encontrado;
        solucion = new int[image.length];
        for (int i = 0; i < image.length - 1; i++) {
            encontrado = false;
            aleatorio = new Random().nextInt(image.length);
            for (int j = 0; j < image.length; j++) {
                if (solucion[j] == aleatorio) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                add(paneles[aleatorio]);
                solucion[i] = aleatorio;
            } else {
                i--;
            }
        }
        solucion[image.length - 1] = 0;
        add(paneles[0]);
    }

    public MouseListener eventosRaton() {
        MouseListener accion = new MouseListener() {
            @Override
            public void mousePressed(MouseEvent evento) {
                paneles[panelSeleccionado].deseleccionarPanel();
                int numeroComponente = 0;
                int aux;
                int correcto = 0;
                Image imagenaux;
                //Coge la posicion del panel pulsado
                for (; numeroComponente < paneles.length; numeroComponente++) {
                    if (evento.getSource() == paneles[numeroComponente]) {
                        break;
                    }
                }
                //Si es el segundo panel que se ha pulsado se guarda la posicion
                //del panel anterior pulsado
                if (doble == 1) {
                    panelPrevio = panelSeleccionado;
                }
                //Guarda la posicion del panel pulsado
                panelSeleccionado = numeroComponente;
                paneles[panelSeleccionado].seleccionarPanel();
                doble++;
                //Una vez que se tiene la posicion de los dos paneles a cambiar les
                //intercambia las imagenes y intercambia los numeros del array de
                //numeros aleatorios
                if (doble == 2) {
                    imagenaux = paneles[panelSeleccionado].getImagen();
                    paneles[panelSeleccionado].cambiarImagen(paneles[panelPrevio].getImagen());
                    paneles[panelPrevio].cambiarImagen(imagenaux);
                    aux = solucion[panelSeleccionado];
                    solucion[panelSeleccionado] = solucion[panelPrevio];
                    solucion[panelPrevio] = aux;
                    doble = 0;
                }
                //Comprueba que en el array de numeros aleatorios estan todos los numeros
                //en su posicion correcta
                for (int i = 0; i < solucion.length; i++) {
                    if (solucion[i] == i) {
                        correcto++;
                    }
                }
                //Si todos los numeros estan ordenados significa que el puzle esta solucionado
                if (correcto == solucion.length) {
                    correct = true;
                    JOptionPane.showMessageDialog(null, "¡¡¡ENORABUENA!!! LO HAS CONSEGUIDO HAS OBTENIDO " + columna * fila + " PUNTOS");

                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        return accion;
    }

    //Devuelve si el puzle esta solucionado correctamente
    public Boolean getCorrect() {
        return correct;
    }
}
