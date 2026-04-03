-- =============================================================================
-- SEED DATA: CATEGORÍAS GLOBALES (MAESTRAS)
-- Nota: Usamos IDs fijos y ON CONFLICT para garantizar idempotencia.
-- =============================================================================

-- CATEGORÍAS DE INGRESO (INCOME)
INSERT INTO categories (id, name, type, user_id) 
VALUES ('a1b2c3d4-e5f6-7890-abcd-111111111111', 'Salario', 'INCOME', NULL)
ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (id, name, type, user_id) 
VALUES ('a1b2c3d4-e5f6-7890-abcd-222222222222', 'Inversiones', 'INCOME', NULL)
ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (id, name, type, user_id) 
VALUES ('a1b2c3d4-e5f6-7890-abcd-333333333333', 'Otros ingresos', 'INCOME', NULL)
ON CONFLICT (name) DO NOTHING;


-- CATEGORÍAS DE GASTO (EXPENSE)
INSERT INTO categories (id, name, type, user_id) 
VALUES ('b2c3d4e5-f6a7-8901-bcde-444444444444', 'Vivienda', 'EXPENSE', NULL)
ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (id, name, type, user_id) 
VALUES ('b2c3d4e5-f6a7-8901-bcde-555555555555', 'Alimentación', 'EXPENSE', NULL)
ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (id, name, type, user_id) 
VALUES ('b2c3d4e5-f6a7-8901-bcde-666666666666', 'Transporte', 'EXPENSE', NULL)
ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (id, name, type, user_id) 
VALUES ('b2c3d4e5-f6a7-8901-bcde-777777777777', 'Salud', 'EXPENSE', NULL)
ON CONFLICT (name) DO NOTHING;

INSERT INTO categories (id, name, type, user_id) 
VALUES ('b2c3d4e5-f6a7-8901-bcde-888888888888', 'Entretenimiento', 'EXPENSE', NULL)
ON CONFLICT (name) DO NOTHING;