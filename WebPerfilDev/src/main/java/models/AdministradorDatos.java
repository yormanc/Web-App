package models;

import jakarta.servlet.ServletContext;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class AdministradorDatos {
    
    private static String RUTA_ARCHIVOS;
    private static final String ARCHIVO_USUARIO = "usuarioBase.json";
    private static final String ARCHIVO_COMPETENCIAS = "competenciasBase.json";
    
    public static void inicializarRuta(ServletContext context) {
        RUTA_ARCHIVOS = context.getRealPath("/data/");
        System.out.println(" Ruta de datos inicializada: " + RUTA_ARCHIVOS);
    }
    
    public static void inicializarArchivosJSONPorDefecto() {
        try {
            if (RUTA_ARCHIVOS == null) {
                System.out.println(" Error: Ruta no inicializada");
                return;
            }
            
            String rutaUsuario = RUTA_ARCHIVOS + ARCHIVO_USUARIO;
            String rutaCompetencias = RUTA_ARCHIVOS + ARCHIVO_COMPETENCIAS;
            
            Files.createDirectories(Paths.get(RUTA_ARCHIVOS));
            System.out.println(" Directorio verificado: " + RUTA_ARCHIVOS);
            
            if (!Files.exists(Paths.get(rutaUsuario))) {
                String jsonUsuario = "{\"nombreCompleto\":\"Tu Nombre\",\"descripcionPersonal\":\"Desarrollador Web Full Stack\",\"correoContacto\":\"email@ejemplo.com\",\"numeroTelefono\":\"+57 300 123 4567\",\"experienciaAnosText\":\"3 años\",\"urlFotoPerfil\":\"https://ui-avatars.com/api/?name=Tu+Nombre&background=667eea&color=fff&size=200\"}";
                Files.write(Paths.get(rutaUsuario), jsonUsuario.getBytes("UTF-8"));
                System.out.println(" Archivo usuarioBase.json creado");
            }
            
            if (!Files.exists(Paths.get(rutaCompetencias))) {
                String jsonCompetencias = "[{\"identificador\":1,\"denominacion\":\"JavaScript\",\"gradoDominacion\":\"Avanzado\",\"colorAsociado\":\"#F7DF1E\"},{\"identificador\":2,\"denominacion\":\"Java\",\"gradoDominacion\":\"Intermedio\",\"colorAsociado\":\"#007396\"},{\"identificador\":3,\"denominacion\":\"HTML/CSS\",\"gradoDominacion\":\"Avanzado\",\"colorAsociado\":\"#E34C26\"}]";
                Files.write(Paths.get(rutaCompetencias), jsonCompetencias.getBytes("UTF-8"));
                System.out.println("✓ Archivo competenciasBase.json creado");
            }
            
            System.out.println(" Archivos JSON inicializados correctamente");
        } catch (Exception e) {
            System.out.println(" Error inicializando archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Usuario cargarDatosUsuario() {
        try {
            if (RUTA_ARCHIVOS == null) {
                System.out.println(" Ruta no inicializada");
                return obtenerUsuarioDefault();
            }
            
            String rutaCompleta = RUTA_ARCHIVOS + ARCHIVO_USUARIO;
            System.out.println("Cargando usuario desde: " + rutaCompleta);
            
            if (!Files.exists(Paths.get(rutaCompleta))) {
                System.out.println("️ Archivo no existe, usando datos por defecto");
                return obtenerUsuarioDefault();
            }
            
            String contenido = new String(Files.readAllBytes(Paths.get(rutaCompleta)), "UTF-8");
            
            String nombre = extraerValor(contenido, "nombreCompleto");
            String descripcion = extraerValor(contenido, "descripcionPersonal");
            String email = extraerValor(contenido, "correoContacto");
            String telefono = extraerValor(contenido, "numeroTelefono");
            String experiencia = extraerValor(contenido, "experienciaAnosText");
            String foto = extraerValor(contenido, "urlFotoPerfil");
            
            System.out.println(" Usuario cargado: " + nombre);
            return new Usuario(nombre, descripcion, email, telefono, experiencia, foto);
        } catch (Exception e) {
            System.out.println(" Error cargando usuario: " + e.getMessage());
            e.printStackTrace();
            return obtenerUsuarioDefault();
        }
    }
    
    public static List<Competencia> cargarTodasLasCompetencias() {
        List<Competencia> competencias = new ArrayList<>();
        try {
            if (RUTA_ARCHIVOS == null) {
                System.out.println(" Ruta no inicializada");
                return competencias;
            }
            
            String rutaCompleta = RUTA_ARCHIVOS + ARCHIVO_COMPETENCIAS;
            System.out.println("Cargando competencias desde: " + rutaCompleta);
            
            if (!Files.exists(Paths.get(rutaCompleta))) {
                System.out.println("️ Archivo no existe, retornando lista vacía");
                return competencias;
            }
            
            String contenido = new String(Files.readAllBytes(Paths.get(rutaCompleta)), "UTF-8");
            
            Pattern pattern = Pattern.compile("\\{[^}]*\"identificador\"[^}]*\\}");
            Matcher matcher = pattern.matcher(contenido);
            
            while (matcher.find()) {
                String obj = matcher.group();
                try {
                    Integer id = Integer.parseInt(extraerNumero(obj, "identificador"));
                    String denominacion = extraerValor(obj, "denominacion");
                    String nivel = extraerValor(obj, "gradoDominacion");
                    String color = extraerValor(obj, "colorAsociado");
                    
                    competencias.add(new Competencia(id, denominacion, nivel, color));
                } catch (Exception ex) {
                    System.out.println("⚠️ Error parseando competencia: " + ex.getMessage());
                }
            }
            
            System.out.println(" Competencias cargadas: " + competencias.size());
        } catch (Exception e) {
            System.out.println(" Error cargando competencias: " + e.getMessage());
            e.printStackTrace();
        }
        return competencias;
    }
    
    public static void guardarDatosUsuario(Usuario usuario) {
        try {
            if (RUTA_ARCHIVOS == null) {
                System.out.println(" Ruta no inicializada");
                return;
            }
            
            String rutaCompleta = RUTA_ARCHIVOS + ARCHIVO_USUARIO;
            String json = String.format(
                "{\"nombreCompleto\":\"%s\",\"descripcionPersonal\":\"%s\",\"correoContacto\":\"%s\",\"numeroTelefono\":\"%s\",\"experienciaAnosText\":\"%s\",\"urlFotoPerfil\":\"%s\"}",
                escaparJson(usuario.getNombreCompleto()),
                escaparJson(usuario.getDescripcionPersonal()),
                escaparJson(usuario.getCorreoContacto()),
                escaparJson(usuario.getNumeroTelefono()),
                escaparJson(usuario.getExperienciaAnosText()),
                escaparJson(usuario.getUrlFotoPerfil())
            );
            Files.write(Paths.get(rutaCompleta), json.getBytes("UTF-8"));
            System.out.println(" Usuario guardado exitosamente");
        } catch (Exception e) {
            System.out.println(" Error guardando usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void guardarCompetencias(List<Competencia> competencias) {
        try {
            if (RUTA_ARCHIVOS == null) {
                System.out.println(" Ruta no inicializada");
                return;
            }
            
            String rutaCompleta = RUTA_ARCHIVOS + ARCHIVO_COMPETENCIAS;
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < competencias.size(); i++) {
                Competencia c = competencias.get(i);
                json.append(String.format(
                    "{\"identificador\":%d,\"denominacion\":\"%s\",\"gradoDominacion\":\"%s\",\"colorAsociado\":\"%s\"}",
                    c.getIdentificador(),
                    escaparJson(c.getDenominacion()),
                    escaparJson(c.getGradoDominacion()),
                    escaparJson(c.getColorAsociado())
                ));
                if (i < competencias.size() - 1) json.append(",");
            }
            json.append("]");
            Files.write(Paths.get(rutaCompleta), json.toString().getBytes("UTF-8"));
            System.out.println(" Competencias guardadas exitosamente");
        } catch (Exception e) {
            System.out.println(" Error guardando competencias: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Usuario obtenerUsuarioDefault() {
        return new Usuario(
            "Tu Nombre", 
            "Desarrollador Web Full Stack", 
            "email@ejemplo.com", 
            "+57 300 123 4567", 
            "3 años", 
            "https://ui-avatars.com/api/?name=Tu+Nombre&background=667eea&color=fff&size=200"
        );
    }
    
    private static String extraerValor(String json, String clave) {
        try {
            String patron = "\"" + clave + "\"\\s*:\\s*\"([^\"]*)\"";
            Pattern p = Pattern.compile(patron);
            Matcher m = p.matcher(json);
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error extrayendo valor para clave: " + clave);
        }
        return "";
    }
    
    private static String extraerNumero(String json, String clave) {
        try {
            String patron = "\"" + clave + "\"\\s*:\\s*(\\d+)";
            Pattern p = Pattern.compile(patron);
            Matcher m = p.matcher(json);
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error extrayendo número para clave: " + clave);
        }
        return "0";
    }
    
    private static String escaparJson(String str) {
        if (str == null) return "";
        return str
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r");
    }
}
