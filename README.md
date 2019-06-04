# callcenter
Prueba técnica para Almundo.
Para el presente desarrollo se empleó el patrón Observer, ya que era necesario activar los mecanismos de asignación de llamadas, una vez terminara alguna de ellas.
Para el asunto de los Empleados, se realiza una carga inicial a partir de un archivo JSON, donde se prioriza a los operadores. Los clientes de las llamadas se crean aleatoriamente.
La asignación de llamadas se hace: una línea, un empleado, que se extraen de una lista de ids, donde la lista de índices de empleados se ordena cada que se libera recurso, esto garantiza que los directores y supervisores sean los últimos en ser llamados. El dato es que los primeros operadores van a estar sobre cargados.
Para el manejo de las llamadas, cuando no se tienen recursos disponibles se implementó una cola sencilla: al entrar llamadas y encontrar que no se puede atender aún se agrega al final de la cola, cuando una llamada finaliza y se libera recurso se hace el llamado de asignación a la primera llamada de la lista.
