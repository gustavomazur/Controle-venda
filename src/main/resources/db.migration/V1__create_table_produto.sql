CREATE TABLE produto (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         descricao TEXT,
                         quantidade INT NOT NULL,
                         categoria VARCHAR(100),
                         precoquepaguei DECIMAL(10,2) NOT NULL,
                         precodevenda DECIMAL(10,2) NOT NULL,
                         imagem VARCHAR(255)
);
