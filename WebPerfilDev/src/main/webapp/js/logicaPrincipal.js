/* global bootstrap */

console.log("logicaPrincipal.js cargado correctamente");

const CONTEXT_PATH = '/WebPerfilDev';
const API_COMPETENCIAS = `${CONTEXT_PATH}/controlador-competencias`;

function abrirDialogoEditarPerfil() {
    console.log("Abriendo modal de editar perfil...");
    const modal = new bootstrap.Modal(document.getElementById('modalEditarPerfil'));
    modal.show();
}

document.getElementById('formularioEditarPerfil').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const datos = {
        nombre: document.getElementById('inputNombre').value,
        descripcion: document.getElementById('inputBio').value,
        email: document.getElementById('inputEmail').value,
        telefono: document.getElementById('inputTelefono').value,
        experiencia: document.getElementById('inputExperiencia').value,
        foto: document.getElementById('inputFoto').value
    };
    
    console.log("Enviando actualización de perfil:", datos);
    
    try {
        const formData = new FormData();
        formData.append('accion', 'actualizarPerfil');
        formData.append('nombre', datos.nombre);
        formData.append('descripcion', datos.descripcion);
        formData.append('email', datos.email);
        formData.append('telefono', datos.telefono);
        formData.append('experiencia', datos.experiencia);
        formData.append('foto', datos.foto);
        
        const response = await fetch(`${CONTEXT_PATH}/`, {
            method: 'POST',
            body: formData
        });
        
        const resultado = await response.text();
        
        if (response.ok && resultado === 'OK') {
            alert('Perfil actualizado correctamente');
            location.reload();
        } else {
            throw new Error(resultado);
        }
    } catch (error) {
        console.error('Error actualizando perfil:', error);
        alert('Error al actualizar el perfil: ' + error.message);
    }
});

function abrirDialogoAgregarCapacidad() {
    console.log("Abriendo modal de agregar capacidad...");
    const modal = new bootstrap.Modal(document.getElementById('modalAgregarCapacidad'));
    modal.show();
}
document.getElementById('formularioAgregarCapacidad').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const datos = {
        nombre: document.getElementById('inputNombreTecnologia').value,
        nivel: document.getElementById('inputNivelTecnologia').value,
        color: document.getElementById('inputColorTecnologia').value
    };
    
    console.log("Enviando nueva capacidad:", datos);
    
    try {
        const formData = new FormData();
        formData.append('accion', 'agregar');
        formData.append('nombreTecnologia', datos.nombre);
        formData.append('nivelTecnologia', datos.nivel);
        formData.append('colorTecnologia', datos.color);
        
        const response = await fetch(API_COMPETENCIAS, {
            method: 'POST',
            body: formData
        });
        
        const resultado = await response.text();
        
        if (response.ok && resultado === 'OK') {
            alert('Capacidad agregada correctamente');
            location.reload();
        } else {
            throw new Error(resultado);
        }
    } catch (error) {
        console.error('Error agregando capacidad:', error);
        alert('Error al agregar capacidad: ' + error.message);
    }
});

function abrirDialogoEditarCapacidad(id, nombre, nivel, color) {
    console.log("Abriendo modal de editar capacidad:", { id, nombre, nivel, color });
    
    document.getElementById('inputIdCapacidad').value = id;
    document.getElementById('inputNombreTecnologiaEdit').value = nombre;
    document.getElementById('inputNivelTecnologiaEdit').value = nivel;
    document.getElementById('inputColorTecnologiaEdit').value = color;
    
    const modal = new bootstrap.Modal(document.getElementById('modalEditarCapacidad'));
    modal.show();
}

document.getElementById('formularioEditarCapacidad').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const datos = {
        id: document.getElementById('inputIdCapacidad').value,
        nombre: document.getElementById('inputNombreTecnologiaEdit').value,
        nivel: document.getElementById('inputNivelTecnologiaEdit').value,
        color: document.getElementById('inputColorTecnologiaEdit').value
    };
    
    console.log("Enviando actualización de capacidad:", datos);
    
    try {
        const formData = new FormData();
        formData.append('accion', 'editar');
        formData.append('idCapacidad', datos.id);
        formData.append('nombreTecnologia', datos.nombre);
        formData.append('nivelTecnologia', datos.nivel);
        formData.append('colorTecnologia', datos.color);
        
        const response = await fetch(API_COMPETENCIAS, {
            method: 'POST',
            body: formData
        });
        
        const resultado = await response.text();
        
        if (response.ok && resultado === 'OK') {
            alert(' Capacidad actualizada correctamente');
            location.reload();
        } else {
            throw new Error(resultado);
        }
    } catch (error) {
        console.error('Error actualizando capacidad:', error);
        alert(' Error al actualizar capacidad: ' + error.message);
    }
});

function confirmarEliminacion(id) {
    console.log("Confirmando eliminación de capacidad ID:", id);
    
    if (confirm('¿Estás seguro de que deseas eliminar esta capacidad?')) {
        eliminarCapacidad(id);
    }
}

async function eliminarCapacidad(id) {
    console.log("Eliminando capacidad ID:", id);
    
    try {
        const formData = new FormData();
        formData.append('accion', 'eliminar');
        formData.append('idCapacidad', id);
        
        const response = await fetch(API_COMPETENCIAS, {
            method: 'POST',
            body: formData
        });
        
        const resultado = await response.text();
        
        if (response.ok && resultado === 'OK') {
            alert('Capacidad eliminada correctamente');
            location.reload();
        } else {
            throw new Error(resultado);
        }
    } catch (error) {
        console.error('Error eliminando capacidad:', error);
        alert('Error al eliminar capacidad: ' + error.message);
    }
}


document.addEventListener('DOMContentLoaded', function() {
    console.log('Aplicación inicializada correctamente');
    console.log('Context Path:', CONTEXT_PATH);
    console.log('API Competencias:', API_COMPETENCIAS);
});
