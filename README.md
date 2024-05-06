# DEV_jhondiaz


Para implementar la funcionalidad de la auditoria pienso crear una estructura completa del modulith con las carpetas:

- management
    Aqui pienso manejar el tema para guardar el objeto en la tabla, en este caso seria en el txt

- model
    Aqui se creara la entidad que describira los parametros que tiene la tabla, es decir, id, descripcion y la fecha

- repository
    Por este lado tendremos los metodos para guardar la auditoria sea en la base de datos o en el txt

- AuditoriaInternal
    En este pienso  implementar los metodos que van dirigidos a la parte interna de mi aplicativo, como voy a capturar los errores del servidor no utilizare la externa


Una vez creado se implementara en el management de service, de esta manera controlaremos en flujo de errores al crear un servicio.
