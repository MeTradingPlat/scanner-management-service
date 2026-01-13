-- Tipos de ejecución
INSERT INTO tipos_ejecucion (id_tipo_ejecucion, enum_tipo_ejecucion) VALUES (1, 'UNA_VEZ') ON CONFLICT (id_tipo_ejecucion) DO NOTHING;
INSERT INTO tipos_ejecucion (id_tipo_ejecucion, enum_tipo_ejecucion) VALUES (2, 'DIARIA') ON CONFLICT (id_tipo_ejecucion) DO NOTHING;

-- Mercados
INSERT INTO mercados (id_mercado, enum_mercado) VALUES (1, 'NYSE') ON CONFLICT (id_mercado) DO NOTHING;
INSERT INTO mercados (id_mercado, enum_mercado) VALUES (2, 'NASDAQ') ON CONFLICT (id_mercado) DO NOTHING;
INSERT INTO mercados (id_mercado, enum_mercado) VALUES (3, 'AMEX') ON CONFLICT (id_mercado) DO NOTHING;
INSERT INTO mercados (id_mercado, enum_mercado) VALUES (4, 'ETF') ON CONFLICT (id_mercado) DO NOTHING;
INSERT INTO mercados (id_mercado, enum_mercado) VALUES (5, 'OTC') ON CONFLICT (id_mercado) DO NOTHING;