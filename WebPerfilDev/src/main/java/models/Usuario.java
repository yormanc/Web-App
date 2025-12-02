package models;

public class Usuario {
    
    private String nombreCompleto;
    private String descripcionPersonal;
    private String correoContacto;
    private String numeroTelefono;
    private String experienciaAnosText;
    private String urlFotoPerfil;
    
    // Constructor vacío
    public Usuario() {
        this.nombreCompleto = "";
        this.descripcionPersonal = "";
        this.correoContacto = "";
        this.numeroTelefono = "";
        this.experienciaAnosText = "";
        this.urlFotoPerfil = "";
    }
    
    // Constructor con parámetros
    public Usuario(String nombreCompleto, String descripcionPersonal,
                  String correoContacto, String numeroTelefono,
                  String experienciaAnosText, String urlFotoPerfil) {
        this.nombreCompleto = nombreCompleto;
        this.descripcionPersonal = descripcionPersonal;
        this.correoContacto = correoContacto;
        this.numeroTelefono = numeroTelefono;
        this.experienciaAnosText = experienciaAnosText;
        this.urlFotoPerfil = urlFotoPerfil;
    }
    
    // Getters
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }
    
    public String getCorreoContacto() {
        return correoContacto;
    }
    
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    
    public String getExperienciaAnosText() {
        return experienciaAnosText;
    }
    
    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }
    
    // Setters
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public void setDescripcionPersonal(String descripcionPersonal) {
        this.descripcionPersonal = descripcionPersonal;
    }
    
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }
    
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    
    public void setExperienciaAnosText(String experienciaAnosText) {
        this.experienciaAnosText = experienciaAnosText;
    }
    
    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", descripcionPersonal='" + descripcionPersonal + '\'' +
                ", correoContacto='" + correoContacto + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", experienciaAnosText='" + experienciaAnosText + '\'' +
                ", urlFotoPerfil='" + urlFotoPerfil + '\'' +
                '}';
    }
}