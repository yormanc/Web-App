package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la persistencia de datos en archivos JSON.
 * Maneja lectura y escritura del perfil del usuario y sus competencias.
 * 
 * @author Proyecto WebPerfilDev
 * @version 1.0
 */
public class AdministradorDatos {
    
    // Rutas de archivos JSON donde se guardan los datos
    private static final String RUTA_ARCHIVO_USUARIO = "datos/usuarioBase.json";
    private static final String RUTA_ARCHIVO_COMPETENCIAS = "datos/competenciasBase.json";
    
    /**
     * Lee un archivo de texto y retorna su contenido como String.
     * 
     * @param rutaArchivo La ruta del archivo a leer
     * @return El contenido del archivo
     * @throws IOException Si hay error al leer
     */
    private static String leerContenidoArchivo(String rutaArchivo) throws IOException {
        StringBuilder contenidoCompleto = new StringBuilder();
        
        try (BufferedReader lectorBuffer = new BufferedReader(new FileReader(rutaArchivo))) {
            String lineaActual;
            
            while ((lineaActual = lectorBuffer.readLine()) != null) {
                contenidoCompleto.append(lineaActual);
            }
        }
        
        return contenidoCompleto.toString();
    }
    
    /**
     * Escribe contenido en un archivo, creando el directorio si no existe.
     * 
     * @param rutaArchivo La ruta donde guardar
     * @param contenidoAGuardar El contenido a escribir
     * @throws IOException Si hay error al escribir
     */
    private static void guardarContenidoEnArchivo(String rutaArchivo, String contenidoAGuardar) throws IOException {
        File archivoDestino = new File(rutaArchivo);
        
        // Crear directorio si no existe
        if (archivoDestino.getParentFile() != null) {
            archivoDestino.getParentFile().mkdirs();
        }
        
        try (BufferedWriter escritorBuffer = new BufferedWriter(new FileWriter(archivoDestino))) {
            escritorBuffer.write(contenidoAGuardar);
            escritorBuffer.flush();
        }
    }
    
    /**
     * Obtiene el usuario actual desde el archivo JSON.
     * 
     * @return Objeto Usuario con datos persistidos
     */
    public static Usuario cargarDatosUsuario() {
        Usuario usuarioRecuperado = new Usuario();
        
        try {
            String contenidoJSON = leerContenidoArchivo(RUTA_ARCHIVO_USUARIO);
            JSONObject datosJSON = new JSONObject(contenidoJSON);
            
            // Llenar atributos del usuario desde JSON
            usuarioRecuperado.setNombreCompleto(datosJSON.getString("nombre"));
            usuarioRecuperado.setDescripcionPersonal(datosJSON.getString("bio"));
            usuarioRecuperado.setCorreoContacto(datosJSON.getString("email"));
            usuarioRecuperado.setNumeroTelefono(datosJSON.getString("telefono"));
            usuarioRecuperado.setExperienciaAnosText(datosJSON.getString("experiencia"));
            usuarioRecuperado.setUrlFotoPerfil(datosJSON.getString("fotoPerfil"));
            
        } catch (Exception excepcion) {
            System.out.println("Error cargando usuario: " + excepcion.getMessage());
            // Retornar usuario con datos por defecto
            usuarioRecuperado.setNombreCompleto("Usuario");
            usuarioRecuperado.setDescripcionPersonal("Descripción no disponible");
        }
        
        return usuarioRecuperado;
    }
    
    /**
     * Guarda los datos del usuario en archivo JSON.
     * 
     * @param usuarioAGuardar El usuario cuyos datos se guardarán
     * @return true si se guardó exitosamente, false si hubo error
     */
    public static boolean guardarDatosUsuario(Usuario usuarioAGuardar) {
        try {
            JSONObject datosUsuarioJSON = new JSONObject();
            
            datosUsuarioJSON.put("nombre", usuarioAGuardar.getNombreCompleto());
            datosUsuarioJSON.put("bio", usuarioAGuardar.getDescripcionPersonal());
            datosUsuarioJSON.put("email", usuarioAGuardar.getCorreoContacto());
            datosUsuarioJSON.put("telefono", usuarioAGuardar.getNumeroTelefono());
            datosUsuarioJSON.put("experiencia", usuarioAGuardar.getExperienciaAnosText());
            datosUsuarioJSON.put("fotoPerfil", usuarioAGuardar.getUrlFotoPerfil());
            
            String jsonFormateado = datosUsuarioJSON.toString(2); // 2 espacios de indentación
            guardarContenidoEnArchivo(RUTA_ARCHIVO_USUARIO, jsonFormateado);
            
            return true;
            
        } catch (Exception excepcion) {
            System.out.println("Error guardando usuario: " + excepcion.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene todas las competencias desde el archivo JSON.
     * 
     * @return Lista de objetos Competencia
     */
    public static List<Competencia> cargarTodasLasCompetencias() {
        List<Competencia> competenciasRecuperadas = new ArrayList<>();
        
        try {
            String contenidoJSON = leerContenidoArchivo(RUTA_ARCHIVO_COMPETENCIAS);
            JSONArray arregloJSON = new JSONArray(contenidoJSON);
            
            // Recorrer cada competencia en el JSON
            for (int indice = 0; indice < arregloJSON.length(); indice++) {
                JSONObject competenciaJSON = arregloJSON.getJSONObject(indice);
                
                Competencia competenciaObjeto = new Competencia(
                    competenciaJSON.getInt("id"),
                    competenciaJSON.getString("nombre"),
                    competenciaJSON.getString("nivel"),
                    competenciaJSON.getString("color")
                );
                
                competenciasRecuperadas.add(competenciaObjeto);
            }
            
        } catch (Exception excepcion) {
            System.out.println("Error cargando competencias: " + excepcion.getMessage());
        }
        
        return competenciasRecuperadas;
    }
    
    /**
     * Agrega una nueva competencia a la lista y guarda en JSON.
     * Genera automáticamente el próximo ID disponible.
     * 
     * @param competenciaAgregar La competencia a agregar
     * @return true si se agregó exitosamente
     */
    public static boolean agregarNuevaCompetencia(Competencia competenciaAgregar) {
        try {
            List<Competencia> todasLasCompetencias = cargarTodasLasCompetencias();
            
            // Generar ID automático (último ID + 1)
            Integer proximoID = 1;
            if (!todasLasCompetencias.isEmpty()) {
                Integer ultimoID = todasLasCompetencias.get(todasLasCompetencias.size() - 1).getIdentificador();
                proximoID = ultimoID + 1;
            }
            
            competenciaAgregar.setIdentificador(proximoID);
            todasLasCompetencias.add(competenciaAgregar);
            
            return guardarListaCompetencias(todasLasCompetencias);
            
        } catch (Exception excepcion) {
            System.out.println("Error agregando competencia: " + excepcion.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza una competencia existente identificada por su ID.
     * 
     * @param competenciaActualizar La competencia con datos nuevos (debe tener ID)
     * @return true si se actualizó exitosamente
     */
    public static boolean actualizarCompetenciaExistente(Competencia competenciaActualizar) {
        try {
            List<Competencia> todasLasCompetencias = cargarTodasLasCompetencias();
            
            // Buscar y reemplazar la competencia
            for (int indice = 0; indice < todasLasCompetencias.size(); indice++) {
                if (todasLasCompetencias.get(indice).getIdentificador()
                        .equals(competenciaActualizar.getIdentificador())) {
                    todasLasCompetencias.set(indice, competenciaActualizar);
                    return guardarListaCompetencias(todasLasCompetencias);
                }
            }
            
            System.out.println("Competencia con ID " + competenciaActualizar.getIdentificador() + " no encontrada");
            return false;
            
        } catch (Exception excepcion) {
            System.out.println("Error actualizando competencia: " + excepcion.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una competencia identificada por su ID.
     * 
     * @param idAEliminar El ID de la competencia a eliminar
     * @return true si se eliminó exitosamente
     */
    public static boolean eliminarCompetenciaPorIdentificador(Integer idAEliminar) {
        try {
            List<Competencia> todasLasCompetencias = cargarTodasLasCompetencias();
            
            // Buscar y eliminar
            boolean encontrada = false;
            for (int indice = 0; indice < todasLasCompetencias.size(); indice++) {
                if (todasLasCompetencias.get(indice).getIdentificador().equals(idAEliminar)) {
                    todasLasCompetencias.remove(indice);
                    encontrada = true;
                    break;
                }
            }
            
            if (encontrada) {
                return guardarListaCompetencias(todasLasCompetencias);
            } else {
                System.out.println("Competencia con ID " + idAEliminar + " no encontrada");
                return false;
            }
            
        } catch (Exception excepcion) {
            System.out.println("Error eliminando competencia: " + excepcion.getMessage());
            return false;
        }
    }
    
    /**
     * Guarda una lista completa de competencias en el archivo JSON.
     * Método privado usado internamente.
     * 
     * @param competenciasAGuardar Lista de competencias
     * @return true si se guardó exitosamente
     */
    private static boolean guardarListaCompetencias(List<Competencia> competenciasAGuardar) {
        try {
            JSONArray arregloJSON = new JSONArray();
            
            // Convertir cada competencia a JSONObject
            for (Competencia competencia : competenciasAGuardar) {
                JSONObject competenciaJSON = new JSONObject();
                competenciaJSON.put("id", competencia.getIdentificador());
                competenciaJSON.put("nombre", competencia.getDenominacion());
                competenciaJSON.put("nivel", competencia.getGradoDominacion());
                competenciaJSON.put("color", competencia.getColorAsociado());
                
                arregloJSON.put(competenciaJSON);
            }
            
            String jsonFormateado = arregloJSON.toString(2); // 2 espacios de indentación
            guardarContenidoEnArchivo(RUTA_ARCHIVO_COMPETENCIAS, jsonFormateado);
            
            return true;
            
        } catch (Exception excepcion) {
            System.out.println("Error guardando lista de competencias: " + excepcion.getMessage());
            return false;
        }
    }
    
    /**
     * Inicializa los archivos JSON con datos por defecto si no existen.
     * Se ejecuta una vez al inicio de la aplicación.
     */
    public static void inicializarArchivosJSONPorDefecto() {
        try {
            File archivoUsuario = new File(RUTA_ARCHIVO_USUARIO);
            File archivoCompetencias = new File(RUTA_ARCHIVO_COMPETENCIAS);
            
            // Crear archivo usuario si no existe
            if (!archivoUsuario.exists()) {
                JSONObject datosDefectoPerfil = new JSONObject();
                datosDefectoPerfil.put("nombre", "Tu Nombre");
                datosDefectoPerfil.put("bio", "Desarrollador apasionado por la tecnología");
                datosDefectoPerfil.put("email", "tu@email.com");
                datosDefectoPerfil.put("telefono", "+57 (XXX) XXXXXX");
                datosDefectoPerfil.put("experiencia", "3 años");
                datosDefectoPerfil.put("fotoPerfil", "https://ui-avatars.com/api/?name=Developer");
                
                guardarContenidoEnArchivo(RUTA_ARCHIVO_USUARIO, datosDefectoPerfil.toString(2));
            }
            
            // Crear archivo competencias si no existe
            if (!archivoCompetencias.exists()) {
                JSONArray arregloDefectoCompetencias = new JSONArray();
                
                String[][] datosCompetenciasIniciales = {
                    {"1", "Java", "Avanzado", "#ED8936"},
                    {"2", "Python", "Intermedio", "#3776AB"},
                    {"3", "JavaScript", "Avanzado", "#F7DF1E"},
                    {"4", "HTML", "Experto", "#E34C26"},
                    {"5", "CSS", "Experto", "#1572B6"}
                };
                
                for (String[] datos : datosCompetenciasIniciales) {
                    JSONObject competenciaDefecto = new JSONObject();
                    competenciaDefecto.put("id", Integer.parseInt(datos[0]));
                    competenciaDefecto.put("nombre", datos[1]);
                    competenciaDefecto.put("nivel", datos[2]);
                    competenciaDefecto.put("color", datos[3]);
                    
                    arregloDefectoCompetencias.put(competenciaDefecto);
                }
                
                guardarContenidoEnArchivo(RUTA_ARCHIVO_COMPETENCIAS, arregloDefectoCompetencias.toString(2));
            }
            
        } catch (Exception excepcion) {
            System.out.println("Error inicializando archivos JSON: " + excepcion.getMessage());
        }
    }
}