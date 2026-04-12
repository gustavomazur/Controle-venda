# Sistema de Controle de Vendas

Sistema de gerenciamento de vendas com cadastro de clientes, produtos, vendas e autenticação JWT.

## Tecnologias

- **Backend:** Java 17 + Spring Boot 3.4.3
- **Frontend:** HTML, CSS, JavaScript (SPA)
- **Banco de Dados:** MySQL 8.0
- **Upload de Imagens:** Cloudinary
- **Autenticação:** JWT (JSON Web Token)
- **Build:** Maven

## Pré-requisitos

- Java 17+
- Maven 3.8+
- MySQL 8.0

## Instalação

### 1. Clonar o projeto

```bash
git clone https://github.com/gustavomazur/contadora-api.git
cd contadora-api
```

### 2. Instalar e configurar o MySQL

#### No Ubuntu/Linux:
```bash
sudo apt install mysql-server
sudo systemctl start mysql
sudo mysql
```

No MySQL, execute:
```sql
CREATE DATABASE IF NOT EXISTS controle_venda;
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'sua_senha';
FLUSH PRIVILEGES;
EXIT;
```

#### No Windows/Mac:
Baixe o MySQL em https://dev.mysql.com/downloads/mysql/ e siga o instalador.

### 3. Configurar variáveis de ambiente

Copie o arquivo de exemplo:
```bash
cp .env.example .env
```

Edite o `.env` com suas configurações:
```env
SPRING_PROFILES_ACTIVE=dev
CLOUDINARY_CLOUD_NAME=seu_cloud_name
CLOUDINARY_API_KEY=sua_api_key
CLOUDINARY_API_SECRET=sua_api_secret
SPRING_DATASOURCE_USERNAME_DEV=root
SPRING_DATASOURCE_PASSWORD_DEV=sua_senha_mysql
```

### 4. Credenciais do Cloudinary (opcional)

Para ativar o upload de imagens:
1. Acesse https://cloudinary.com/console
2. Crie uma conta gratuita
3. Copie as credenciais para o `.env`

## Rodar o projeto

### Modo desenvolvimento (MySQL local)

```bash
./mvnw spring-boot:run
```

### Modo Docker (MySQL no container)

```bash
docker-compose up -d
```

## Acessar a aplicação

- **Frontend:** http://localhost:8080
- **Cadastro:** http://localhost:8080/cadastro.html
- **Login:** http://localhost:8080/login.html

## Endpoints da API

### Autenticação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/auth/cadastro` | Cadastrar novo usuário |
| POST | `/api/auth/login` | Fazer login |

### Clientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/clientes` | Listar todos os clientes |
| GET | `/api/clientes/{id}` | Buscar cliente por ID |
| POST | `/api/clientes` | Cadastrar cliente |
| PUT | `/api/clientes/{id}` | Atualizar cliente |
| DELETE | `/api/clientes/{id}` | Deletar cliente |

### Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produto` | Listar todos os produtos |
| GET | `/produto/{id}` | Buscar produto por ID |
| POST | `/produto` | Cadastrar produto |
| PUT | `/produto/{id}` | Atualizar produto |
| DELETE | `/produto/{id}` | Deletar produto |

### Vendas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/venda` | Listar todas as vendas |
| POST | `/venda` | Registrar venda |

## Estrutura do projeto

```
src/
├── main/
│   ├── java/br/com/contadora/contadora_api/
│   │   ├── config/          # Configurações (Security, Cloudinary, JWT)
│   │   ├── controller/      # REST Controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── mapper/          # Mapeamento DTO <-> Entidade
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositórios JPA
│   │   └── service/          # Lógica de negócio
│   └── resources/
│       ├── static/          # Arquivos frontend (HTML, CSS, JS)
│       └── application.properties
└── test/
```

## Fluxo de autenticação

1. Usuário se cadastra em `/cadastro.html`
2. Sistema cria usuário com senha criptografada (BCrypt)
3. Usuário faz login em `/login.html`
4. Sistema retorna token JWT (válido por 24h)
5. Token é armazenado no localStorage
6. Todas as requisições para `/api/**`enviam o token no header `Authorization: Bearer <token>`
7. Se token expirar, usuário é redirecionado para login

## Importante

- **NUNCA** commite o arquivo `.env` com dados reais
- O `.gitignore` já protege arquivos sensíveis
- Use `.env.example` como template

## License

MIT License
