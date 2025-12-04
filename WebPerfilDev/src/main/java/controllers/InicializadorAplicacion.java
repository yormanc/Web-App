package controllers;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import models.AdministradorDatos;

@WebListener
public class InicializadorAplicacion implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("========================================");
        System.out.println("INICIANDO APLICACION WEB PERFIL DEV");
        System.out.println("========================================");
        
        try {
            AdministradorDatos.inicializarRuta(sce.getServletContext());
            AdministradorDatos.inicializarArchivosJSONPorDefecto();
            System.out.println("Verificando archivos creados...");
            models.Usuario u = AdministradorDatos.cargarDatosUsuario();
            System.out.println("Usuario cargado: " + u.getNombreCompleto());
            java.util.List<models.Competencia> comps = AdministradorDatos.cargarTodasLasCompetencias();
            System.out.println("Competencias cargadas: " + comps.size()); 
            System.out.println("Aplicacion inicializada correctamente");
            System.out.println("========================================");
        } catch (Exception e) {
            System.err.println("ERROR EN INICIALIZACION");
            e.printStackTrace();
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicacion detenida");
    }
}
