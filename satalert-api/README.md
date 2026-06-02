# SatAlert API

Sistema de monitoramento de queimadas e desmatamento via satélite — FIAP Global Solution 2026/1

---

## Integrantes

| Nome | RM | Turma |
|------|----|-------|
| Integrante 1 | RM000000 | 2TDS |
| Integrante 2 | RM000000 | 2TDS |
| Integrante 3 | RM000000 | 2TDS |
| Integrante 4 | RM000000 | 2TDS |
| Integrante 5 | RM000000 | 2TDS |

---

## Links

- **Deploy:** https://satalert.up.railway.app
- **Swagger:** https://satalert.up.railway.app/swagger-ui.html
- **Vídeo demonstração:** https://youtu.be/...
- **Vídeo pitch:** https://youtu.be/...

---

## Sobre o projeto

O SatAlert é uma API REST que recebe dados de sensores IoT (ESP32) e satélites, detecta ocorrências de queimadas e desmatamento em regiões monitoradas e notifica os usuários cadastrados. O sistema classifica automaticamente o nível de risco (BAIXO, MEDIO, CRITICO) com base na temperatura, nível de fumaça e área afetada.

---

## Arquitetura

```
ESP32 (sensor) → POST /api/ocorrencias/queimada
                        ↓
                  API Java (Spring Boot)
                        ↓
                  Oracle / PostgreSQL
                        ↓
               App Mobile (React Native)
```

### Diagrama de entidades

```
TB_REGIAO (1) ──────────── (N) TB_OCORRENCIA
                                     │
TB_ALERTA (1) ──────────── (N) TB_OCORRENCIA
  ├── AlertaQueimada
  └── AlertaDesmatamento

TB_OCORRENCIA (N) ────── (N) TB_SATELITE
         [TB_OCORRENCIA_SATELITE — chave composta]

TB_USUARIO (N) ──────────── (1) TB_REGIAO
```

---

## Tecnologias

- Java 21
- Spring Boot 3.3
- Spring Data JPA + Hibernate
- Spring Security + JWT
- Spring HATEOAS
- Swagger / OpenAPI 3
- Oracle XE (local) / PostgreSQL (produção)
- Lombok

---

## Como executar localmente

### Pré-requisitos
- Java 21
- Maven
- Oracle XE rodando com schema `satalert`

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/arthurscamara/satalert-api.git
cd satalert-api

# 2. Rode os scripts SQL no SQL Developer na ordem:
#    satalert_ddl.sql → satalert_dml.sql → satalert_plsql.sql

# 3. Configure o banco em src/main/resources/application.properties
#    spring.datasource.username=satalert
#    spring.datasource.password=satalert123

# 4. Rode a aplicação
mvn spring-boot:run

# 5. Acesse o Swagger
# http://localhost:8080/swagger-ui.html
```

---

## Endpoints principais

### Autenticação

```http
POST /api/auth/registro
Content-Type: application/json

{
  "nome": "Carlos Souza",
  "email": "carlos@email.com",
  "senha": "123456",
  "telefone": "11999990001"
}
```

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "carlos@email.com",
  "senha": "123456"
}

# Resposta:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiracao": 86400000
}
```

### Ocorrências (requer Bearer token)

```http
POST /api/ocorrencias/queimada
Authorization: Bearer {token}
Content-Type: application/json

{
  "idRegiao": 1,
  "temperatura": 87.3,
  "nivelFumaca": 92.1
}

# Resposta 201:
{
  "id": 21,
  "nomeRegiao": "Pantanal Sul",
  "estado": "MT",
  "tipoAlerta": "QUEIMADA",
  "nivelRisco": "CRITICO",
  "status": "ATIVO",
  "dtOcorrencia": "2026-06-01",
  "dtResolucao": null
}
```

```http
GET /api/ocorrencias?status=ATIVO&page=0&size=10
Authorization: Bearer {token}
```

```http
GET /api/ocorrencias/1
Authorization: Bearer {token}

# Resposta com HATEOAS:
{
  "id": 1,
  "nomeRegiao": "Pantanal Sul",
  ...
  "_links": {
    "self": { "href": "http://localhost:8080/api/ocorrencias/1" },
    "ocorrencias": { "href": "http://localhost:8080/api/ocorrencias" },
    "resolver": { "href": "http://localhost:8080/api/ocorrencias/1/resolver" }
  }
}
```

```http
PATCH /api/ocorrencias/1/resolver
Authorization: Bearer {token}
```

```http
DELETE /api/ocorrencias/1
Authorization: Bearer {token}
```

### Regiões

```http
POST /api/regioes
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Cerrado Goiano",
  "estado": "GO",
  "bioma": "Cerrado",
  "latitude": -15.7801,
  "longitude": -47.9292
}
```

---

## Deploy no Railway

O projeto usa o profile `prod` em produção, que conecta automaticamente no PostgreSQL provisionado pelo Railway via variável `DATABASE_URL`.

Variáveis de ambiente configuradas no Railway:
- `SPRING_PROFILES_ACTIVE=prod`
- `DATABASE_URL` (injetada automaticamente pelo Railway)
- `JWT_SECRET` (chave segura gerada manualmente)
