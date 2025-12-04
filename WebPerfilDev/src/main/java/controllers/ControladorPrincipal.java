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
    public void init() throws ServletException {
        super.init();
        System.out.println(" ControladorPrincipal inicializado");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== GET " + request.getRequestURI() + " ===");
        
        try {
            Usuario usuarioActual = AdministradorDatos.cargarDatosUsuario();
            List<Competencia> listaCompetencias = AdministradorDatos.cargarTodasLasCompetencias();
            Competencia[] arregloCompetencias = listaCompetencias.toArray(new Competencia[0]);
            request.setAttribute("usuarioEnSesion", usuarioActual);
            request.setAttribute("competenciasDelUsuario", arregloCompetencias);
            request.setAttribute("totalCompetenciasActuales", arregloCompetencias.length);
            System.out.println("Datos enviados al JSP - Usuario: " + usuarioActual.getNombreCompleto() + ", Competencias: " + arregloCompetencias.length);
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error en doGet: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String accion = request.getParameter("accion");
        System.out.println("=== POST / === Acción: " + accion);
        
        try {
            if ("actualizarPerfil".equals(accion)) {
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                String email = request.getParameter("email");
                String telefono = request.getParameter("telefono");
                String experiencia = request.getParameter("experiencia");
                String foto = request.getParameter("foto");
                
                System.out.println("Actualizando perfil: " + nombre);
                
                Usuario usuarioActualizado = new Usuario(nombre, descripcion, email, telefono, experiencia, foto);
                AdministradorDatos.guardarDatosUsuario(usuarioActualizado);
                
                response.setContentType("text/plain");
                response.getWriter().print("OK");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("Acción desconocida");
            }
        } catch (Exception e) {
            System.err.println("Error en doPost: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("ERROR: " + e.getMessage());
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Controlador principal de la aplicación";
    }
}
