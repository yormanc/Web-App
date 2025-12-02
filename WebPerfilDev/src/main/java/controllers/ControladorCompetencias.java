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

@WebServlet(name = "ControladorCompetencias", urlPatterns = { "/competencias" })
public class ControladorCompetencias extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if ("agregarCompetencia".equals(accion)) {
            String denominacion = request.getParameter("denominacion");
            String gradoDominacion = request.getParameter("gradoDominacion");
            String colorAsociado = request.getParameter("colorAsociado");
            
            // Obtener el siguiente ID
            List<Competencia> competencias = AdministradorDatos.cargarTodasLasCompetencias();
            Integer nuevoId = competencias.size() + 1;
            
            // Crear y guardar
            Competencia nueva = new Competencia(nuevoId, denominacion, gradoDominacion, colorAsociado);
            competencias.add(nueva);
            AdministradorDatos.guardarCompetencias(competencias);
            
            response.getWriter().print("OK");
        } 
        else if ("modificarCompetencia".equals(accion)) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            String denominacion = request.getParameter("denominacion");
            String gradoDominacion = request.getParameter("gradoDominacion");
            String colorAsociado = request.getParameter("colorAsociado");
            
            // Buscar y actualizar
            List<Competencia> competencias = AdministradorDatos.cargarTodasLasCompetencias();
            for (Competencia c : competencias) {
                if (c.getIdentificador().equals(id)) {
                    c.setDenominacion(denominacion);
                    c.setGradoDominacion(gradoDominacion);
                    c.setColorAsociado(colorAsociado);
                    break;
                }
            }
            AdministradorDatos.guardarCompetencias(competencias);
            response.getWriter().print("OK");
        }
        else if ("eliminarCompetencia".equals(accion)) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            
            // Eliminar
            List<Competencia> competencias = AdministradorDatos.cargarTodasLasCompetencias();
            competencias.removeIf(c -> c.getIdentificador().equals(id));
            AdministradorDatos.guardarCompetencias(competencias);
            
            response.getWriter().print("OK");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Controlador de competencias";
    }
}