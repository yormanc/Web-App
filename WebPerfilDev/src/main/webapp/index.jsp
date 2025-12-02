<%-- 
    Document   : index.jsp
    Created on : 29/11/2025, 6:06:35 p. m.
    Author     : WINDOWS 11
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Usuario" %>
<%@ page import="models.Competencia" %>

<%
    // Obtener datos que el Servlet envió
    Usuario perfil = (Usuario) request.getAttribute("usuarioEnSesion");
    Competencia[] capacidadesTecnicas = (Competencia[]) request.getAttribute("competenciasDelUsuario");
    Integer cantidadCapacidades = (Integer) request.getAttribute("totalCompetenciasActuales");
    
    // Si no hay datos, usar valores por defecto
    if (perfil == null) {
        perfil = new Usuario("Cargando...", "Espera...", "email@ejemplo.com", "", "", "");
    }
    if (capacidadesTecnicas == null) {
        capacidadesTecnicas = new Competencia[0];
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= perfil.getNombreCompleto() %> - Mi Portafolio</title>
    <link rel="stylesheet" href="css/estilosBase.css">
    <link rel="stylesheet" href="css/mobileStyles.css">
</head>
<body>
    <div class="contenedorPrincipal">
        
        <!-- ENCABEZADO CON BANNER Y PERFIL -->
        <header class="seccionEncabezado">
            
            <!-- BANNER CON GRADIENTE -->
            <div class="bannerGradiente" id="bannerPrincipal">
                <button class="botonEditarBanner" onclick="mostrarEditorBanner()">
                    ✏️ Cambiar Banner
                </button>
            </div>
            
            <!-- INFORMACIÓN DEL PERFIL -->
            <div class="perfilUsuario">
                <img src="<%= perfil.getUrlFotoPerfil() %>" 
                     alt="Foto de perfil" 
                     class="fotoPerfil"
                     onerror="this.src='https://ui-avatars.com/api/?name=Desarrollador&background=667eea&color=fff'">
                
                <div class="infoPerfil">
                    <h1><%= perfil.getNombreCompleto() %></h1>
                    <p class="bioPerfil"><%= perfil.getDescripcionPersonal() %></p>
                    
                    <div class="detallesContacto">
                        <span class="detalle">📧 <%= perfil.getCorreoContacto() %></span>
                        <span class="detalle">📱 <%= perfil.getNumeroTelefono() %></span>
                        <span class="detalle">💼 <%= perfil.getExperienciaAnosText() %></span>
                    </div>
                </div>
                
                <button class="botonEditarPerfil" onclick="abrirDialogoEditarPerfil()">
                    ✏️ Editar Perfil
                </button>
            </div>
        </header>

        <!-- SECCIÓN DE CAPACIDADES TÉCNICAS -->
        <section class="seccionCapacidades">
            <div class="cabeceraCapacidades">
                <h2>Mis Capacidades Técnicas</h2>
                <button class="botonPrimario" onclick="abrirDialogoAgregarCapacidad()">
                    Agregar Capacidad
                </button>
            </div>

            <div class="rejillaCapacidades" id="contenedorCapacidades">
                <% 
                    if (capacidadesTecnicas != null && capacidadesTecnicas.length > 0) {
                        for (Competencia tecn : capacidadesTecnicas) {
                %>
                    <div class="tarjetaCapacidad" style="border-left-color: <%= tecn.getColorAsociado() %>;">
                        <div class="encabezadoTarjeta">
                            <h3><%= tecn.getDenominacion() %></h3>
                            <span class="etiquetaNivel <%= tecn.getGradoDominacion().toLowerCase().replace(" ", "") %>">
                                <%= tecn.getGradoDominacion() %>
                            </span>
                        </div>
                        
                        <div class="accionesTarjeta">
                            <button class="botonChico botonSecundario" 
                                    onclick="abrirDialogoEditarCapacidad(<%= tecn.getIdentificador() %>, '<%= tecn.getDenominacion() %>', '<%= tecn.getGradoDominacion() %>', '<%= tecn.getColorAsociado() %>')">
                                Editar
                            </button>
                            <button class="botonChico botonDanger" 
                                    onclick="confirmarEliminacion(<%= tecn.getIdentificador() %>)">
                                Eliminar
                            </button>
                        </div>
                    </div>
                <% 
                        }
                    } else {
                %>
                    <div class="mensajeVacio">
                        <p>No hay capacidades registradas. ¡Agrega tu primera capacidad!</p>
                    </div>
                <% 
                    }
                %>
            </div>
        </section>

        <!-- PIE DE PÁGINA -->
        <footer class="piePagina">
            <p>© 2025 Mi Portafolio Profesional - Desarrollado con Java y Servlets</p>
        </footer>
    </div>

    <!-- MODAL: EDITAR PERFIL -->
    <div id="modalEditarPerfil" class="modal" style="display: none;">
        <div class="contenidoModal">
            <div class="cabeceraModal">
                <h2>Editar Información Personal</h2>
                <button class="botonCerrarModal" onclick="cerrarModal('modalEditarPerfil')">&times;</button>
            </div>
            
            <form id="formularioEditarPerfil" class="formulario">
                <div class="grupoFormulario">
                    <label>Tu Nombre Completo:</label>
                    <input type="text" id="inputNombre" value="<%= perfil.getNombreCompleto() %>" required>
                </div>
                
                <div class="grupoFormulario">
                    <label>Acerca de ti (Biografía):</label>
                    <textarea id="inputBio" required><%= perfil.getDescripcionPersonal() %></textarea>
                </div>
                
                <div class="grupoFormulario">
                    <label>Email:</label>
                    <input type="email" id="inputEmail" value="<%= perfil.getCorreoContacto() %>" required>
                </div>
                
                <div class="grupoFormulario">
                    <label>Teléfono:</label>
                    <input type="tel" id="inputTelefono" value="<%= perfil.getNumeroTelefono() %>">
                </div>
                
                <div class="grupoFormulario">
                    <label>Años de Experiencia:</label>
                    <input type="text" id="inputExperiencia" value="<%= perfil.getExperienciaAnosText() %>">
                </div>
                
                <div class="grupoFormulario">
                    <label>URL de tu Foto de Perfil:</label>
                    <input type="url" id="inputFoto" value="<%= perfil.getUrlFotoPerfil() %>">
                </div>
                
                <div class="pieModal">
                    <button type="submit" class="botonPrimario">Guardar Cambios</button>
                    <button type="button" class="botonSecundario" onclick="cerrarModal('modalEditarPerfil')">
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- MODAL: AGREGAR CAPACIDAD -->
    <div id="modalAgregarCapacidad" class="modal" style="display: none;">
        <div class="contenidoModal">
            <div class="cabeceraModal">
                <h2>Agregar Nueva Capacidad</h2>
                <button class="botonCerrarModal" onclick="cerrarModal('modalAgregarCapacidad')">&times;</button>
            </div>
            
            <form id="formularioAgregarCapacidad" class="formulario">
                <div class="grupoFormulario">
                    <label>Nombre de la Tecnología:</label>
                    <input type="text" id="inputNombreTecnologia" placeholder="Ej: React, Docker, MySQL" required>
                </div>
                
                <div class="grupoFormulario">
                    <label>Nivel de Dominio:</label>
                    <select id="inputNivelTecnologia" required>
                        <option value="">-- Selecciona un nivel --</option>
                        <option value="Básico">Básico</option>
                        <option value="Intermedio">Intermedio</option>
                        <option value="Avanzado">Avanzado</option>
                        <option value="Experto">Experto</option>
                    </select>
                </div>
                
                <div class="grupoFormulario">
                    <label>Color de la Tarjeta (Código HEX):</label>
                    <input type="color" id="inputColorTecnologia" value="#667eea">
                </div>
                
                <div class="pieModal">
                    <button type="submit" class="botonPrimario">Agregar</button>
                    <button type="button" class="botonSecundario" onclick="cerrarModal('modalAgregarCapacidad')">
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- MODAL: EDITAR CAPACIDAD -->
    <div id="modalEditarCapacidad" class="modal" style="display: none;">
        <div class="contenidoModal">
            <div class="cabeceraModal">
                <h2>Editar Capacidad</h2>
                <button class="botonCerrarModal" onclick="cerrarModal('modalEditarCapacidad')">&times;</button>
            </div>
            
            <form id="formularioEditarCapacidad" class="formulario">
                <input type="hidden" id="inputIdCapacidad">
                
                <div class="grupoFormulario">
                    <label>Nombre de la Tecnología:</label>
                    <input type="text" id="inputNombreTecnologiaEdit" required>
                </div>
                
                <div class="grupoFormulario">
                    <label>Nivel de Dominio:</label>
                    <select id="inputNivelTecnologiaEdit" required>
                        <option value="Básico">Básico</option>
                        <option value="Intermedio">Intermedio</option>
                        <option value="Avanzado">Avanzado</option>
                        <option value="Experto">Experto</option>
                    </select>
                </div>
                
                <div class="grupoFormulario">
                    <label>Color de la Tarjeta:</label>
                    <input type="color" id="inputColorTecnologiaEdit">
                </div>
                
                <div class="pieModal">
                    <button type="submit" class="botonPrimario">Actualizar</button>
                    <button type="button" class="botonSecundario" onclick="cerrarModal('modalEditarCapacidad')">
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- SCRIPT LÓGICA -->
    <script src="js/logicaPrincipal.js"></script>
</body>
</html>
