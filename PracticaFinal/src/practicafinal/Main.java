package practicafinal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.Timer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;

/**
 *
 * @author Cristobal y Sebas
 */
public class Main {

    //DECLARACIÓN DE ATRIBUTOS
    private JFrame usuario;
    private JFrame ventana;
    private JPanel panelVisualizaciones;
    private Timer cronometro;
    private JProgressBar barraTemporal;
    private JTextField nombrejugador;
    private JTextField numerohorizontal;
    private JTextField numerovertical;
    private int columna, fila;
    private CardLayout local;
    private JPanel panelPartidas;
    private PanelSubImagen psi;
    private int cont = 0;
    private int puntos = 0;
    private Partida partida;
    private JTextArea areaVisualizacionResultadosPersonales;
    private JTextArea areaVisualizacionResultados;
    private JPanel panelHistorial;
    private JPanel panelClasificacionGeneral;
    private File fichero = new File("pikachu.jpg");

    public static void main(String[] args) {
        new Main().inicio();
    }

    private void inicio() {
        //CREACION DEL CONTENEDOR JFrame ventana
        ventana = new JFrame("PRACTICA - PROGRAMACION II - 2022-2023 - UIB");

        //DECLARACIÓN PANEL DE CONTENIDOS JContentPane DEL CONTENEDOR JFrame ventana
        Container panelContenidos = ventana.getContentPane();
        panelContenidos.setLayout(new BorderLayout());

        //CREACION DEL CONTENEDOR JFrame usuario
        usuario = new JFrame("INTRODUCCION DATOS");
        Container panelDatos = usuario.getContentPane();
        panelDatos.setLayout(new BorderLayout());

        //CREACION DEL COMPONENTE JButton continuar
        JButton continuar = new JButton("CONFIRMAR");
        panelDatos.add(continuar, BorderLayout.SOUTH);
        continuar.addActionListener(new manipuladorEventosFuncionalidades());

        //CREACION DEL CONTENEDOR JPanel panelEscribir DEL CONTENEDOR JFrame usuario
        JPanel panelEscribir = new JPanel();
        panelEscribir.setLayout(new GridLayout(3, 2));
        panelDatos.add(panelEscribir, BorderLayout.CENTER);
        panelEscribir.setBackground(Color.black);

        //CREACION DEL COMPONENTE JLabel NJ que indca donde hay que poner el nombre
        //del jugador
        JLabel NJ = new JLabel("NOMBRE JUGADOR");
        panelEscribir.add(NJ);
        NJ.setForeground(Color.white);
        NJ.setBackground(Color.black);

        //CREACION DEL COMPONENTE JTextField nombrejugador donde se escribe el nombre
        //del jugador
        nombrejugador = new JTextField(50);
        panelEscribir.add(nombrejugador);

        //CREACION DEL COMPONENTE JLabel NDSH que indca donde hay que poner el numero
        //de subdivisiones horizontales
        JLabel NDSH = new JLabel("NUMERO DE SUBDIVISIONES HORIZONTAL");
        panelEscribir.add(NDSH);
        NDSH.setForeground(Color.white);
        NDSH.setBackground(Color.black);

        //CREACION DEL COMPONENTE JTextField nombrejugador donde se escribe el numero
        //de subdivisiones horizontales
        numerohorizontal = new JTextField(50);
        panelEscribir.add(numerohorizontal);

        //CREACION DEL COMPONENTE JLabel NDSV que indca donde hay que poner el numero
        //de subdivisiones horizontales
        JLabel NDSV = new JLabel("NUMERO DE SUBDIVISIONES VERTICAL");
        panelEscribir.add(NDSV);
        NDSV.setForeground(Color.white);
        NDSV.setBackground(Color.black);

        //CREACION DEL COMPONENTE JTextField nombrejugador donde se escribe el numero
        //de subdivisiones verticales
        numerovertical = new JTextField(50);
        panelEscribir.add(numerovertical);

        usuario.setSize(800, 150);
        usuario.setLocationRelativeTo(null);

        //CREACION DE LOS COMPONENTES JSplitPane que se utilizaran de separadores 
        JSplitPane separador1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelContenidos.add(separador1, BorderLayout.CENTER);

        JSplitPane separador2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        panelContenidos.add(separador2, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        separador1.add(panelBotones);
        panelBotones.setLayout(new GridLayout(4, 1));

        //CREACION DEL CONTENEDOR JPanel panelVisualizaciones con un CardLayout que
        //contendra varios paneles de entre ellos el de juegos
        panelVisualizaciones = new JPanel();
        separador1.add(panelVisualizaciones);
        panelVisualizaciones.setLayout(new CardLayout());
        panelVisualizaciones.setBackground(Color.black);

        //CREACION DEL CONTENEDOR JPanel panelStandBy donde se vera el logo de la uib
        JPanel panelStandBy = new JPanel();
        panelVisualizaciones.add(panelStandBy, "PANEL STAND BY");

        //CREACION DEL CONTENEDOR JPanel panelPartidas en el que se jugará la partida
        panelPartidas = new JPanel();
        panelVisualizaciones.add(panelPartidas, "PANEL PARTIDAS");
        panelPartidas.setLayout(new BorderLayout());

        //CREACION DEL CONTENEDOR JPanel panelImagenSolucion que enseña la solucion del
        //puzle
        JPanel panelImagenSolucion = new JPanel();
        panelVisualizaciones.add(panelImagenSolucion, "PANEL SOLUCION");
        panelImagenSolucion.setLayout(new BorderLayout());

        //CREACION DEL COMPONENTE JButton botonContinuar
        JButton botonContinuar = new JButton("CONTINUAR");
        panelImagenSolucion.add(botonContinuar, BorderLayout.SOUTH);
        botonContinuar.addActionListener(new manipuladorEventosFuncionalidades());

        //Lectura de la imagen de la solucion
        BufferedImage im;
        try {
            im = ImageIO.read(fichero);
            Image sol = im.getSubimage(0, 0, im.getWidth(), im.getHeight());
            Panel imagenSolucion = new Panel(new SubImagen(sol), 1, 1);
            panelImagenSolucion.add(imagenSolucion, BorderLayout.CENTER);
        } catch (IOException ex) {
        }

        //CREACION DEL CONTENEDOR JPanel panelClasificacionGeneral en el que se vera
        //la clasificacion general
        panelClasificacionGeneral = new JPanel();
        panelVisualizaciones.add(panelClasificacionGeneral, "PANEL CLASIFICACION GENERAL");
        panelClasificacionGeneral.setBackground(Color.white);

        areaVisualizacionResultados = new JTextArea();
        panelClasificacionGeneral.add(areaVisualizacionResultados);
        areaVisualizacionResultados.setFont(new Font("Arial", Font.BOLD, 24));
        areaVisualizacionResultados.append("                                    HISTORIAL\n");
        areaVisualizacionResultados.append("\n");

        //CREACION DEL CONTENEDOR JPanel panelHistorial en el que se vera
        //la clasificacion general
        panelHistorial = new JPanel();
        panelVisualizaciones.add(panelHistorial, "PANEL HISTORIAL");
        panelHistorial.setBackground(Color.white);

        areaVisualizacionResultadosPersonales = new JTextArea();
        panelHistorial.add(areaVisualizacionResultadosPersonales);
        areaVisualizacionResultadosPersonales.setFont(new Font("Arial", Font.BOLD, 24));
        areaVisualizacionResultadosPersonales.append("                                    HISTORIAL\n");
        areaVisualizacionResultadosPersonales.append("\n");

        //CREACION DEL CONTENEDOR JMenuBar barraMenu
        JMenuBar barraMenu = new JMenuBar();
        separador2.add(barraMenu);
        barraMenu.setLayout(new BorderLayout());
        barraMenu.setBackground(Color.black);

        //CREACION DEL CONTENEDOR JMenu menu y de todos los JMenuItem con las
        //funcionalidades de todos los botones
        JMenu menu = new JMenu("MENU");
        barraMenu.add(menu, BorderLayout.NORTH);
        menu.setForeground(Color.white);

        JMenuItem nuevaPartidaMenu = new JMenuItem("NUEVA PARTIDA");
        nuevaPartidaMenu.setForeground(Color.white);
        nuevaPartidaMenu.setBackground(Color.black);
        nuevaPartidaMenu.addActionListener(new manipuladorEventosFuncionalidades());

        JMenuItem clasificacionMenu = new JMenuItem("CLASIFICACION GENERAL");
        clasificacionMenu.setForeground(Color.white);
        clasificacionMenu.setBackground(Color.black);
        clasificacionMenu.addActionListener(new manipuladorEventosFuncionalidades());

        JMenuItem historialMenu = new JMenuItem("HISTORIAL");
        historialMenu.setForeground(Color.white);
        historialMenu.setBackground(Color.black);
        historialMenu.addActionListener(new manipuladorEventosFuncionalidades());

        JMenuItem cambiarDirectorioMenu = new JMenuItem("CAMBIAR DIRECTORIO DE IMAGENES");
        cambiarDirectorioMenu.setForeground(Color.white);
        cambiarDirectorioMenu.setBackground(Color.black);
        cambiarDirectorioMenu.addActionListener(new manipuladorEventosFuncionalidades());

        JMenuItem salirMenu = new JMenuItem("SALIR");
        salirMenu.setForeground(Color.white);
        salirMenu.setBackground(Color.black);
        salirMenu.addActionListener(new manipuladorEventosFuncionalidades());

        menu.add(nuevaPartidaMenu);
        menu.add(clasificacionMenu);
        menu.add(historialMenu);
        menu.add(cambiarDirectorioMenu);
        menu.add(salirMenu);

        //CREACION DE LOS COMPONENTES JButton que son los botones que iran en el
        //panelBotones
        JButton nuevaPartidaBoton = new JButton("NUEVA PARTIDA");
        nuevaPartidaBoton.setForeground(Color.white);
        nuevaPartidaBoton.setBackground(Color.black);
        nuevaPartidaBoton.addActionListener(new manipuladorEventosFuncionalidades());

        JButton clasificacionBoton = new JButton("CLASIFICACION GENERAL");
        clasificacionBoton.setForeground(Color.white);
        clasificacionBoton.setBackground(Color.black);
        clasificacionBoton.addActionListener(new manipuladorEventosFuncionalidades());

        JButton historialBoton = new JButton("HISTORIAL");
        historialBoton.setForeground(Color.white);
        historialBoton.setBackground(Color.black);
        historialBoton.addActionListener(new manipuladorEventosFuncionalidades());

        JButton salirBoton = new JButton("SALIR");
        salirBoton.setForeground(Color.white);
        salirBoton.setBackground(Color.black);
        salirBoton.addActionListener(new manipuladorEventosFuncionalidades());

        panelBotones.add(nuevaPartidaBoton);
        panelBotones.add(clasificacionBoton);
        panelBotones.add(historialBoton);
        panelBotones.add(salirBoton);

        //CREACION DEL CONTENEDOR JToolBar IconosMenu y de todos los JButton con 
        //iconos con las funcionalidades de todos los botones y con gestores de
        //eventos propios
        JToolBar IconosMenu = new JToolBar();
        barraMenu.add(IconosMenu, BorderLayout.CENTER);
        IconosMenu.setBackground(Color.black);
        IconosMenu.setFloatable(false);

        JButton nuevaPartidaIcono = new JButton(new ImageIcon("iconoNuevaPartida.jpg"));
        nuevaPartidaIcono.setBackground(Color.black);
        nuevaPartidaIcono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                usuario.setVisible(true);
            }
        });

        JButton clasificacionIcono = new JButton(new ImageIcon("iconoHistorialGeneral.jpg"));
        clasificacionIcono.setBackground(Color.black);
        clasificacionIcono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                local = (CardLayout) (panelVisualizaciones.getLayout());
                local.show(panelVisualizaciones, "PANEL CLASIFICACION GENERAL");
            }
        });

        JButton historialIcono = new JButton(new ImageIcon("iconoHistorialSelectivo.jpg"));
        historialIcono.setBackground(Color.black);
        historialIcono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                local = (CardLayout) (panelVisualizaciones.getLayout());
                local.show(panelVisualizaciones, "PANEL HISTORIAL");
            }
        });

        JButton cambiarDirectorioIcono = new JButton(new ImageIcon("iconoCambiarDIrectorio.jpg"));
        cambiarDirectorioIcono.setBackground(Color.black);
        cambiarDirectorioIcono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                JFileChooser ventanaSeleccion = new JFileChooser();
                int op = ventanaSeleccion.showOpenDialog(ventana);
                if (op == JFileChooser.APPROVE_OPTION) {
                    fichero = ventanaSeleccion.getSelectedFile();
                }
            }
        });

        JButton salirIcono = new JButton(new ImageIcon("iconoSalir.jpg"));
        salirIcono.setBackground(Color.black);
        salirIcono.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                System.exit(0);
            }
        });

        IconosMenu.add(nuevaPartidaIcono);
        IconosMenu.add(clasificacionIcono);
        IconosMenu.add(historialIcono);
        IconosMenu.add(cambiarDirectorioIcono);
        IconosMenu.add(salirIcono);

        //CREACION DEL COMPONENTE JProgressBar que es la barra de progresion del juego
        barraTemporal = new JProgressBar();
        panelPartidas.add(barraTemporal, BorderLayout.SOUTH);
        barraTemporal.setBackground(Color.yellow);
        barraTemporal.setForeground(Color.red);
        barraTemporal.setStringPainted(true);

        //CREACION DEL COMPONENTE JLabel con la imagende la uib
        JLabel imagenUIB = new JLabel(new ImageIcon("UIB.jpg"));
        panelStandBy.add(imagenUIB, BorderLayout.CENTER);

        //DIMENSIONAMIENTO DEL CONTENEDOR JFrame ventana 
        ventana.setSize(1300, 900);
        //CENTRADO DEL CONTENEDOR ventana EN EL CENTRO DE LA PANTALLA
        ventana.setLocationRelativeTo(null);
        //ACTIVACIÓN DEL CIERRE INTERACTIVO VENTANA DE WINDOWS EN EL CONTENEDOR 
        //JFrame ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        //VISUALIZACIÓN CONTENEDOR JFrame ventana
        ventana.setVisible(true);
    }
    
    //Nuestro gestor de eventos que gestiona la mayoria de estos
    private class manipuladorEventosFuncionalidades implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            local = (CardLayout) (panelVisualizaciones.getLayout());
            switch (evento.getActionCommand()) {
                
                //Al darle a continuar cuando nos sale la solucion al haber perdido
                //la partida el panelVisualizaciones sale el panelStandBy
                case "CONTINUAR" ->
                    local.show(panelVisualizaciones, "PANEL STAND BY");
                    
                //Al empezar una nueva partida se hace visible la ventana usuario
                case "NUEVA PARTIDA" ->
                    usuario.setVisible(true);
                    
                //Al abrir el historial selectivo tendremos que escribir el nombre del
                //jugador en cuestion y se comprobara este nombre con todas las partidas
                //guardadas hasta encontrar las que coincidan y las enseñara en el JTextArea
                case "HISTORIAL" -> {
                    String input = "";
                    try {
                        input = JOptionPane.showInputDialog("HISTORIAL JUGADOR\n INTRODUCIR NOMBRE DE JUGADOR");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error");
                        break;
                    }
                    panelHistorial.remove(areaVisualizacionResultadosPersonales);
                    areaVisualizacionResultadosPersonales = new JTextArea();
                    panelHistorial.add(areaVisualizacionResultadosPersonales);
                    areaVisualizacionResultadosPersonales.setFont(new Font("Arial", Font.BOLD, 24));
                    areaVisualizacionResultadosPersonales.append("                                    HISTORIAL\n");
                    areaVisualizacionResultadosPersonales.append("\n");
                    try {
                        PartidaObjetoLectura pol = new PartidaObjetoLectura("partidas.dat");
                        try {
                            while (true) {
                                partida = pol.leerPartida();
                                if (partida.getNombre().equals(input)) {
                                    areaVisualizacionResultadosPersonales.append(partida.toString() + "\n");
                                }
                            }
                        } catch (EOFException ex) {
                            pol.cerrarFichero();
                        } catch (ClassNotFoundException ex) {
                        }
                    } catch (IOException ex) {
                    }
                    local.show(panelVisualizaciones, "PANEL HISTORIAL");
                }

                //Al abrir la clasificacion general enseñara en el JTextArea todas
                //las partidas jugadas
                case "CLASIFICACION GENERAL" -> {
                    panelClasificacionGeneral.remove(areaVisualizacionResultados);
                    areaVisualizacionResultados = new JTextArea();
                    panelClasificacionGeneral.add(areaVisualizacionResultados);
                    areaVisualizacionResultados.setFont(new Font("Arial", Font.BOLD, 24));
                    areaVisualizacionResultados.append("                                    HISTORIAL\n");
                    areaVisualizacionResultados.append("\n");
                    try {
                        PartidaObjetoLectura pol = new PartidaObjetoLectura("partidas.dat");
                        try {
                            while (true) {
                                partida = pol.leerPartida();
                                areaVisualizacionResultados.append(partida.toString() + "\n");
                            }
                        } catch (EOFException ex) {
                            pol.cerrarFichero();
                        } catch (ClassNotFoundException ex) {
                        }
                    } catch (IOException ex) {
                    }
                    local.show(panelVisualizaciones, "PANEL CLASIFICACION GENERAL");
                }
                
                //Con el JFileChooser se selecciona el fichero con la imagen designada
                case "CAMBIAR DIRECTORIO DE IMAGENES" -> {
                    JFileChooser ventanaSeleccion = new JFileChooser();
                    int op = ventanaSeleccion.showOpenDialog(ventana);
                    if (op == JFileChooser.APPROVE_OPTION) {
                        fichero = ventanaSeleccion.getSelectedFile();
                    }
                }
                
                //Cuando ya se ha escrito el nombre y las subdivisiones se guarda, se recorta
                //la imagen y se inicia el cronometro para empezar a jugar
                case "CONFIRMAR" -> {
                    partida = new Partida();
                    partida.setNombre(nombrejugador.getText());
                    fila = Integer.parseInt(numerohorizontal.getText());
                    columna = Integer.parseInt(numerovertical.getText());
                    usuario.setVisible(false);
                    nombrejugador.setText("");
                    numerohorizontal.setText("");
                    numerovertical.setText("");
                    try {
                        BufferedImage imagen = ImageIO.read(fichero);
                        generaciónImagenesRecortes(imagen);
                    } catch (IOException e) {
                    }
                    local.show(panelVisualizaciones, "PANEL PARTIDAS");
                    cronometro = new Timer(columna * fila * columna * fila, gestorEvento);
                    cronometro.start();
                }
                case "SALIR" ->
                    System.exit(0);
            }
        }
    };

    ActionListener gestorEvento = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent evento) {
            if (evento.getSource() == cronometro) {
                int valor = barraTemporal.getValue();
                //Comprueba que el cronometro no haya llegado al final y en ese caso lo incrementa en 1
                if (valor < 100) {
                    barraTemporal.setValue(++valor);
                    //Si el puzle esta solucionado guarda todos los datos en un fichero y para el cronometro
                    if (psi.getCorrect()) {
                        puntos = columna * fila * (100 / barraTemporal.getValue());
                        String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Madrid")).format(DateTimeFormatter.ofPattern("HH:mm dd:MM:yy"));
                        partida.setPuntos(puntos);
                        partida.setFecha(timestamp);
                        PartidaObjetoEscritura poe = new PartidaObjetoEscritura("partidas.dat");
                        poe.escribirPartida(partida);
                        poe.cerrarFichero();
                        cronometro.stop();
                        barraTemporal.setValue(0);
                    }
                //Si el cronometro ha llegado al final significa que has perdido y guarda los datos de haber
                //perdido en un fichero, sale un mensaje de que has perdido y te sale la solucion
                } else {
                    cronometro.stop();
                    puntos = 0;
                    String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Madrid")).format(DateTimeFormatter.ofPattern("HH:mm dd:MM:yy"));
                    partida.setPuntos(puntos);
                    partida.setFecha(timestamp);
                    PartidaObjetoEscritura poe = new PartidaObjetoEscritura("partidas.dat");
                    poe.escribirPartida(partida);
                    poe.cerrarFichero();
                    JOptionPane.showMessageDialog(null, "NO LO HAS CONSEGUIDO, EL TIEMPO HA TERMINADO");
                    barraTemporal.setValue(0);
                    local.show(panelVisualizaciones, "PANEL SOLUCION");
                }
            }
        }
    };

    public void generaciónImagenesRecortes(BufferedImage imagen) {
        int y = 0;
        int k = 0;
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        int w = width / columna;
        int h = height / fila;
        int max = columna * fila;
        SubImagen image[] = new SubImagen[max];
        //Recorta la imagen y la transforma en subImagenes
        for (int i = 0; i < fila; i++) {
            int x = 0;
            for (int j = 0; j < columna; j++) {
                //System.out.println(x +" " + y +" "+ w +" "+ h);
                Image imagenSalida = imagen.getSubimage(x, y, w, h);
                image[k] = new SubImagen(imagenSalida);
                x += w;
                k++;
            }
            y += h;
        }
        //Comprueba que no sea la primera partida y elimina la partida anterior
        if (cont > 0) {
            psi.setVisible(false);
            panelPartidas.remove(psi);
        }
        //Crea un PanelSubImagen con las subImagenes hechas
        psi = new PanelSubImagen(image, fila, columna);
        panelPartidas.add(psi, BorderLayout.CENTER);
        cont++;
    }
}
