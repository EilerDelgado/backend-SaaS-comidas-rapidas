# Base de Datos

La base de datos actual es MySQL y el esquema está gestionado por las entidades JPA de Hibernate.

## Naturaleza Multi-Tenant

El sistema está diseñado para dar soporte a múltiples restaurantes. Todas las entidades transaccionales y de catálogo principales tienen (o tendrán) relación con un `Restaurante` para garantizar que la información se mantenga aislada por tenant.

## Entidades Principales

* **Restaurante:** Representa al negocio en sí. Aísla lógicamente la información.
* **Usuario:** Puede ser un empleado del restaurante o un cliente.
* **Categoria:** Agrupa los productos para facilitar la búsqueda y organización en el menú.
* **Producto:** Elemento vendible. Tiene atributos como precio, descripción y estado de disponibilidad.
* **Pedido:** Representa una transacción de venta. Contiene el estado del pedido, el método de pago utilizado y está asociado a un Usuario (opcional si es venta en mostrador "walk-in").
* **DetallePedido:** Desglose de cada Pedido; incluye el Producto específico, cantidad y precio registrado en el momento de la compra.
* **Caja:** Representa los flujos de dinero y pagos diarios.

## Generación de Identificadores (Regla Implementada)

Para entidades que requieren un identificador de negocio seguro contra concurrencia (como códigos de Producto o de Pedido), se implementa un generador de identificadores personalizado (`IdentifierGenerator` de Hibernate). Esto permite generar llaves primarias en formatos legibles como `PRD001` sin requerir transacciones extra o bloqueos en la base de datos a través de `SELECT MAX`.
