package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.AdministradorDatos;
import models.Competencia;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorCompetencias", urlPatterns = {"/controlador-competencias"})
public class ControladorCompetencias extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        
        String accion = request.getParameter("accion");
        
        System.out.println("=== POST /controlador-competencias ===");
        System.out.println("Acción recibida: " + accion);
        
        try {
            if (accion == null || accion.trim().isEmpty()) {
                System.err.println("ERROR: Acción es NULL o vacía");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ERROR: Acción no especificada");
                return;
            }
            
            switch (accion.trim().toLowerCase()) {
                case "agregar":
                    agregarCapacidad(request, response);
                    break;
                case "editar":
                    editarCapacidad(request, response);
                    break;
                case "eliminar":
                    eliminarCapacidad(request, response);
                    break;
                default:
                    System.err.println("ERROR: Acción desconocida: " + accion);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("ERROR: Acción desconocida: " + accion);
            }
        } catch (Exception e) {
            System.err.println("Error en doPost: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ERROR: " + e.getMessage());
        }
    }
    
    private void agregarCapacidad(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String nombre = request.getParameter("nombreTecnologia");
        String nivel = request.getParameter("nivelTecnologia");
        String color = request.getParameter("colorTecnologia");
        
        System.out.println("Agregando capacidad: " + nombre + " - " + nivel + " - " + color);
        
        if (nombre == null || nivel == null || color == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ERROR: Faltan parámetros");
            return;
        }
        
        try {
            List<Competencia> competencias = AdministradorDatos.cargarTodasLasCompetencias();
            
            // Generar nuevo ID
            int nuevoId = competencias.stream()
                    .mapToInt(Competencia::getIdentificador)
                    .max()
                    .orElse(0) + 1;
            
            Competencia nueva = new Competencia(nuevoId, nombre, nivel, color);
            competencias.add(nueva);
            
            AdministradorDatos.guardarCompetencias(competencias);
            
            System.out.println("Capacidad agregada correctamente: ID " + nuevoId);
            response.getWriter().write("OK");
        } catch (Exception e) {
            System.err.println("Error agregando capacidad: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ERROR: " + e.getMessage());
        }
    }
    
    private void editarCapacidad(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String idStr = request.getParameter("idCapacidad");
        String nombre = request.getParameter("nombreTecnologia");
        String nivel = request.getParameter("nivelTecnologia");
        String color = request.getParameter("colorTecnologia");
        
        System.out.println("Editando capacidad ID: " + idStr);
        
        if (idStr == null || nombre == null || nivel == null || color == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ERROR: Faltan parámetros");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            List<Competencia> competencias = AdministradorDatos.cargarTodasLasCompetencias();
            
            boolean encontrada = false;
            for (int i = 0; i < competencias.size(); i++) {
                if (competencias.get(i).getIdentificador() == id) {
                    competencias.set(i, new Competencia(id, nombre, nivel, color));
                    encontrada = true;
                    break;
                }
            }
            
            if (encontrada) {
                AdministradorDatos.guardarCompetencias(competencias);
                System.out.println("Capacidad editada correctamente: ID " + id);
                response.getWriter().write("OK");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("ERROR: Capacidad no encontrada");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ERROR: ID inválido");
        } catch (Exception e) {
            System.err.println("Error editando capacidad: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ERROR: " + e.getMessage());
        }
    }
    
    private void eliminarCapacidad(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String idStr = request.getParameter("idCapacidad");
        
        System.out.println("Eliminando capacidad ID: " + idStr);
        
        if (idStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ERROR: Falta parámetro idCapacidad");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            List<Competencia> competencias = AdministradorDatos.cargarTodasLasCompetencias();
            
            boolean eliminada = competencias.removeIf(c -> c.getIdentificador() == id);
            
            if (eliminada) {
                AdministradorDatos.guardarCompetencias(competencias);
                System.out.println("✓ Capacidad eliminada correctamente: ID " + id);
                response.getWriter().write("OK");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("ERROR: Capacidad no encontrada");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ERROR: ID inválido");
        } catch (Exception e) {
            System.err.println("Error eliminando capacidad: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ERROR: " + e.getMessage());
        }
    }
}
