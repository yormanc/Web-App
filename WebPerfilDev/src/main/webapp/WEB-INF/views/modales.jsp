<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Usuario" %>
<%
    Usuario perfilModal = (Usuario) request.getAttribute("usuarioEnSesion");
    if (perfilModal == null) {
        perfilModal = new Usuario("", "", "", "", "", "");
    }
%>

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
                        <input type="text" class="form-control" id="inputNombre" value="<%= perfilModal.getNombreCompleto() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Descripción Personal</label>
                        <textarea class="form-control" id="inputBio" rows="3" required><%= perfilModal.getDescripcionPersonal() %></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" class="form-control" id="inputEmail" value="<%= perfilModal.getCorreoContacto() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Teléfono</label>
                        <input type="text" class="form-control" id="inputTelefono" value="<%= perfilModal.getNumeroTelefono() %>">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Experiencia (años)</label>
                        <input type="text" class="form-control" id="inputExperiencia" value="<%= perfilModal.getExperienciaAnosText() %>">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">URL Foto Perfil</label>
                        <input type="text" class="form-control" id="inputFoto" value="<%= perfilModal.getUrlFotoPerfil() %>">
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

