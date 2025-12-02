package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el perfil de un usuario desarrollador.
 * Contiene información personal y una colección de competencias técnicas.
 * 
 * @author Proyecto WebPerfilDev
 * @version 1.0
 */
public class Usuario {
    
    // Atributos personales del usuario
    private String nombreCompleto;
    private String descripcionPersonal;
    private String correoContacto;
    private String numeroTelefono;
    private String experienciaAnosText;
    private String urlFotoPerfil;
    
    // Lista de competencias que posee el usuario
    private List<Competencia> listadoCompetencias;
    
    /**
     * Constructor por defecto.
     * Inicializa el usuario con valores vacíos y lista de competencias vacía.
     */
    public Usuario() {
        this.listadoCompetencias = new ArrayList<>();
    }
    
    /**
     * Constructor parametrizado que inicializa todos los atributos personales.
     * La lista de competencias comienza vacía.
     * 
     * @param nombreCompleto Nombre del usuario
     * @param descripcionPersonal Biografía o descripción
     * @param correoContacto Email de contacto
     * @param numeroTelefono Número telefónico
     * @param experienciaAnosText Años de experiencia
     * @param urlFotoPerfil URL a la foto de perfil
     */
    public Usuario(String nombreCompleto, String descripcionPersonal, 
                  String correoContacto, String numeroTelefono, 
                  String experienciaAnosText, String urlFotoPerfil) {
        this.nombreCompleto = nombreCompleto;
        this.descripcionPersonal = descripcionPersonal;
        this.correoContacto = correoContacto;
        this.numeroTelefono = numeroTelefono;
        this.experienciaAnosText = experienciaAnosText;
        this.urlFotoPerfil = urlFotoPerfil;
        this.listadoCompetencias = new ArrayList<>();
    }
    
    // ============ GETTERS (Métodos para obtener valores) ============
    
    /**
     * Obtiene el nombre completo del usuario.
     * @return El nombre del usuario
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    /**
     * Obtiene la descripción personal del usuario.
     * @return La biografía o descripción personal
     */
    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }
    
    /**
     * Obtiene el email de contacto.
     * @return El correo electrónico
     */
    public String getCorreoContacto() {
        return correoContacto;
    }
    
    /**
     * Obtiene el número telefónico.
     * @return El teléfono de contacto
     */
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    
    /**
     * Obtiene el texto que representa años de experiencia.
     * @return String con la experiencia (ej: "5 años")
     */
    public String getExperienciaAnosText() {
        return experienciaAnosText;
    }
    
    /**
     * Obtiene la URL de la foto de perfil.
     * @return URL de la imagen
     */
    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }
    
    /**
     * Obtiene la lista completa de competencias del usuario.
     * @return ArrayList de objetos Competencia
     */
    public List<Competencia> getListadoCompetencias() {
        return listadoCompetencias;
    }
    
    // ============ SETTERS (Métodos para establecer valores) ============
    
    /**
     * Establece el nombre completo del usuario.
     * @param nombreCompleto El nuevo nombre
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    /**
     * Establece la descripción personal del usuario.
     * @param descripcionPersonal La nueva descripción
     */
    public void setDescripcionPersonal(String descripcionPersonal) {
        this.descripcionPersonal = descripcionPersonal;
    }
    
    /**
     * Establece el correo de contacto.
     * @param correoContacto El nuevo email
     */
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }
    
    /**
     * Establece el número telefónico.
     * @param numeroTelefono El nuevo teléfono
     */
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    
    /**
     * Establece el texto de experiencia.
     * @param experienciaAnosText El nuevo texto de experiencia
     */
    public void setExperienciaAnosText(String experienciaAnosText) {
        this.experienciaAnosText = experienciaAnosText;
    }
    
    /**
     * Establece la URL de la foto de perfil.
     * @param urlFotoPerfil La nueva URL
     */
    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }
    
    /**
     * Establece la lista completa de competencias.
     * @param listadoCompetencias La nueva lista de competencias
     */
    public void setListadoCompetencias(List<Competencia> listadoCompetencias) {
        this.listadoCompetencias = listadoCompetencias;
    }
    
    // ============ MÉTODOS DE UTILIDAD ============
    
    /**
     * Agrega una competencia a la lista del usuario.
     * 
     * @param competencia La competencia a agregar
     * @return true si se agregó exitosamente, false si es nula
     */
    public boolean agregarCompetencia(Competencia competencia) {
        if (competencia != null) {
            return listadoCompetencias.add(competencia);
        }
        return false;
    }
    
    /**
     * Elimina una competencia de la lista por su identificador.
     * 
     * @param idCompetencia El ID de la competencia a eliminar
     * @return true si se encontró y eliminó, false si no existe
     */
    public boolean eliminarCompetenciaPorId(Integer idCompetencia) {
        return listadoCompetencias.removeIf(c -> 
            c.getIdentificador().equals(idCompetencia)
        );
    }
    
    /**
     * Busca una competencia por su identificador.
     * 
     * @param idCompetencia El ID a buscar
     * @return La competencia encontrada, o null si no existe
     */
    public Competencia buscarCompetenciaPorId(Integer idCompetencia) {
        for (Competencia c : listadoCompetencias) {
            if (c.getIdentificador().equals(idCompetencia)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Obtiene la cantidad total de competencias del usuario.
     * 
     * @return Número de competencias
     */
    public int obtenerTotalCompetencias() {
        return listadoCompetencias.size();
    }
    
    /**
     * Método toString que muestra información del usuario.
     * Útil para debugging.
     * 
     * @return Una representación en texto del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", correoContacto='" + correoContacto + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", experienciaAnosText='" + experienciaAnosText + '\'' +
                ", totalCompetencias=" + obtenerTotalCompetencias() +
                '}';
    }
}
