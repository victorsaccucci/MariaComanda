-- Tabela de tipos de usuário
CREATE TABLE user_type (
    id UUID PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE
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
