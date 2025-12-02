package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.AdministradorDatos;
import models.Usuario;

import java.io.IOException;
import java.util.List;

/**
 * Servlet que actúa como controlador principal de la aplicación.
 * Se encarga de servir la página inicial con los datos del usuario.
 * 
 * URL de acceso: /WebPerfilDev-1.0-SNAPSHOT/
 * 
 * @author Proyecto WebPerfilDev
 * @version 1.0
 */
@WebServlet(name = "ControladorPrincipal", urlPatterns = { "/" })
public class ControladorPrincipal extends HttpServlet {
    
    /**
     * Maneja las peticiones GET (cuando el usuario abre la URL en el navegador).
     * 
     * @param request  Objeto que contiene información de la petición
     * @param response Objeto para enviar la respuesta al cliente
     * @throws ServletException Si hay error en la lógica del servlet
     * @throws IOException      Si hay error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Inicializar archivos JSON si no existen (primera ejecución)
        AdministradorDatos.inicializarArchivosJSONPorDefecto();
        
        // Cargar datos del usuario desde el archivo JSON
        Usuario usuarioActual = AdministradorDatos.cargarDatosUsuario();
        
        // Cargar todas las competencias desde el archivo JSON
        models.Competencia[] arregloCompetencias = 
            AdministradorDatos.cargarTodasLasCompetencias().toArray(new models.Competencia[0]);
        
        // Guardar los datos en el request para que la vista (JSP) pueda acceder
        request.setAttribute("usuarioEnSesion", usuarioActual);
        request.setAttribute("competenciasDelUsuario", arregloCompetencias);
        request.setAttribute("totalCompetenciasActuales", arregloCompetencias.length);
        
        // Redirigir la petición a la vista index.jsp
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    /**
     * Maneja peticiones POST (cuando se envía un formulario).
     * Por ahora, redirige a doGet para simplificar.
     * 
     * @param request  Objeto que contiene información de la petición
     * @param response Objeto para enviar la respuesta al cliente
     * @throws ServletException Si hay error en la lógica del servlet
     * @throws IOException      Si hay error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Retorna la descripción del servlet.
     * Esta es una buena práctica en Java.
     * 
     * @return Descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet controlador principal que carga el perfil del usuario";
    }
}
