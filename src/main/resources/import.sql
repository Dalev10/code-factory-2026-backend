-- Ingresos (Income)
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Salario', 'INCOME', NULL);
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Inversiones', 'INCOME', NULL);
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Otros ingresos', 'INCOME', NULL);

-- Gastos (Expense)
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Vivienda', 'EXPENSE', NULL);
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Alimentación', 'EXPENSE', NULL);
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Transporte', 'EXPENSE', NULL);
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Salud', 'EXPENSE', NULL);
INSERT INTO categories (id, name, type, user_id) VALUES (gen_random_uuid(), 'Entretenimiento', 'EXPENSE', NULL);