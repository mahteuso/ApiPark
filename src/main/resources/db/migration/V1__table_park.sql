CREATE TABLE usuarios (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    role VARCHAR(25),
    data_criacao DATE,
    data_modificacao DATE,
    criado_por VARCHAR(255),
    modificado_por VARCHAR(255)
);
