-- CREACIÓN DE MODULOS
INSERT INTO module (name, base_path)
VALUES ('PRODUCT', '/products');
INSERT INTO module (name, base_path)
VALUES ('CATEGORY', '/categories');
INSERT INTO module (name, base_path)
VALUES ('CUSTOMER', '/customers');
INSERT INTO module (name, base_path)
VALUES ('AUTH', '/auth');

-- CREACIÓN
INSERT INTO module (name, base_path)
VALUES ('PERMISSION', '/permissions');


-- CREACIÓN DE OPERACIONES
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_ALL_PRODUCTS', '', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_ONE_PRODUCT', '/[0-9]*', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('CREATE_ONE_PRODUCT', '', 'POST', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('UPDATE_ONE_PRODUCT', '/[0-9]*', 'PUT', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('DISABLE_ONE_PRODUCT', '/[0-9]*/disabled', 'PUT', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('ENABLE_ONE_PRODUCT', '/[0-9]*/enabled', 'PUT', false, 1);

INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_ALL_CATEGORIES', '', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_ONE_CATEGORY', '/[0-9]*', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('CREATE_ONE_CATEGORY', '', 'POST', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('UPDATE_ONE_CATEGORY', '/[0-9]*', 'PUT', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('DISABLE_ONE_CATEGORY', '/[0-9]*/disabled', 'PUT', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('ENABLE_ONE_CATEGORY', '/[0-9]*/enabled', 'PUT', false, 2);


INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_ALL_CUSTOMERS', '', 'GET', false, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('REGISTER_ONE', '', 'POST', true, 3);

INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('AUTHENTICATE', '/authenticate', 'POST', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('VALIDATE-TOKEN', '/validate-token', 'GET', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_MY_PROFILE', '/profile', 'GET', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('LOGOUT', '/logout', 'POST', true, 4);

-- CREACIÓN DE OPERACIONES DE MÓDULO
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_ALL_PERMISSIONS', '', 'GET', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('READ_ONE_PERMISSION', '/[0-9]*', 'GET', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('CREATE_ONE_PERMISSION', '', 'POST', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id)
VALUES ('DELETE_ONE_PERMISSION', '/[0-9]*', 'DELETE', false, 5);


-- CREACIÓN DE ROLES
INSERT INTO role (name)
VALUES ('CUSTOMER');
INSERT INTO role (name)
VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO role (name)
VALUES ('ADMINISTRATOR');

-- CREACIÓN DE PERMISOS
INSERT INTO granted_permission (role_id, operation_id)
VALUES (1, 15);

INSERT INTO granted_permission (role_id, operation_id)
VALUES (2, 1);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (2, 2);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (2, 4);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (2, 6);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (2, 7);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (2, 9);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (2, 15);

INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 1);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 2);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 3);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 4);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 5);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 6);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 7);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 8);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 9);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 10);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 15);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 21);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 5);

INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 17);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 18);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 19);
INSERT INTO granted_permission (role_id, operation_id)
VALUES (3, 20);


-- CREACIÓN DE USUARIOS
INSERT INTO user (username, name, password, role_id)
VALUES ('cliente', 'carlos flores', '$2a$10$CvkZPA3dJxTsiEVIp283X.8RnmxN4.1vyo732cReVKZXKjJB00IPa', 1);
INSERT INTO user (username, name, password, role_id)
VALUES ('asistente', 'fulano pérez', '$2a$10$CvkZPA3dJxTsiEVIp283X.8RnmxN4.1vyo732cReVKZXKjJB00IPa', 2);
INSERT INTO user (username, name, password, role_id)
VALUES ('administrador', 'mengano hernández', '$2a$10$CvkZPA3dJxTsiEVIp283X.8RnmxN4.1vyo732cReVKZXKjJB00IPa', 3);

-- CREACIÓN DE CATEGORIAS
INSERT INTO category (name, status)
VALUES ('Electrónica', 'ENABLED');
INSERT INTO category (name, status)
VALUES ('Ropa', 'ENABLED');
INSERT INTO category (name, status)
VALUES ('Deportes', 'ENABLED');
INSERT INTO category (name, status)
VALUES ('Hogar', 'ENABLED');

-- CREACIÓN DE PRODUCTOS
INSERT INTO product (name, price, status, category_id)
VALUES ('Smartphone', 500.00, 'ENABLED', 1);
INSERT INTO product (name, price, status, category_id)
VALUES ('Auriculares Bluetooth', 50.00, 'DISABLED', 1);
INSERT INTO product (name, price, status, category_id)
VALUES ('Tablet', 300.00, 'ENABLED', 1);

INSERT INTO product (name, price, status, category_id)
VALUES ('Camiseta', 25.00, 'ENABLED', 2);
INSERT INTO product (name, price, status, category_id)
VALUES ('Pantalones', 35.00, 'ENABLED', 2);
INSERT INTO product (name, price, status, category_id)
VALUES ('Zapatos', 45.00, 'ENABLED', 2);

INSERT INTO product (name, price, status, category_id)
VALUES ('Balón de Fútbol', 20.00, 'ENABLED', 3);
INSERT INTO product (name, price, status, category_id)
VALUES ('Raqueta de Tenis', 80.00, 'DISABLED', 3);

INSERT INTO product (name, price, status, category_id)
VALUES ('Aspiradora', 120.00, 'ENABLED', 4);
INSERT INTO product (name, price, status, category_id)
VALUES ('Licuadora', 50.00, 'ENABLED', 4);
