# Projeto Biblioteca (API Backend)  Backend 

Este repositório contém a API REST para o projeto da Biblioteca, construída com **Spring Boot**, Java e PostgreSQL.

Esta API é consumida por uma aplicação frontend separada.

## Arquitetura

Este é um projeto full-stack dividido em dois repositórios:

* **Backend (Este repositório):** API REST em Spring Boot.
* **Frontend (Angular e typescript):** 🔗 [Repositório do Frontend](https://github.com/Isaque-Melo/biblioteca-frontend.git)

---

🐳 Como Rodar o Projeto com Docker

Este projeto é dividido em dois repositórios (Frontend e Backend). Para rodar a aplicação completa utilizando Docker, siga os passos abaixo.
Pré-requisitos

    Docker e Docker Compose instalados na sua máquina.

    Git instalado.

Passo a Passo

    Crie uma pasta para o projeto e entre nela:
    Bash

mkdir sistema-biblioteca
cd sistema-biblioteca

Clone os dois repositórios dentro desta pasta: Certifique-se de que as pastas tenham os nomes exatos abaixo para que o Docker as encontre.
Bash

# Clone o Backend
git clone https://github.com/Isaque-Melo/biblioteca-backend.git

# Clone o Frontend
git clone https://github.com/Isaque-Melo/biblioteca-frontend.git

Crie o arquivo docker-compose.yml: Na raiz da pasta sistema-biblioteca (ao lado das pastas dos projetos), crie um arquivo chamado docker-compose.yml com o seguinte conteúdo:
YAML

version: '3.8'

services:
  db:
    image: postgres:15-alpine
    container_name: biblioteca-db
    environment:
      POSTGRES_DB: biblioteca_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432" 
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - biblioteca-net
    restart: always

  backend:
    build: ./biblioteca-backend
    container_name: biblioteca-api
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/biblioteca_db
      DB_USERNAME: postgres
      DB_PASSWORD: postgres
      CORS_ALLOWED_ORIGINS: http://localhost:4200
    depends_on:
      - db
    restart: on-failure
    networks:
      - biblioteca-net

  frontend:
    build: ./biblioteca-frontend
    container_name: biblioteca-ui
    ports:
      - "4200:80"
    depends_on:
      - backend
    networks:
      - biblioteca-net

volumes:
  postgres_data:

networks:
  biblioteca-net:
    driver: bridge

Inicie a aplicação:
Bash

    docker-compose up -d --build

🚀 Acessando a Aplicação

    Frontend: Acesse http://localhost:4200

    API (Swagger/Backend): Acesse http://localhost:8081

    Banco de Dados: localhost:5432 (Usuário/Senha: postgres)

    
    sistema-biblioteca/
├── docker-compose.yml

├── biblioteca-backend/  <-- (Repositório clonado)

│   ├── Dockerfile
│   └── src...
└── biblioteca-frontend/ <-- (Repositório clonado)

    ├── Dockerfile
    └── src...
