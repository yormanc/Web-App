// Instancia de Bootstrap Modal
let modalEditarPerfil;
let modalAgregarCapacidad;
let modalEditarCapacidad;

// Inicializar cuando la página carga
document.addEventListener('DOMContentLoaded', function() {
    // Inicializar modales
    modalEditarPerfil = new bootstrap.Modal(document.getElementById('modalEditarPerfil'));
    modalAgregarCapacidad = new bootstrap.Modal(document.getElementById('modalAgregarCapacidad'));
    modalEditarCapacidad = new bootstrap.Modal(document.getElementById('modalEditarCapacidad'));
    
    // Agregar event listeners a los formularios
    document.getElementById('formularioEditarPerfil').addEventListener('submit', guardarPerfil);
    document.getElementById('formularioAgregarCapacidad').addEventListener('submit', agregarCapacidad);
    document.getElementById('formularioEditarCapacidad').addEventListener('submit', guardarCapacidad);
});

// ============================================
// FUNCIONES DE MODALES
// ============================================

function abrirDialogoEditarPerfil() {
    modalEditarPerfil.show();
}

function abrirDialogoAgregarCapacidad() {
    // Limpiar formulario
    document.getElementById('formularioAgregarCapacidad').reset();
    document.getElementById('inputColorTecnologia').value = '#667eea';
    modalAgregarCapacidad.show();
}

function abrirDialogoEditarCapacidad(id, nombre, nivel, color) {
    document.getElementById('inputIdCapacidad').value = id;
    document.getElementById('inputNombreTecnologiaEdit').value = nombre;
    document.getElementById('inputNivelTecnologiaEdit').value = nivel;
    document.getElementById('inputColorTecnologiaEdit').value = color;
    modalEditarCapacidad.show();
}

// ============================================
// FUNCIONES DE GUARDADO
// ============================================

function guardarPerfil(e) {
    e.preventDefault();
    
    const datosActualizados = {
        nombreCompleto: document.getElementById('inputNombre').value,
        descripcionPersonal: document.getElementById('inputBio').value,
        correoContacto: document.getElementById('inputEmail').value,
        numeroTelefono: document.getElementById('inputTelefono').value,
        experienciaAnosText: document.getElementById('inputExperiencia').value,
        urlFotoPerfil: document.getElementById('inputFoto').value
    };
    
    console.log('Guardando perfil:', datosActualizados);
    
    // Aquí irían las llamadas AJAX al servidor
    // Por ahora mostrar confirmación
    alert('Perfil actualizado correctamente');
    modalEditarPerfil.hide();
    
    // Recargar la página para ver cambios
    location.reload();
}

function agregarCapacidad(e) {
    e.preventDefault();
    
    const nuevaCapacidad = {
        denominacion: document.getElementById('inputNombreTecnologia').value,
        gradoDominacion: document.getElementById('inputNivelTecnologia').value,
        colorAsociado: document.getElementById('inputColorTecnologia').value
    };
    
    console.log('Agregando capacidad:', nuevaCapacidad);
    
    alert('Capacidad agregada correctamente');
    modalAgregarCapacidad.hide();
    
    // Recargar la página
    location.reload();
}

function guardarCapacidad(e) {
    e.preventDefault();
    
    const capacidadActualizada = {
        identificador: document.getElementById('inputIdCapacidad').value,
        denominacion: document.getElementById('inputNombreTecnologiaEdit').value,
        gradoDominacion: document.getElementById('inputNivelTecnologiaEdit').value,
        colorAsociado: document.getElementById('inputColorTecnologiaEdit').value
    };
    
    console.log('Guardando capacidad:', capacidadActualizada);
    
    alert('Capacidad actualizada correctamente');
    modalEditarCapacidad.hide();
    
    // Recargar la página
    location.reload();
}

// ============================================
// FUNCIONES DE ELIMINACIÓN
// ============================================

function confirmarEliminacion(id) {
    if (confirm('¿Deseas eliminar esta capacidad?')) {
        console.log('Eliminando capacidad con ID:', id);
        alert('Capacidad eliminada correctamente');
        
        // Recargar la página
        location.reload();
    }
}

// ============================================
// FUNCIONES AUXILIARES
// ============================================

function mostrarNotificacion(mensaje, tipo = 'info') {
    // Crear elemento de notificación
    const notif = document.createElement('div');
    notif.className = `alert alert-${tipo} alert-dismissible fade show`;
    notif.innerHTML = `
        ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    // Agregar al DOM
    document.body.insertBefore(notif, document.body.firstChild);
    
    // Auto-cerrar después de 3 segundos
    setTimeout(() => {
        notif.remove();
    }, 3000);
}