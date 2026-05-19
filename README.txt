Nombre: Sadrak Cerdas Cedeño

Para ejecutar el proyecto se selecciona el proyecto y luego va a la function run Maven y selecciona la opcion javafx_run.

El motor para la base de datos es MySQL, gestionado con un servidor en linea phpMyAdmin.

Para la creación de la base de datos fue de manera en linea con la ayuda de la herramienta phpMyAdmin, en el proyecto vienen las credenciales necesarias para entrar a la base de datos. 


Descripción de las clases:

En el paquete model se puede encontrar las clases:
 Parcela: Modela las extensiones de tierra del sistema agrario.
 Responsable (Abstracta): Clase padre que define los atributos básicos de 
  quienes ejecutan las tareas.
  - Productor (Hija): Extiende de Responsable agregando el atributo de finca.
  - TecnicoAgricola (Hija): Extiende de Responsable incorporando la especialidad.
 Cultivo (Abstracta): Modela la base de las plantas cultivadas.
  - CultivoAnual (Hija): Implementa el ciclo corto medido en días.
  - CultivoPerenne (Hija): Implementa ciclos largos evaluados en años de producción.
 LaborAgricola: Clase central de asociación/composición. Almacena las 
  referencias directas a objetos de tipo Parcela, Cultivo y Responsable junto con 
  las variables de costo, descripción y fecha de realización.

En el paquete data se encuentra las clases:
  ParcelaDAO, CultivoDAO, ResponsableDAO, LaborAgricolaDAO: Clases responsables 
  de centralizar las sentencias SQL (INSERT, SELECT con JOINs relacionales), 
  aislando por completo la lógica de negocio y la base de datos de las interfaces.

En el paquete cr.ac.una.sistemagestionagraria se encuentran las vistas y controladores
  App: Clase principal autogenerada que extiende de Application para inicializar 
  el entorno de JavaFX y enlazar la hoja de estilos externa CSS.
  PrimaryController: Controlador principal de la interfaz visual 'primary.fxml'. 
  Maneja eventos de botones, validaciones de lógica (costos no negativos, áreas 
  mayores a cero) y el poblamiento polimórfico de las tablas gráficas.

Como se genera el reporte:
El informe general se genera mediante la persistencia de datos y el flujo de 
archivos de texto plano de la siguiente manera:

1. El sistema recupera en tiempo real todos los registros combinados desde la 
   tabla 'labores_agricolas' en MySQL utilizando consultas SQL JOIN complejas.
2. Al presionar el botón "Generar Informe" en la cuarta pestaña de la interfaz, 
   el controlador invoca un bloque que implementa las clases Java 'FileWriter' y 
   'PrintWriter'.
3. El programa formatea los strings para estructurar un diseño tabular ordenado y 
   vuelca de manera automática la información de las labores en un archivo físico 
   llamado "reporte_labores_agricolas.txt".
4. Este archivo plano se deposita directamente en la raíz principal del proyecto.


