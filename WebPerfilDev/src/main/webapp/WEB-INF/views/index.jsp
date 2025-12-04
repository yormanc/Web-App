<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Usuario" %>
<%@ page import="models.Competencia" %>

<%!
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
    Usuario perfil = (Usuario) request.getAttribute("usuarioEnSesion");
    Competencia[] capacidadesTecnicas = (Competencia[]) request.getAttribute("competenciasDelUsuario");
    
    if (perfil == null) {
        perfil = new Usuario("ERROR", "No se cargaron datos", "email@ejemplo.com", "", "", "");
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
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .main-container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .hero-section {
            background: white;
            padding: 40px;
            margin-bottom: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }
        .capacidades-section {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }
        .capacidad-card {
            height: 100%;
            transition: transform 0.3s, box-shadow 0.3s;
            border: none;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .capacidad-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
        }
        .profile-img {
            border: 5px solid #667eea;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            width: 200px;
            height: 200px;
            object-fit: cover;
        }
        .navbar {
            margin-bottom: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
    <div class="main-container">
        <!-- NAVBAR -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <span class="navbar-brand mb-0 h1"> <%= perfil.getNombreCompleto() %></span>
                <button class="btn btn-light" onclick="abrirDialogoEditarPerfil()"> Editar Perfil</button>
            </div>
        </nav>

        <!-- HERO SECTION -->
        <header class="hero-section">
            <div class="row align-items-center">
                <div class="col-md-4 text-center">
                    <img 
                        src="${pageContext.request.contextPath}/<%= perfil.getUrlFotoPerfil() %>" 
                        alt="<%= perfil.getNombreCompleto() %>" 
                        class="rounded-circle img-fluid profile-img"
                        onerror="this.src='https://ui-avatars.com/api/?name=<%= perfil.getNombreCompleto().replace(" ", "+") %>&background=667eea&color=fff&size=200'">

                </div>
                <div class="col-md-8">
                    <h1 class="display-4 text-primary"><%= perfil.getNombreCompleto() %></h1>
                    <p class="lead text-secondary"><%= perfil.getDescripcionPersonal() %></p>
                    <hr>
                    <div class="row">
                        <div class="col-md-6">
                            <p class="mb-2">
                                <strong> Email:</strong> 
                                <a href="mailto:<%= perfil.getCorreoContacto() %>"><%= perfil.getCorreoContacto() %></a>
                            </p>
                            <p class="mb-2">
                                <strong> Teléfono:</strong> <%= perfil.getNumeroTelefono() %>
                            </p>
                        </div>
                        <div class="col-md-6">
                            <p class="mb-2">
                                <strong> Experiencia:</strong> <%= perfil.getExperienciaAnosText() %>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- CAPACIDADES TÉCNICAS -->
        <section class="capacidades-section">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="text-primary mb-0"> Mis Capacidades Técnicas</h2>
                <button class="btn btn-success" onclick="abrirDialogoAgregarCapacidad()">
                     Agregar Capacidad
                </button>
            </div>
            
            <% if (capacidadesTecnicas.length > 0) { %>
                <div class="row">
                    <% for (Competencia c : capacidadesTecnicas) { %>
                        <div class="col-md-4 mb-4">
                            <div class="card capacidad-card" style="border-left: 5px solid <%= c.getColorAsociado() %>">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h5 class="card-title text-primary mb-0">
                                            <%= c.getDenominacion() %>
                                        </h5>
                                        <span class="badge bg-<%= obtenerColorBadge(c.getGradoDominacion()) %> fs-6">
                                            <%= c.getGradoDominacion() %>
                                        </span>
                                    </div>
                                    <div class="btn-group btn-group-sm" role="group">
                                        <button class="btn btn-outline-primary" onclick="abrirDialogoEditarCapacidad(<%= c.getIdentificador() %>, '<%= c.getDenominacion() %>', '<%= c.getGradoDominacion() %>', '<%= c.getColorAsociado() %>')">
                                             Editar
                                        </button>
                                        <button class="btn btn-outline-danger" onclick="confirmarEliminacion(<%= c.getIdentificador() %>)">
                                             Eliminar
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <div class="alert alert-info text-center" role="alert">
                    <h4 class="alert-heading"> No hay capacidades registradas</h4>
                    <p>¡Agrega tu primera capacidad técnica para comenzar!</p>
                    <hr>
                    <button class="btn btn-primary" onclick="abrirDialogoAgregarCapacidad()">
                         Agregar Primera Capacidad
                    </button>
                </div>
            <% } %>
        </section>
    </div>

        <!-- MODALES -->
    <%@ include file="modales.jsp" %>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Lógica personalizada INLINE -->
    <script>
        console.log("✓ JavaScript inline cargado");
        
        const CONTEXT_PATH = '${pageContext.request.contextPath}';
        const API_COMPETENCIAS = CONTEXT_PATH + '/controlador-competencias';
        
        function abrirDialogoEditarPerfil() {
            console.log("Abriendo modal de editar perfil...");
            const modal = new bootstrap.Modal(document.getElementById('modalEditarPerfil'));
            modal.show();
        }
        
        function abrirDialogoAgregarCapacidad() {
            console.log("Abriendo modal de agregar capacidad...");
            const modal = new bootstrap.Modal(document.getElementById('modalAgregarCapacidad'));
            modal.show();
        }
        
        function abrirDialogoEditarCapacidad(id, nombre, nivel, color) {
            console.log("Abriendo modal de editar capacidad:", { id, nombre, nivel, color });
            document.getElementById('inputIdCapacidad').value = id;
            document.getElementById('inputNombreTecnologiaEdit').value = nombre;
            document.getElementById('inputNivelTecnologiaEdit').value = nivel;
            document.getElementById('inputColorTecnologiaEdit').value = color;
            const modal = new bootstrap.Modal(document.getElementById('modalEditarCapacidad'));
            modal.show();
        }
        
        function confirmarEliminacion(id) {
            console.log("Confirmando eliminación de capacidad ID:", id);
            if (confirm('¿Estás seguro de que deseas eliminar esta capacidad?')) {
                eliminarCapacidad(id);
            }
        }
        
        async function eliminarCapacidad(id) {
            console.log("Eliminando capacidad ID:", id);
            try {
                const params = new URLSearchParams();
                params.append('accion', 'eliminar');
                params.append('idCapacidad', id);
                
                const response = await fetch(API_COMPETENCIAS, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: params.toString()
                });
                
                const resultado = await response.text();
                console.log('Response status:', response.status);
                console.log('Resultado:', resultado);
                
                if (response.ok && resultado === 'OK') {
                    alert(' Capacidad eliminada correctamente');
                    location.reload();
                } else {
                    throw new Error(resultado);
                }
            } catch (error) {
                console.error('Error eliminando capacidad:', error);
                alert(' Error al eliminar capacidad: ' + error.message);
            }
        }
        
        document.addEventListener('DOMContentLoaded', function() {
            console.log(' Aplicación inicializada');
            
            // Editar perfil
            document.getElementById('formularioEditarPerfil').addEventListener('submit', async function(e) {
                e.preventDefault();
                console.log("Formulario editar perfil enviado");
                
                const params = new URLSearchParams();
                params.append('accion', 'actualizarPerfil');
                params.append('nombre', document.getElementById('inputNombre').value);
                params.append('descripcion', document.getElementById('inputBio').value);
                params.append('email', document.getElementById('inputEmail').value);
                params.append('telefono', document.getElementById('inputTelefono').value);
                params.append('experiencia', document.getElementById('inputExperiencia').value);
                params.append('foto', document.getElementById('inputFoto').value);
                
                console.log('Datos enviados:', params.toString());
                
                try {
                    const response = await fetch(CONTEXT_PATH + '/', { 
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: params.toString()
                    });
                    
                    const resultado = await response.text();
                    console.log('Response status:', response.status);
                    console.log('Resultado:', resultado);
                    
                    if (response.ok && resultado === 'OK') {
                        alert(' Perfil actualizado');
                        location.reload();
                    } else {
                        throw new Error(resultado);
                    }
                } catch (error) {
                    console.error('Error:', error);
                    alert(' Error: ' + error.message);
                }
            });
            
            // Agregar capacidad
            document.getElementById('formularioAgregarCapacidad').addEventListener('submit', async function(e) {
                e.preventDefault();
                console.log("Formulario agregar enviado");
                
                const params = new URLSearchParams();
                params.append('accion', 'agregar');
                params.append('nombreTecnologia', document.getElementById('inputNombreTecnologia').value);
                params.append('nivelTecnologia', document.getElementById('inputNivelTecnologia').value);
                params.append('colorTecnologia', document.getElementById('inputColorTecnologia').value);
                
                console.log('Datos enviados:', params.toString());
                
                try {
                    const response = await fetch(API_COMPETENCIAS, { 
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: params.toString()
                    });
                    
                    const resultado = await response.text();
                    console.log('Response status:', response.status);
                    console.log('Resultado:', resultado);
                    
                    if (response.ok && resultado === 'OK') {
                        alert(' Capacidad agregada');
                        location.reload();
                    } else {
                        throw new Error(resultado);
                    }
                } catch (error) {
                    console.error('Error:', error);
                    alert(' Error: ' + error.message);
                }
            });
            
            // Editar capacidad
            document.getElementById('formularioEditarCapacidad').addEventListener('submit', async function(e) {
                e.preventDefault();
                console.log("Formulario editar capacidad enviado");
                
                const params = new URLSearchParams();
                params.append('accion', 'editar');
                params.append('idCapacidad', document.getElementById('inputIdCapacidad').value);
                params.append('nombreTecnologia', document.getElementById('inputNombreTecnologiaEdit').value);
                params.append('nivelTecnologia', document.getElementById('inputNivelTecnologiaEdit').value);
                params.append('colorTecnologia', document.getElementById('inputColorTecnologiaEdit').value);
                
                console.log('Datos enviados:', params.toString());
                
                try {
                    const response = await fetch(API_COMPETENCIAS, { 
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: params.toString()
                    });
                    
                    const resultado = await response.text();
                    console.log('Response status:', response.status);
                    console.log('Resultado:', resultado);
                    
                    if (response.ok && resultado === 'OK') {
                        alert(' Capacidad actualizada');
                        location.reload();
                    } else {
                        throw new Error(resultado);
                    }
                } catch (error) {
                    console.error('Error:', error);
                    alert(' Error: ' + error.message);
                }
            });
        });
    </script>
</body>
</html>


</body>
</html>
