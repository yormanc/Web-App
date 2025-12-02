package models;

/**
 * Clase que representa una competencia técnica o habilidad profesional.
 * Almacena información sobre tecnologías, lenguajes o herramientas
 * que posee un desarrollador.
 * 
 * @author Proyecto WebPerfilDev
 * @version 1.0
 */
public class Competencia {
    
    // Atributos (propiedades) de la competencia
    private Integer identificador;
    private String denominacion;
    private String gradoDominacion;
    private String colorAsociado;
    
    /**
     * Constructor por defecto sin parámetros.
     * Inicializa una competencia vacía.
     */
    public Competencia() {
    }
    
    /**
     * Constructor parametrizado que inicializa todos los atributos.
     * 
     * @param identificador ID único de la competencia
     * @param denominacion Nombre de la tecnología (Java, Python, etc.)
     * @param gradoDominacion Nivel de dominio (Básico, Intermedio, Avanzado, Experto)
     * @param colorAsociado Color HEX para visualización (#667eea, etc.)
     */
    public Competencia(Integer identificador, String denominacion, 
                      String gradoDominacion, String colorAsociado) {
        this.identificador = identificador;
        this.denominacion = denominacion;
        this.gradoDominacion = gradoDominacion;
        this.colorAsociado = colorAsociado;
    }
    
    // ============ GETTERS (Métodos para obtener valores) ============
    
    /**
     * Obtiene el identificador único de la competencia.
     * @return El ID de la competencia
     */
    public Integer getIdentificador() {
        return identificador;
    }
    
    /**
     * Obtiene el nombre/denominación de la competencia.
     * @return El nombre de la tecnología
     */
    public String getDenominacion() {
        return denominacion;
    }
    
    /**
     * Obtiene el grado de dominio de la competencia.
     * @return El nivel de dominio (Básico, Intermedio, Avanzado, Experto)
     */
    public String getGradoDominacion() {
        return gradoDominacion;
    }
    
    /**
     * Obtiene el color asociado para visualización.
     * @return Color en formato HEX
     */
    public String getColorAsociado() {
        return colorAsociado;
    }
    
    // ============ SETTERS (Métodos para establecer valores) ============
    
    /**
     * Establece el identificador único de la competencia.
     * @param identificador El nuevo ID
     */
    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }
    
    /**
     * Establece el nombre/denominación de la competencia.
     * @param denominacion El nuevo nombre
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    
    /**
     * Establece el grado de dominio de la competencia.
     * @param gradoDominacion El nuevo nivel de dominio
     */
    public void setGradoDominacion(String gradoDominacion) {
        this.gradoDominacion = gradoDominacion;
    }
    
    /**
     * Establece el color asociado para visualización.
     * @param colorAsociado El nuevo color HEX
     */
    public void setColorAsociado(String colorAsociado) {
        this.colorAsociado = colorAsociado;
    }
    
    /**
     * Método toString que muestra todos los atributos de la competencia.
     * Útil para debugging.
     * 
     * @return Una representación en texto de la competencia
     */
    @Override
    public String toString() {
        return "Competencia{" +
                "identificador=" + identificador +
                ", denominacion='" + denominacion + '\'' +
                ", gradoDominacion='" + gradoDominacion + '\'' +
                ", colorAsociado='" + colorAsociado + '\'' +
                '}';
    }
}
