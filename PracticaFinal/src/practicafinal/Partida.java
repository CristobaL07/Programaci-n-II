package practicafinal;

/**
 *
 * @author Cristobal y Sebas
 */
public class Partida implements java.io.Serializable{
    //DECLARACIÓN DE ATRIBUTOS
    private String nombre;
    private String fecha;
    private int puntos;
    
    //MÉTODO CONSTRUCTOR VACIO
    public Partida() {
        
    }
    
    //MÉTODO CONSTRUCTOR
    public Partida(String nombre, String fecha, int puntos) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.puntos = puntos;
    }
    
    //Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    //Metodo toString
    @Override
    public String toString() {
        return "NOMBRE: " + nombre + "         - FECHA: " + fecha + "   - PUNTOS: " + puntos;
    }
}
