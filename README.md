drunken-wallhack
========================

Introducción
------------
Tercera tarea correspondiente al ramo "Programación Orientada a Objetos" ELO329. Esta applet realizada en Java simula un laboratorio de física que comprende cinco objetos: puntos fijos (FixedHook), bolas (Ball), bloques con roce cinético (Block), resortes (Spring) y Osciladores, simulando la interacción entre ellos mientras que a su vez muestra en un gráfico la energía cinetica, potencial y total del sistema. En el archivo documentacion.pdf se puede encontrar una descripcion más detallada del trabajo realizado.

Compilación y Ejecución
------------
El programa se puede correr tanto como applet como aplicacion. Para preparar la ejecucion como applet hay que ejecutar el siguiente comando:

	$ make jar
Para correr el applet basta ejecutar el siguiente comando, que ejecutara el arhicvo PhysicsLab.html

	$ make runApplet

Para ejecutar el programa aplicacion basta usar:

	$ make run
o bien este otro comando:

	$ java -jar PhysicsLab.jar

Comentarios
------------
-agregar aca las opciones de html
-Una vez ejecutado el programa se puede apreciar un eje en donde interactúan los distintos elementos y una serie opciones en la barra superior de la ventana.
-Desde el menú "Configuration>insert" se pueden añadir los distintos elementos (bolas, puntos fijos, resortes, bloques y osciladores) con sus propiedades asignadas de manera aleatoria (masa, tamaño, etc).
-También se encuentra acá la opción "My Scenario" que inserta un escenario prestablecido.
-Una vez agregados los elementos estos se pueden reposicionar seleccionandolos con un click y arrastrándolos a la posición deseada.
-En el caso que más de un elemento se encuentre en la misma posición se puede cambiar el elemento seleccionado con la tecla 'n' o 'space'.
-Desde el menú "My World" se pueden iniciar y detener las simulaciones como también modificar los parámetros del simulador.
-Es importante mencionar que durante una simulación no se puede modificar los elementos añadidos.
-Entre los parámetros que se pueden modificar del simulador esta la tasa de refresco de la pantalla como también el delta de tiempo entre cada cálculo de la simulación.

Archivos y breves descripciones
------------
- **PhysicsLab.html**: Archivo html necesario para ejecutar el applet.
- **PhysicsLab**: Acá se ejecuta el main y se crea la instancia de mundo, como tambien los listeners y la interfaz gráfica del "laboratorio".
- **abMenuListener**: Implementación de los menus.
- **MouseListener**: Implementación del mouse.
- **MyWorld**: Contiene los elementos a simular y emula el "mundo".
- **MyWorldView**: Dibuja el "mundo".
- **PhysicsElement**: Define las clases de elementos físicos. Ball y FixedHook heredan de este.
- **Simulateable**: Interfaz para clasificar clases simulables.
- **SpringAttachable**: Interfaz para clasificar clases que se pueden conctar a un resorte.
- **Ball**: Define el objeto bola.
- **BallView**: Define el aspecto de una bola para la interfáz gráfica.
- **FixedHook**: Define el objeto punto fijo.
- **FixedHookView**: Define el aspecto de un punto fijo para la interfaz gráfica.
- **Block**: Define el objeto de bloque con roce cinético.
- **BlockView**: Define el aspecto de los bloques.
- **Spring**: Define el objeto resorte.
- **SpringView**: Define el aspecto de un resorte para la interfáz gráfica.
- **Oscilador**: Define el objeto oscilador.
- **OsciladorView**: Define el aspecto de un Oscilador para la interfáz gráfica.
- ** GraphView**: Crea gráficos a partir de los datos simulados.
- **collision.wav**: Sonido de choques entre elementos.