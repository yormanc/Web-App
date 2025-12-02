<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Usuario" %>
<%@ page import="models.Competencia" %>

<%!
    // Método auxiliar para obtener el color del badge
    public String obtenerColorBadge(String nivel) {
        if (nivel == null) return "secondary";
        switch(nivel.toLowerCase()) {
            case "básico":
            case "basico":
                return "info";
            case "intermedio":
                return "warning";
            case "avanzado":
                return "success";
            case "experto":
                return "danger";
            default:
                return "secondary";
        }
    }
%>

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
    <title><%= perfil.getNombreCompleto() %> - Portafolio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/estilosBase.css">
    <link rel="stylesheet" href="css/mobileStyles.css">
</head>
<body>
    <!-- NAVBAR -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <span class="navbar-brand mb-0 h1"><%= perfil.getNombreCompleto() %></span>
            <button class="btn btn-light" onclick="abrirDialogoEditarPerfil()">Editar Perfil</button>
        </div>
    </nav>

    <!-- HERO SECTION -->
    <header class="hero-section">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-4 text-center">
                    <img src="<%= perfil.getUrlFotoPerfil() %>" alt="<%= perfil.getNombreCompleto() %>" class="rounded-circle img-fluid" width="200">
                </div>
                <div class="col-md-8">
                    <h1><%= perfil.getNombreCompleto() %></h1>
                    <p class="lead"><%= perfil.getDescripcionPersonal() %></p>
                    <p>
                        <strong>Email:</strong> <%= perfil.getCorreoContacto() %><br>
                        <strong>Teléfono:</strong> <%= perfil.getNumeroTelefono() %><br>
                        <strong>Experiencia:</strong> <%= perfil.getExperienciaAnosText() %>
                    </p>
                </div>
            </div>
        </div>
    </header>

    <!-- CAPACIDADES TÉCNICAS -->
    <section class="capacidades-section">
        <div class="container">
            <h2>Mis Capacidades Técnicas</h2>
            <button class="btn btn-success mb-4" onclick="abrirDialogoAgregarCapacidad()">+ Agregar Capacidad</button>

            <% if (capacidadesTecnicas.length > 0) { %>
                <div class="row">
                    <% for (Competencia c : capacidadesTecnicas) { %>
                        <div class="col-md-4 mb-3">
                            <div class="card capacidad-card" style="border-left: 4px solid <%= c.getColorAsociado() %>">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h5 class="card-title text-primary"><%= c.getDenominacion() %></h5>
                                        <span class="badge bg-<%= obtenerColorBadge(c.getGradoDominacion()) %>">
                                            <%= c.getGradoDominacion() %>
                                        </span>
                                    </div>
                                    <button class="btn btn-sm btn-primary" onclick="abrirDialogoEditarCapacidad(<%= c.getIdentificador() %>, '<%= c.getDenominacion() %>', '<%= c.getGradoDominacion() %>', '<%= c.getColorAsociado() %>')">Editar</button>
                                    <button class="btn btn-sm btn-danger" onclick="confirmarEliminacion(<%= c.getIdentificador() %>)">Eliminar</button>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <p>No hay capacidades registradas. ¡Agrega tu primera capacidad!</p>
            <% } %>
        </div>
    </section>

    <!-- MODAL: EDITAR PERFIL -->
    <div class="modal fade" id="modalEditarPerfil" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Editar Perfil</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form id="formularioEditarPerfil">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Nombre Completo</label>
                            <input type="text" class="form-control" id="inputNombre" value="<%= perfil.getNombreCompleto() %>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Descripción Personal</label>
                            <textarea class="form-control" id="inputBio" rows="3" required><%= perfil.getDescripcionPersonal() %></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" id="inputEmail" value="<%= perfil.getCorreoContacto() %>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Teléfono</label>
                            <input type="text" class="form-control" id="inputTelefono" value="<%= perfil.getNumeroTelefono() %>">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Experiencia (años)</label>
                            <input type="text" class="form-control" id="inputExperiencia" value="<%= perfil.getExperienciaAnosText() %>">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">URL Foto Perfil</label>
                            <input type="text" class="form-control" id="inputFoto" value="<%= perfil.getUrlFotoPerfil() %>">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- MODAL: AGREGAR CAPACIDAD -->
    <div class="modal fade" id="modalAgregarCapacidad" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Agregar Capacidad</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form id="formularioAgregarCapacidad">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Nombre de la Tecnología</label>
                            <input type="text" class="form-control" id="inputNombreTecnologia" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Nivel de Dominio</label>
                            <select class="form-control" id="inputNivelTecnologia" required>
                                <option value="">Selecciona un nivel</option>
                                <option value="Básico">Básico</option>
                                <option value="Intermedio">Intermedio</option>
                                <option value="Avanzado">Avanzado</option>
                                <option value="Experto">Experto</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Color de la Tarjeta (HEX)</label>
                            <input type="color" class="form-control form-control-color" id="inputColorTecnologia" value="#667eea">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- MODAL: EDITAR CAPACIDAD -->
    <div class="modal fade" id="modalEditarCapacidad" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Editar Capacidad</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form id="formularioEditarCapacidad">
                    <div class="modal-body">
                        <input type="hidden" id="inputIdCapacidad">
                        <div class="mb-3">
                            <label class="form-label">Nombre de la Tecnología</label>
                            <input type="text" class="form-control" id="inputNombreTecnologiaEdit" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Nivel de Dominio</label>
                            <select class="form-control" id="inputNivelTecnologiaEdit" required>
                                <option value="Básico">Básico</option>
                                <option value="Intermedio">Intermedio</option>
                                <option value="Avanzado">Avanzado</option>
                                <option value="Experto">Experto</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Color de la Tarjeta (HEX)</label>
                            <input type="color" class="form-control form-control-color" id="inputColorTecnologiaEdit">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Lógica personalizada -->
    <script src="js/logicaPrincipal.js"></script>
</body>
</html>
