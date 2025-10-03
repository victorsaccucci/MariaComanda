-- Tabela de tipos de usuário
CREATE TABLE user_type (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    sub_type VARCHAR(30) NOT NULL
);

-- Tabela de usuários
CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    user_type_id UUID NOT NULL,
    FOREIGN KEY (user_type_id) REFERENCES user_type(id)
);

-- Tabela de restaurantes
CREATE TABLE restaurant (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    address TEXT NOT NULL,
    cuisine_type VARCHAR(80) NOT NULL,
    opening_hours VARCHAR(120),
    owner_user_id UUID NOT NULL,
    FOREIGN KEY (owner_user_id) REFERENCES app_user(id)
);

-- Tabela de itens do cardápio
CREATE TABLE menu_item (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    dine_in_only BOOLEAN NOT NULL,
    photo_path VARCHAR(255),
    restaurant_id UUID NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

-- Inserção dos tipos de usuário (name descritivo + sub_type técnico)
INSERT INTO user_type (id, name, sub_type) VALUES
    ('c37d661d-7e61-49ea-96a5-68c34e83db3a', 'Owner', 'OWNER'),
    ('a68a8d55-92c7-4c4d-a41a-1f43b47f7f5c', 'Customer', 'CUSTOMER');

-- Inserção de usuários (um owner e um customer)
INSERT INTO app_user (id, name, email, password_hash, user_type_id) VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'João Silva', 'joao@restaurante.com',
     '$2a$12$1234567890123456789012uabcdefghijklmnopqrstuvwxyzABCDEF',
     'c37d661d-7e61-49ea-96a5-68c34e83db3a'),
    ('550e8400-e29b-41d4-a716-446655440000', 'Maria Santos', 'maria@email.com',
     '$2a$12$abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123',
     'a68a8d55-92c7-4c4d-a41a-1f43b47f7f5c');

-- Inserção de restaurante
INSERT INTO restaurant (id, name, address, cuisine_type, opening_hours, owner_user_id) VALUES
    ('7d793cc8-8f0d-4a3b-8690-0c66b62eb0ec', 'Restaurante do João',
     'Rua das Flores, 123 - Centro', 'Brasileira',
     'Seg-Sex: 11h-22h, Sáb-Dom: 11h-23h',
     'f47ac10b-58cc-4372-a567-0e02b2c3d479');

-- Inserção de itens do cardápio
INSERT INTO menu_item (id, name, description, price, dine_in_only, photo_path, restaurant_id) VALUES
    ('9c17d6b3-8f8d-4e3a-95a5-f06d47c839ab', 'Feijoada Completa',
     'Feijoada tradicional com acompanhamentos', 45.90, true,
     '/photos/feijoada.jpg', '7d793cc8-8f0d-4a3b-8690-0c66b62eb0ec'),
    ('b5d5c7d8-21e4-4f4d-9d4e-12c8f6544563', 'Moqueca de Peixe',
     'Moqueca de pescada com arroz e pirão', 52.90, false,
     '/photos/moqueca.jpg', '7d793cc8-8f0d-4a3b-8690-0c66b62eb0ec');
