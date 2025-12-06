# Mi Perfil Dev - Portafolio de Habilidades de Programación

Aplicación web para mostrar mi perfil profesional y gestionar mis habilidades técnicas de programación.

## Descripción

Este es un proyecto web hecho con Java que permite mostrar información personal de un programador y administrar una lista de tecnologías que conoce. La aplicación usa el patrón MVC y guarda los datos en archivos JSON.

## Tecnologías utilizadas

- Java JDK 21
- Jakarta Servlets 6.0
- JSP (JavaServer Pages)
- Bootstrap 5.3
- JavaScript
- Apache Tomcat 10.1.49
- Maven

## Funcionalidades

### Perfil de usuario
- Mostrar nombre completo
- Mostrar foto de perfil
- Mostrar descripción personal
- Mostrar información de contacto (email y teléfono)
- Mostrar años de experiencia
- Editar toda la información del perfil

### Gestión de capacidades técnicas
- Ver lista de capacidades técnicas (tecnologías que conozco)
- Agregar nuevas capacidades con nombre, nivel de dominio y color
- Editar capacidades existentes
- Eliminar capacidades

### Persistencia de datos
Los datos se guardan en dos archivos JSON:
- `usuarioBase.json` - Información del perfil
- `competenciasBase.json` - Lista de capacidades técnicas

## Estructura del proyecto

WebPerfilDev/
├── src/main/java/
│ ├── controllers/
│ │ ├── ControladorPrincipal.java
│ │ ├── ControladorCompetencias.java
│ │ └── InicializadorAplicacion.java
│ └── models/
│ ├── Usuario.java
│ ├── Competencia.java
│ └── AdministradorDatos.java
├── src/main/webapp/
│ └── WEB-INF/views/
│ ├── index.jsp
│ └── modales.jsp
└── pom.xml

text

## Cómo ejecutar el proyecto

### Requisitos previos
- Tener instalado Java JDK 21
- Tener instalado Apache NetBeans 25
- Tener instalado Apache Tomcat 10.1.49

### Pasos para ejecutar

1. Clonar el repositorio:
git clone https://github.com/yormanc/Web-App.git

text

2. Abrir NetBeans

3. File -> Open Project

4. Seleccionar la carpeta WebPerfilDev

5. Configurar el servidor Tomcat:
   - Tools -> Servers
   - Add Server
   - Seleccionar Apache Tomcat
   - Indicar la ruta donde está instalado Tomcat

6. Limpiar y compilar el proyecto:
   - Click derecho en el proyecto
   - Clean
   - Build

7. Ejecutar el proyecto:
   - Click derecho en el proyecto
   - Run

8. El navegador se abrirá automáticamente en:
http://localhost:8085/WebPerfilDev/

text

## Arquitectura MVC

El proyecto sigue el patrón Modelo-Vista-Controlador:

**Modelo:**
- Usuario.java - Representa la información del perfil
- Competencia.java - Representa cada capacidad técnica
- AdministradorDatos.java - Maneja la lectura y escritura de archivos JSON

**Vista:**
- index.jsp - Página principal que muestra el perfil y las capacidades
- modales.jsp - Ventanas emergentes con los formularios de edición

**Controlador:**
- ControladorPrincipal.java - Maneja la carga de la página principal y actualización del perfil
- ControladorCompetencias.java - Maneja agregar, editar y eliminar capacidades
- InicializadorAplicacion.java - Se ejecuta cuando arranca el servidor

## Cómo funciona

1. Cuando se inicia el servidor, InicializadorAplicacion crea los archivos JSON si no existen

2. Cuando el usuario entra a la página principal:
   - ControladorPrincipal carga los datos del usuario desde usuarioBase.json
   - ControladorPrincipal carga las capacidades desde competenciasBase.json
   - Los datos se envían a index.jsp
   - index.jsp muestra la información en HTML

3. Cuando el usuario edita el perfil:
   - Se abre un modal con un formulario
   - Al guardar, JavaScript envía los datos a ControladorPrincipal
   - ControladorPrincipal guarda los nuevos datos en usuarioBase.json

4. Cuando el usuario agrega/edita/elimina una capacidad:
   - Se abre un modal con un formulario
   - Al guardar, JavaScript envía los datos a ControladorCompetencias
   - ControladorCompetencias actualiza competenciasBase.json

## Archivos JSON

Los datos se guardan en la carpeta:
target/WebPerfilDev-1.0-SNAPSHOT/data/

text

**usuarioBase.json** - Guarda la información del perfil:
{
"nombreCompleto": "Yorman Alexis Cortes Echeverri",
"descripcionPersonal": "Descripción del perfil",
"correoContacto": "yorman.cortes@udea.edu.co",
"numeroTelefono": "+57 (300) 1234567",
"experienciaAnosText": "3 años",
"urlFotoPerfil": "ruta/a/la/foto.jpg"
}

text

**competenciasBase.json** - Guarda las capacidades técnicas:
[
{
"identificador": 1,
"denominacion": "Java",
"gradoDominacion": "Avanzado",
"colorAsociado": "#ED8936"
}
]

text

## Problemas conocidos

- La foto de perfil se configura mediante URL
- Los archivos JSON se crean en la carpeta target, que se borra al hacer Clean
- Solo funciona en un navegador a la vez

## Pruebas realizadas

Se probaron las siguientes funcionalidades:

1. Cargar la página principal - Funciona correctamente
2. Editar el perfil del usuario - Funciona correctamente
3. Agregar una nueva capacidad - Funciona correctamente
4. Editar una capacidad existente - Funciona correctamente
5. Eliminar una capacidad - Funciona correctamente
6. Reiniciar el servidor y verificar que los datos se mantienen - Funciona correctamente

## Autor

Yorman Alexis Cortes Echeverri
- Email: yorman.cortes@udea.edu.co
- Teléfono: +57 (300) 1234567

## Información del proyecto

Este proyecto fue desarrollado como parte de un curso de programación web. Es mi primer proyecto usando el patrón MVC y servlets de Java.

## Notas

- El proyecto no usa frameworks como Spring o JSF
- No usa base de datos, solo archivos JSON
- El código está comentado para facilitar su comprensión
- Algunas partes del código se pueden mejorar, pero funciona correctamente

## Enlaces útiles

- Repositorio: https://github.com/yormanc/Web-App
- Documentación de Jakarta Servlets: https://jakarta.ee/specifications/servlet/
- Bootstrap: https://getbootstrap.com/
- Apache Tomcat: https://tomcat.apache.org/