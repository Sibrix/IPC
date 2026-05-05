# ⚓ Carta Náutica Digital - JavaFX

Sistema interactivo de navegación y gestión de rutas desarrollado en **Java** utilizando **JavaFX** y **Scene Builder**. Este proyecto permite a los usuarios interactuar con una carta náutica real, gestionar perfiles de usuario y realizar cálculos de navegación.

## 🚀 Características Principales

- **Interfaz Interactiva:** Visualización dinámica de una carta náutica con soporte para herramientas de dibujo.
- **Herramientas de Navegación:** Implementación de herramientas digitales como transportador de ángulos, reglas y marcadores de puntos de interés (POI).
- **Gestión de Usuarios:** Sistema completo de registro, inicio de sesión y modificación de perfiles, con validación de datos.
- **Persistencia de Datos:** Integración con base de datos **SQLite** para almacenar de forma segura la información de los usuarios y sus rutas.
- **Historial de Resultados:** Módulo para visualizar y gestionar registros de sesiones y problemas resueltos.
- **Diseño Moderno:** Estilizado mediante hojas de estilo **CSS** personalizadas para una experiencia de usuario profesional.

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 11+
- **Framework UI:** JavaFX (Scene Builder para el diseño de vistas FXML)
- **Base de Datos:** SQLite (JDBC)
- **Patrón de Diseño:** Modelo-Vista-Controlador (MVC)
- **Entorno de Desarrollo:** NetBeans / IntelliJ IDEA

## 📂 Estructura del Proyecto

- `src/CartaNautica`: Lógica principal del mapa y controladores de la carta.
- `src/Main`: Gestión de ventanas, inicio de sesión y registro.
- `src/modelos`: Clases de datos y utilidades (ej. gestión de alertas).
- `src/estilos`: Archivos CSS para la personalización de la interfaz.
- `lib/`: Librerías externas (SQLite JDBC y conectores).
