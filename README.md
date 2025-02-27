# FotosAppAA

FotosAppAA es una aplicación de procesamiento de imágenes que permite aplicar filtros a imágenes de manera concurrente. Además, incluye funcionalidades avanzadas como ajuste del número de hilos en el pool de ejecución, historial de imágenes procesadas, y un interfaz intuitivo desarrollado con JavaFX.

## Funcionalidades

- **Aplicar Filtros**: 
  - Escala de grises.
  - Aumento de brillo.
  - Inversión de colores.
- **Procesamiento Concurrente**:
  - Configuración del número máximo de hilos en el pool.
  - Encolado automático de tareas si se excede el número de hilos configurado.
- **Historial**:
  - Registro de las imágenes procesadas con detalles de los filtros aplicados.
- **Guardado Personalizado**:
  - Selección de carpeta de guardado.
  - Generación automática de nombres de archivo.
- **Interfaz Intuitiva**:
  - Selector de imágenes y carpetas.
  - Barra de progreso para cada tarea.
  - Deslizador para ajustar el número de hilos concurrentes.
- **Splash Screen**:
  - Pantalla de inicio con una cuenta regresiva animada y un logo personalizado.

## Requisitos del Sistema

- **Java**: JDK 17 o superior.
- **Maven**: Para la gestión de dependencias.
- **Librerías utilizadas**:
  - `JavaFX`: Desarrollo de la interfaz gráfica.
  - `SLF4J` y `Log4j`: Registro de logs.
  - `ImageIO`: Manipulación de imágenes.

## Instalación y Uso

1. **Clonar el Repositorio**:
   ```bash
   git clone https://github.com/JMGella/FotosAppAA.git
   cd FotosAppAA
