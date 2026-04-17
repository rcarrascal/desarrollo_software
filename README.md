# Sistema POS - Demostración de Relaciones JPA

Este proyecto es una aplicación completa de punto de venta (POS) desarrollada con Spring Boot y JPA, diseñada específicamente para demostrar las diferentes relaciones de JPA en un contexto real.

## 🎯 Objetivo del Proyecto

Demostrar de manera práctica las relaciones JPA:
- **@OneToMany**: Cliente → Ventas, Proveedor → Compras, Producto → Detalles
- **@ManyToOne**: Compra → Proveedor, Venta → Cliente, Detalles → Producto
- **Relaciones bidireccionales**: Compra ↔ DetalleCompra, Venta ↔ DetalleVenta
- **Cascade Operations**: Operaciones en cascada para detalles
- **Fetch Strategies**: Lazy loading para optimización

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/pos/
├── model/              # Entidades JPA
│   ├── Cliente.java
│   ├── Proveedor.java
│   ├── Producto.java
│   ├── Compra.java
│   ├── DetalleCompra.java
│   ├── Venta.java
│   └── DetalleVenta.java
├── repository/         # Repositorios JPA
│   ├── ClienteRepository.java
│   ├── ProveedorRepository.java
│   ├── ProductoRepository.java
│   ├── CompraRepository.java
│   └── VentaRepository.java
├── service/            # Interfaces de servicios
│   ├── ClienteService.java
│   ├── ProveedorService.java
│   ├── ProductoService.java
│   ├── CompraService.java
│   ├── VentaService.java
│   └── impl/          # Implementaciones
│       ├── ClienteServiceImpl.java
│       ├── ProveedorServiceImpl.java
│       ├── ProductoServiceImpl.java
│       ├── CompraServiceImpl.java
│       └── VentaServiceImpl.java
└── controller/         # Controladores MVC
    ├── HomeController.java
    ├── ClienteController.java
    ├── ProveedorController.java
    ├── ProductoController.java
    ├── CompraController.java
    └── VentaController.java
```

## 📊 Modelo de Datos

### Relaciones Principales

1. **Cliente ↔ Venta** (@OneToMany / @ManyToOne)
   - Un cliente puede tener muchas ventas
   - Cada venta pertenece a un cliente

2. **Proveedor ↔ Compra** (@OneToMany / @ManyToOne)
   - Un proveedor puede tener muchas compras
   - Cada compra pertenece a un proveedor

3. **Producto ↔ Detalles** (@OneToMany)
   - Un producto puede estar en muchos detalles de compra
   - Un producto puede estar en muchos detalles de venta

4. **Compra ↔ DetalleCompra** (@OneToMany bidireccional)
   - Una compra tiene múltiples detalles
   - Cascade ALL para operaciones automáticas

5. **Venta ↔ DetalleVenta** (@OneToMany bidireccional)
   - Una venta tiene múltiples detalles
   - Cascade ALL para operaciones automáticas

## 🚀 Tecnologías Utilizadas

- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (desarrollo)
- **MySQL** (producción - opcional)
- **Thymeleaf** (vistas)
- **Bootstrap 5** (UI)
- **Lombok** (reducción de código boilerplate)
- **Maven** (gestión de dependencias)

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- (Opcional) MySQL 8.0 o superior para producción

## 🔧 Instalación y Ejecución

### 1. Clonar o descargar el proyecto

### 2. Compilar el proyecto
```bash
mvn clean install
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

### 4. Acceder a la aplicación
- **URL**: http://localhost:8080
- **Consola H2**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:posdb`
  - Usuario: `sa`
  - Password: (dejar en blanco)

## 🔄 Migración a MySQL

Para migrar de H2 a MySQL, editar `src/main/resources/application.properties`:

```properties
# Comentar configuración H2
#spring.datasource.url=jdbc:h2:mem:posdb
#spring.datasource.driverClassName=org.h2.Driver
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Descomentar configuración MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/posdb?createDatabaseIfNotExist=true
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

## 📚 Funcionalidades

### Gestión de Clientes
- Crear, editar, listar y eliminar clientes
- Relación @OneToMany con ventas

### Gestión de Proveedores
- Crear, editar, listar y eliminar proveedores
- Relación @OneToMany con compras

### Gestión de Productos
- Crear, editar, listar y eliminar productos
- Control de stock automático
- Relación @OneToMany con detalles de compra y venta

### Gestión de Compras
- Registrar compras con múltiples productos
- Asociar compra a un proveedor
- Actualización automática de stock (incremento)
- Cálculo automático de totales

### Gestión de Ventas
- Registrar ventas con múltiples productos
- Asociar venta a un cliente
- Actualización automática de stock (decremento)
- Validación de stock disponible
- Cálculo automático de totales

## 🎓 Conceptos JPA Demostrados

### 1. Anotaciones de Relación
- `@OneToMany`: Relaciones uno a muchos
- `@ManyToOne`: Relaciones muchos a uno
- `@JoinColumn`: Definición de columnas de unión

### 2. Estrategias de Cascade
- `CascadeType.ALL`: Todas las operaciones en cascada
- `orphanRemoval = true`: Eliminación de huérfanos

### 3. Estrategias de Fetch
- `FetchType.LAZY`: Carga perezosa para optimización
- `@Query` con `JOIN FETCH`: Solución al problema N+1

### 4. Callbacks de Ciclo de Vida
- `@PrePersist`: Antes de persistir
- `@PreUpdate`: Antes de actualizar

### 5. Validaciones
- `@NotBlank`: Campos obligatorios
- `@Email`: Validación de email
- `@PositiveOrZero`: Validación de números

## 🎨 Interfaz de Usuario

La aplicación cuenta con una interfaz moderna desarrollada con:
- Bootstrap 5 para diseño responsive
- Bootstrap Icons para iconografía
- Gradientes y sombras para un diseño atractivo
- Alertas y mensajes de feedback al usuario

## 📝 Notas para la Clase Magistral

Este proyecto está diseñado para explicar:

1. **Relaciones básicas**: Cómo modelar relaciones del mundo real
2. **Bidireccionalidad**: Ventajas y consideraciones
3. **Cascade**: Cuándo y cómo usar operaciones en cascada
4. **Fetch strategies**: Optimización de consultas
5. **Transacciones**: Manejo de transacciones con `@Transactional`
6. **Patrón Repository**: Abstracción de acceso a datos
7. **Patrón Service**: Lógica de negocio separada
8. **MVC**: Arquitectura Model-View-Controller

## 🐛 Solución de Problemas

### Error de conexión a H2
- Verificar que la URL JDBC sea correcta
- Asegurarse de que la consola H2 esté habilitada

### Error de stock insuficiente
- Primero realizar compras para incrementar el stock
- Luego realizar ventas

### Error de dependencias
- Ejecutar `mvn clean install` para descargar dependencias

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

## 👨‍💻 Autor

Proyecto creado para demostración de JPA y Spring Boot.
