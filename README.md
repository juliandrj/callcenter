# Almundo: Prueba Técnica
## Solución base
Para el presente desarrollo se empleó el patrón **Observer**, ya que era necesario activar los mecanismos de asignación de llamadas, una vez terminara alguna de ellas.

Para el asunto de los Empleados, se realiza una carga inicial a partir de un archivo **JSON**, donde se prioriza a los operadores. El archivo es el siguiente, donde se puede observar la estructura: [empleados.json](https://github.com/juliandrj/callcenter/blob/master/callcenter/src/main/resources/empleados.json).

La estructura generada se explica mejor en el diagrama de clases anexo: [callcenter.png](https://github.com/juliandrj/callcenter/blob/master/callcenter.png)

Los clientes de las llamadas se crean aleatoriamente, usando apache-commons-lang se asigna una cadena aleatoria para el nombre y un numero aleatorio para el id.

La asignación de llamadas se hace: una línea, un empleado, que se extraen de una lista de ids, donde: la lista de índices de empleados disponibles se ordena cada que se libera recurso, esto garantiza que los directores y supervisores sean los últimos en ser llamados, pero sobre carga a los operadores con indice más bajo.

A medida que llega una llamada se toma el primer elemento de las listas de disponibles y se elimina. Luego se emplea el indice para asignar la llamada a un hilo con su respectivo empleado. Cuando la llamada finaliza se reasignan los indices en las listas de disponibles.

## Solución extra

Para el caso de las llamadas que no pueden ser atendidas por falta de recursos (líneas y empleados), se encola la llamada en un lista. Esta cola se procesa cada que una llamada en curso finaliza. Se atiende al primer elemento de la lista y se retira de ella, luego se ejecuta el despachador quien determina si puede ser atendida o si se debe encolar de nuevo.

La solución se da por es simple hecho de ser un comportamiento, en general, natural. Negar una llamada al cliente es con frecuencia un motivo de retiro, no vale la pena perder un cliente por no implementar una cola.
