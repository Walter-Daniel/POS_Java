# 🛒 MyPOS: Sistema de Punto de Venta

Sistema de ventas de escritorio desarrollado en **Java 21** con **NetBeans** y **MySQL**, orientado a la gestión de clientes, productos y facturación. Ideal para pequeños y medianos negocios que requieren llevar control de ventas e inventario.

![MyPOS](https://res.cloudinary.com/journal-project/image/upload/v1754000652/mnzazx6hcr3mvtnay6lx.png)

---

## 📋 Funcionalidades Principales

- 👥 Gestión de Clientes (CRUD)
- 📦 Gestión de Productos (CRUD)
- 🧾 Generación de Ventas con detalle de factura
- 🧮 Cálculo automático de IVA y Total
- 📊 Reportes por rango de fechas
- 🔎 Búsqueda de facturas por número
- 🗃 Actualización automática del stock
- 💾 Conexión a base de datos MySQL

---

## 🧱 Tecnologías Usadas

| Tecnología | Versión  |
|------------|----------|
| Java       | 21       |
| NetBeans   | 21+      |
| MySQL      | 8.0+     |
| JDBC       | ✔️       |
| Swing      | ✔️       |

---

## 📂 Estructura del Proyecto

```bash
📦 MyPOS
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Config/              # Conexión a BD
│   │   │   ├── UI/                  # Interfaces gráficas (Swing)
│   │   │   └── Models/              # Clases de negocio (Producto, Cliente, Factura)
│   │   └── resources/
├── database/
│   └── mypos_schema.sql            # Script de base de datos
└── README.md
