drunken-wallhack
========================

Introducci�n
------------
Tercera tarea correspondiente al ramo "Programaci�n Orientada a Objetos" ELO329. Esta applet realizada en Java simula un laboratorio de f�sica que comprende cinco objetos: puntos fijos (FixedHook), bolas (Ball), bloques con roce cin�tico (Block), resortes (Spring) y Osciladores, simulando la interacci�n entre ellos mientras que a su vez muestra en un gr�fico la energ�a cinetica, potencial y total del sistema. En el archivo documentacion.pdf se puede encontrar una descripcion m�s detallada del trabajo realizado.

Compilaci�n y Ejecuci�n
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
-Una vez ejecutado el programa se puede apreciar un eje en donde interact�an los distintos elementos y una serie opciones en la barra superior de la ventana.
-Desde el men� "Configuration>insert" se pueden a�adir los distintos elementos (bolas, puntos fijos, resortes, bloques y osciladores) con sus propiedades asignadas de manera aleatoria (masa, tama�o, etc).
-Tambi�n se encuentra ac� la opci�n "My Scenario" que inserta un escenario prestablecido.
-Una vez agregados los elementos estos se pueden reposicionar seleccionandolos con un click y arrastr�ndolos a la posici�n deseada.
-En el caso que m�s de un elemento se encuentre en la misma posici�n se puede cambiar el elemento seleccionado con la tecla 'n' o 'space'.
-Desde el men� "My World" se pueden iniciar y detener las simulaciones como tambi�n modificar los par�metros del simulador.
-Es importante mencionar que durante una simulaci�n no se puede modificar los elementos a�adidos.
-Entre los par�metros que se pueden modificar del simulador esta la tasa de refresco de la pantalla como tambi�n el delta de tiempo entre cada c�lculo de la simulaci�n.

Archivos y breves descripciones
------------
- **PhysicsLab.html**: Archivo html necesario para ejecutar el applet.
- **PhysicsLab**: Ac� se ejecuta el main y se crea la instancia de mundo, como tambien los listeners y la interfaz gr�fica del "laboratorio".
- **abMenuListener**: Implementaci�n de los menus.
- **MouseListener**: Implementaci�n del mouse.
- **MyWorld**: Contiene los elementos a simular y emula el "mundo".
- **MyWorldView**: Dibuja el "mundo".
- **PhysicsElement**: Define las clases de elementos f�sicos. Ball y FixedHook heredan de este.
- **Simulateable**: Interfaz para clasificar clases simulables.
- **SpringAttachable**: Interfaz para clasificar clases que se pueden conctar a un resorte.
- **Ball**: Define el objeto bola.
- **BallView**: Define el aspecto de una bola para la interf�z gr�fica.
- **FixedHook**: Define el objeto punto fijo.
- **FixedHookView**: Define el aspecto de un punto fijo para la interfaz gr�fica.
- **Block**: Define el objeto de bloque con roce cin�tico.
- **BlockView**: Define el aspecto de los bloques.
- **Spring**: Define el objeto resorte.
- **SpringView**: Define el aspecto de un resorte para la interf�z gr�fica.
- **Oscilador**: Define el objeto oscilador.
- **OsciladorView**: Define el aspecto de un Oscilador para la interf�z gr�fica.
- ** GraphView**: Crea gr�ficos a partir de los datos simulados.
- **collision.wav**: Sonido de choques entre elementos.