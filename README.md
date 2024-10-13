# UD1_PE_ManejoFicheros

Este proyecto implementa un sistema de manejo de jugadores utilizando el patrón **MVC (Modelo-Vista-Controlador)** en conjunto con el patrón **DAO (Data Access Object)**. 

## Estructura del Proyecto

El programa crea una carpeta llamada **DATOS** en el directorio del proyecto, donde se almacenan todos los tipos de archivos utilizados para la persistencia de los datos de los jugadores. Los formatos de archivo soportados incluyen texto, binario, objetos, acceso aleatorio y XML.

## Funcionalidades

El sistema permite realizar las siguientes operaciones CRUD (Crear, Leer, Actualizar, Eliminar):

1. **Crear Jugador**: Permite agregar un nuevo jugador ingresando sus datos (ID, Nick, Experiencia, Nivel de Vida, Monedas).
   
2. **Leer Jugador**: Se pueden listar todos los jugadores registrados o buscar un jugador específico por su ID. Se muestra información detallada sobre el jugador.

3. **Actualizar Jugador**: Permite modificar los datos de un jugador existente. Se solicita primero el ID del jugador y se muestran sus datos actuales antes de proceder con la modificación.

4. **Eliminar Jugador**: Se puede eliminar un jugador del sistema especificando su ID.

5. **Configuración de Almacenamiento**: Al iniciar el programa, se debe elegir el tipo de archivo que se desea utilizar para almacenar los datos de los jugadores. Es posible cambiar el tipo de archivo en cualquier momento durante la ejecución del programa.

## Instrucciones de Uso

1. **Ejecución del Programa**: Al ejecutar el programa, se presentará un menú principal con las opciones disponibles. 

2. **Elección del Tipo de Almacenamiento**: Antes de realizar cualquier operación, se debe seleccionar el tipo de archivo que se desea manejar. Esto determinará cómo se almacenarán los datos en el sistema.

3. **Operaciones CRUD**: Una vez que se ha configurado el tipo de archivo, se pueden realizar las operaciones CRUD mencionadas anteriormente.

4. **Cambio de Tipo de Archivo**: En cualquier momento, se puede volver al menú de configuración para cambiar el tipo de archivo. Esto permitirá utilizar otro método de almacenamiento sin perder los datos existentes.


## Estructura de Clases

- **Modelo**: Contiene las clases `Jugador`, que representa la entidad del jugador, y las interfaces y clases DAO que manejan la persistencia de datos.
  
- **Vista**: Encargada de interactuar con el usuario, mostrando menús y solicitando datos.

- **Controlador**: Contiene la lógica de negocio y coordina la interacción entre la vista y el modelo.
