está es la app que hice en funcionamento, explicando un poco mejor cómo funciona: 
- Tiene dos categorías de instancias, localizaciones instantáneas, que es apenas una única ubicación, y rutas, que son instancias que guardan un conjunto de ubicaciones instantáneas. 
- ⁠Para guardar los datos probé algunos métodos pero el mejor fue usando una biblioteca que se llama Room. Esta permite implementar base de datos SQLite locales de manera más simple y esto tiene algunas ventajas: 
    - Funcionamento asincrono de fábrica, lo que permite acceso thread safe, o sea, seria posible enviar en tiempo real las localizaciones al servidor al mismo tiempo que estas son grabadas localmente sin mayores modificaciones o creación de una colección especifica para eso.
    - Debug de los datos adquiridos en tiempo real
    - Query e Sort del próprio SQL evitando tener que implementar manualmente búsqueda y filtro de instancias de la colección
- ⁠Finalmente implementé un servicio de rastreo de localización que funciona en foreground, o sea, en segundo plano pero el usuario es informado de rastreamento por medio de una notificación, el intervalo es de 1 segundo entre cada muestra que es grabada en la base de datos
