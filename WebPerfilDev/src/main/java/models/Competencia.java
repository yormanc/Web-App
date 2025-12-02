package models;

public class Competencia {
    
    private Integer identificador;
    private String denominacion;
    private String gradoDominacion;
    private String colorAsociado;
    
    // Constructor vacío
    public Competencia() {
        this.identificador = 0;
        this.denominacion = "";
        this.gradoDominacion = "";
        this.colorAsociado = "#667eea";
    }
    
    // Constructor con parámetros
    public Competencia(Integer identificador, String denominacion, 
                      String gradoDominacion, String colorAsociado) {
        this.identificador = identificador;
        this.denominacion = denominacion;
        this.gradoDominacion = gradoDominacion;
        this.colorAsociado = colorAsociado;
    }
    
    // Getters
    public Integer getIdentificador() {
        return identificador;
    }
    
    public String getDenominacion() {
        return denominacion;
    }
    
    public String getGradoDominacion() {
        return gradoDominacion;
    }
    
    public String getColorAsociado() {
        return colorAsociado;
    }
    
    // Setters
    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }
    
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    
    public void setGradoDominacion(String gradoDominacion) {
        this.gradoDominacion = gradoDominacion;
    }
    
    public void setColorAsociado(String colorAsociado) {
        this.colorAsociado = colorAsociado;
    }
    
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