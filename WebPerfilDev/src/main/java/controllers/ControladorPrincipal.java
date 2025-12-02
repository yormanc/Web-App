package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.AdministradorDatos;
import models.Usuario;
import models.Competencia;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorPrincipal", urlPatterns = { "/" })
public class ControladorPrincipal extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Cargar datos del usuario
        Usuario usuarioActual = AdministradorDatos.cargarDatosUsuario();
        if (usuarioActual == null) {
            usuarioActual = new Usuario("Cargando...", "Espera...", "email@ejemplo.com", "", "", "");
        }
        
        // Cargar competencias
        List<Competencia> listaCompetencias = AdministradorDatos.cargarTodasLasCompetencias();
        Competencia[] arregloCompetencias = listaCompetencias.toArray(new Competencia[0]);
        
        // Enviar datos a la vista
        request.setAttribute("usuarioEnSesion", usuarioActual);
        request.setAttribute("competenciasDelUsuario", arregloCompetencias);
        request.setAttribute("totalCompetenciasActuales", arregloCompetencias.length);
        
        // Ir a index.jsp
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if ("actualizarPerfil".equals(accion)) {
            // Actualizar datos del perfil
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            String experiencia = request.getParameter("experiencia");
            String foto = request.getParameter("foto");
            
            Usuario usuarioActualizado = new Usuario(nombre, descripcion, email, telefono, experiencia, foto);
            AdministradorDatos.guardarDatosUsuario(usuarioActualizado);
        }
        
        // Redirigir de vuelta
        doGet(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Controlador principal de la aplicación";
    }
}