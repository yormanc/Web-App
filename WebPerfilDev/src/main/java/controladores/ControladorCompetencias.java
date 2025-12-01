package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelos.Competencia;
import modelos.AdministradorDatos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet que actúa como controlador de competencias.
 * Se encarga de manejar el CRUD (Create, Read, Update, Delete)
 * de las competencias del usuario.
 * 
 * URL de acceso: /WebPerfilDev-1.0-SNAPSHOT/competencias
 * 
 * Métodos soportados:
 * - GET: Obtener todas las competencias en JSON
 * - POST: Agregar, actualizar o eliminar una competencia
 * 
 * @author Proyecto WebPerfilDev
 * @version 1.0
 */
@WebServlet(name = "ControladorCompetencias", urlPatterns = { "/competencias" })
public class ControladorCompetencias extends HttpServlet {
    
    /**
     * Maneja peticiones GET que solicitan obtener las competencias.
     * Retorna un JSON con todas las competencias.
     * 
     * @param request  Objeto que contiene información de la petición
     * @param response Objeto para enviar la respuesta al cliente
     * @throws ServletException Si hay error en la lógica del servlet
     * @throws IOException      Si hay error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar tipo de respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter escritor = response.getWriter()) {
            
            // Obtener todas las competencias
            List<Competencia> competenciasActuales = AdministradorDatos.cargarTodasLasCompetencias();
            
            // Crear un arreglo JSON
            JSONArray arregloJSON = new JSONArray();
            
            // Recorrer cada competencia y agregarla al arreglo
            for (Competencia competencia : competenciasActuales) {
                JSONObject competenciaJSON = new JSONObject();
                competenciaJSON.put("id", competencia.getIdentificador());
                competenciaJSON.put("nombre", competencia.getDenominacion());
                competenciaJSON.put("nivel", competencia.getGradoDominacion());
                competenciaJSON.put("color", competencia.getColorAsociado());
                
                arregloJSON.put(competenciaJSON);
            }
            
            // Escribir el JSON en la respuesta
            escritor.write(arregloJSON.toString());
            escritor.flush();
            
        } catch (Exception excepcion) {
            // Si hay error, enviar respuesta de error en JSON
            enviarRespuestaError(response, "Error al obtener competencias: " + excepcion.getMessage());
        }
    }
    
    /**
     * Maneja peticiones POST que modifican las competencias.
     * Soporta las acciones: agregar, actualizar, eliminar.
     * 
     * Parámetros esperados:
     * - accion: "agregar", "actualizar" o "eliminar"
     * - nombre: Nombre de la competencia (para agregar/actualizar)
     * - nivel: Nivel de dominio (para agregar/actualizar)
     * - color: Color HEX (para agregar/actualizar)
     * - id: ID de la competencia (para actualizar/eliminar)
     * 
     * @param request  Objeto que contiene información de la petición
     * @param response Objeto para enviar la respuesta al cliente
     * @throws ServletException Si hay error en la lógica del servlet
     * @throws IOException      Si hay error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configurar tipo de respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener el parámetro "accion" que indica qué hacer
            String tipoAccion = request.getParameter("accion");
            
            if (tipoAccion == null || tipoAccion.isEmpty()) {
                enviarRespuestaError(response, "El parámetro 'accion' es requerido");
                return;
            }
            
            // Procesar según la acción
            switch (tipoAccion) {
                case "agregar":
                    procesarAgregarCompetencia(request, response);
                    break;
                case "actualizar":
                    procesarActualizarCompetencia(request, response);
                    break;
                case "eliminar":
                    procesarEliminarCompetencia(request, response);
                    break;
                default:
                    enviarRespuestaError(response, "Acción no reconocida: " + tipoAccion);
            }
            
        } catch (Exception excepcion) {
            enviarRespuestaError(response, "Error procesando acción: " + excepcion.getMessage());
        }
    }
    
    /**
     * Procesa la acción de agregar una nueva competencia.
     * 
     * @param request  Objeto de petición
     * @param response Objeto de respuesta
     * @throws IOException Si hay error de I/O
     */
    private void procesarAgregarCompetencia(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Obtener parámetros del formulario
        String nombreCompetencia = request.getParameter("nombre");
        String nivelCompetencia = request.getParameter("nivel");
        String colorCompetencia = request.getParameter("color");
        
        // Validar que no estén vacíos
        if (nombreCompetencia == null || nombreCompetencia.trim().isEmpty() ||
            nivelCompetencia == null || nivelCompetencia.trim().isEmpty() ||
            colorCompetencia == null || colorCompetencia.trim().isEmpty()) {
            
            enviarRespuestaError(response, "Todos los campos son requeridos (nombre, nivel, color)");
            return;
        }
        
        // Crear nueva competencia (ID se genera automáticamente)
        Competencia nuevaCompetencia = new Competencia(
            null,  // ID null, lo genera el gestor
            nombreCompetencia.trim(),
            nivelCompetencia.trim(),
            colorCompetencia.trim()
        );
        
        // Intentar agregar
        boolean exitoAgregar = AdministradorDatos.agregarNuevaCompetencia(nuevaCompetencia);
        
        if (exitoAgregar) {
            enviarRespuestaExito(response, "Competencia agregada exitosamente");
        } else {
            enviarRespuestaError(response, "Error al agregar la competencia");
        }
    }
    
    /**
     * Procesa la acción de actualizar una competencia existente.
     * 
     * @param request  Objeto de petición
     * @param response Objeto de respuesta
     * @throws IOException Si hay error de I/O
     */
    private void procesarActualizarCompetencia(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Obtener parámetros
        String idText = request.getParameter("id");
        String nombreCompetencia = request.getParameter("nombre");
        String nivelCompetencia = request.getParameter("nivel");
        String colorCompetencia = request.getParameter("color");
        
        // Validar que ID esté presente y sea un número
        if (idText == null || idText.trim().isEmpty()) {
            enviarRespuestaError(response, "El ID de la competencia es requerido");
            return;
        }
        
        Integer idCompetencia;
        try {
            idCompetencia = Integer.parseInt(idText);
        } catch (NumberFormatException excepcion) {
            enviarRespuestaError(response, "El ID debe ser un número válido");
            return;
        }
        
        // Validar otros campos
        if (nombreCompetencia == null || nombreCompetencia.trim().isEmpty() ||
            nivelCompetencia == null || nivelCompetencia.trim().isEmpty() ||
            colorCompetencia == null || colorCompetencia.trim().isEmpty()) {
            
            enviarRespuestaError(response, "Todos los campos son requeridos");
            return;
        }
        
        // Crear competencia actualizada
        Competencia competenciaActualizada = new Competencia(
            idCompetencia,
            nombreCompetencia.trim(),
            nivelCompetencia.trim(),
            colorCompetencia.trim()
        );
        
        // Intentar actualizar
        boolean exitoActualizar = AdministradorDatos.actualizarCompetenciaExistente(competenciaActualizada);
        
        if (exitoActualizar) {
            enviarRespuestaExito(response, "Competencia actualizada exitosamente");
        } else {
            enviarRespuestaError(response, "Error al actualizar la competencia (ID no encontrado?)");
        }
    }
    
    /**
     * Procesa la acción de eliminar una competencia.
     * 
     * @param request  Objeto de petición
     * @param response Objeto de respuesta
     * @throws IOException Si hay error de I/O
     */
    private void procesarEliminarCompetencia(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Obtener parámetro ID
        String idText = request.getParameter("id");
        
        if (idText == null || idText.trim().isEmpty()) {
            enviarRespuestaError(response, "El ID de la competencia es requerido");
            return;
        }
        
        Integer idCompetencia;
        try {
            idCompetencia = Integer.parseInt(idText);
        } catch (NumberFormatException excepcion) {
            enviarRespuestaError(response, "El ID debe ser un número válido");
            return;
        }
        
        // Intentar eliminar
        boolean exitoEliminar = AdministradorDatos.eliminarCompetenciaPorIdentificador(idCompetencia);
        
        if (exitoEliminar) {
            enviarRespuestaExito(response, "Competencia eliminada exitosamente");
        } else {
            enviarRespuestaError(response, "Error al eliminar la competencia (ID no encontrado?)");
        }
    }
    
    /**
     * Método auxiliar para enviar respuesta exitosa en JSON.
     * 
     * @param response Objeto de respuesta HTTP
     * @param mensaje  Mensaje a enviar
     * @throws IOException Si hay error de I/O
     */
    private void enviarRespuestaExito(HttpServletResponse response, String mensaje) throws IOException {
        JSONObject respuestaJSON = new JSONObject();
        respuestaJSON.put("exito", true);
        respuestaJSON.put("mensaje", mensaje);
        
        try (PrintWriter escritor = response.getWriter()) {
            escritor.write(respuestaJSON.toString());
            escritor.flush();
        }
    }
    
    /**
     * Método auxiliar para enviar respuesta de error en JSON.
     * 
     * @param response Objeto de respuesta HTTP
     * @param mensaje  Mensaje de error a enviar
     * @throws IOException Si hay error de I/O
     */
    private void enviarRespuestaError(HttpServletResponse response, String mensaje) throws IOException {
        JSONObject respuestaJSON = new JSONObject();
        respuestaJSON.put("exito", false);
        respuestaJSON.put("mensaje", mensaje);
        
        try (PrintWriter escritor = response.getWriter()) {
            escritor.write(respuestaJSON.toString());
            escritor.flush();
        }
    }
    
    /**
     * Retorna la descripción del servlet.
     * 
     * @return Descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet controlador que maneja CRUD de competencias del usuario";
    }
}
