# 🛒 Shopping Cart – Microservices Architecture

Este projeto é um sistema de e-commerce modular desenvolvido com arquitetura de microsserviços, utilizando **Spring Boot 3**, **Eureka Discovery**, **RabbitMQ**, **MySQL (Docker)** e comunicação via **REST + Mensageria**.  
O objetivo é oferecer uma estrutura sólida para um carrinho de compras moderno, escalável e com serviços independentes.

---

## 🚧 Status do Projeto
- **Projeto em construção** — novas features são adicionadas regularmente.  
- **Testes unitários:** JUnit 5 + Mockito.

---

## 📌 Repositório
🔗 https://github.com/MurilloNS/shopping-cart

---

## 📐 Arquitetura

```
                     +---------+---------+
                     |       Eureka      |
                     |  Service Registry |
                     +---------+---------+
                               |
        -----------------------------------------------------
        |             |               |             |
+-------+-----+ +-----+-------+ +-----+-------+ +---+--------+
| ms-users    | | ms-profiles | | ms-products | | ms-orders  |
+-------------+ +-------------+ +-------------+ +------------+
| Auth/JWT    | | Perfil/End. | | Produtos    | | Pedidos    |
| Login       | | Dados do    | | Estoque     | | Checkout   |
| Registro    | | usuário     | | Imagens     | | Carrinho   |
+-------------+ +-------------+ +-------------+ +------------+
                 \           |          |          /
                   \         |          |         /
                 +-------------------------+
                 |        RabbitMQ         |
                 +-------------------------+
```

---

## 🧩 Microsserviços

### 🔐 **ms-users**
- Registro de usuários  
- Login  
- Autenticação com JWT  
- Persistência em MySQL  

---

### 👤 **ms-profiles**
- Gerenciamento de perfis  
- Endereços de entrega  
- Associação de usuários  
- CRUD completo  

---

### 📦 **ms-products**
- Cadastro de produtos  
- Upload de imagens (MultipartFormData)  
- Estoque  
- Controle de status (ativo/inativo)  

---

### 🧾 **ms-orders**
- Criação de pedidos  
- Cancelamento  
- Listagem por usuário  
- Integração com RabbitMQ para eventos futuros (pagamentos, estoque, etc.)  

---

## 🛰️ Eureka Service Registry

Gerencia a descoberta automática dos microsserviços.

Exemplo de dependência (já no projeto):

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

---

## 🗄️ Banco de Dados & Mensageria

### **MySQL (Docker)**
Cada microsserviço possui seu próprio schema.

### **Adminer**
Interface web:  
➡️ http://localhost:8080

### **RabbitMQ**
Painel administrativo:  
➡️ http://localhost:15672 (guest / guest)

---

## 🐳 Docker Compose

```yaml
version: '3.8'

services:
  mysql_db:
    image: mysql:latest
    container_name: mysql_db
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: shopping_db
      MYSQL_USER: admin
      MYSQL_PASSWORD: admin
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - shopping_network

  adminer:
    image: adminer
    container_name: adminer
    ports:
      - "8080:8080"
    networks:
      - shopping_network
    depends_on:
      - mysql_db

  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq_service
    restart: always
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    ports:
      - "5672:5672"
      - "15672:15672"
    networks:
      - shopping_network

volumes:
  mysql_data:

networks:
  shopping_network:
    driver: bridge
    name: shopping_network
```

---

## 🧪 Testes

Os microsserviços utilizam:  
- **JUnit 5**  
- **Mockito**

Testes focados em:  
✔️ Service layer  
✔️ Controllers  
✔️ Regras de negócio  
✔️ Validações  

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|-----------|-----|
| Java 17 | Linguagem principal |
| Spring Boot 3 | Base dos microsserviços |
| Spring Cloud Netflix Eureka | Service Discovery |
| Spring Security + JWT | Autenticação |
| Spring Data JPA | Persistência |
| RabbitMQ | Mensageria |
| MySQL | Banco relacional |
| Adminer | Interface do banco |
| Swagger / SpringDoc | Documentação REST |
| Docker + Compose | Infraestrutura |
| JUnit 5 + Mockito | Testes |

---

## ▶️ Como Executar

### **1️⃣ Suba a infraestrutura**
```bash
docker compose up -d
```

### **2️⃣ Inicie o Eureka Server**
```bash
mvn spring-boot:run -pl ms-eureka
```

### **3️⃣ Inicie cada microsserviço**
```bash
mvn spring-boot:run -pl ms-users
mvn spring-boot:run -pl ms-profiles
mvn spring-boot:run -pl ms-products
mvn spring-boot:run -pl ms-orders
```

---

## 🧑‍💻 Autor

Ollirum (Murillo N. S.) <br>
Desenvolvedor Java | Microsserviços | Spring Boot

---

## 📄 Licença

Este projeto está sob a licença MIT.
