# SatAlert API — Java Spring Boot
## Challenge FIAP Global Solution 2026/1

API REST de monitoramento de queimadas e desmatamento via satélite.

---

## Integrantes

| Nome | RM | Turma |
|---|---|---|
| Andrei de Paiva Gibbini | 563061 | 2TDSPF |
| Arthur Câmara | 562310 | 2TDSPG |
| Diogo Cunha | 563654 | 2TDSPF |
| Pedro Santos Pequini | 561842 | 2TDSPF |
| Pedro Sakai Silva Zambaca | 565956 | 2TDSPF |

---

## Links

- **Deploy:** *(em breve)*
- **Swagger:** http://localhost:8080/swagger-ui.html
- **Vídeo demonstração:** *(em breve)*
- **Vídeo pitch:** *(em breve)*

---

## Sobre o Projeto

O SatAlert é uma API REST que recebe dados de sensores IoT (ESP32) e satélites, detecta ocorrências de queimadas e desmatamento em regiões monitoradas e notifica os usuários cadastrados. O sistema classifica automaticamente o nível de risco (BAIXO, MEDIO, CRITICO) com base na temperatura, nível de fumaça e área afetada.

Esta API Java cuida dos alertas e ocorrências. A API .NET (repositório separado) cuida do gerenciamento de usuários e notificações.

---

## Arquitetura

```
ESP32 (sensor) → POST /api/ocorrencias/queimada
                        ↓
                  API Java (Spring Boot)
                        ↓
                  Oracle FIAP
                        ↓
               API .NET (notificações)
```

### Diagrama de Entidades

```
TB_REGIAO (1) ──────────── (N) TB_OCORRENCIA
                                     │
TB_ALERTA (1) ──────────── (N) TB_OCORRENCIA
  ├── AlertaQueimada (herança)
  └── AlertaDesmatamento (herança)

TB_OCORRENCIA (N) ────── (N) TB_SATELITE
         [TB_OCORRENCIA_SATELITE — chave composta]

TB_USUARIO (N) ──────────── (1) TB_REGIAO
TB_NOTIFICACAO (N) ─────── (1) TB_USUARIO
TB_NOTIFICACAO (N) ─────── (1) TB_OCORRENCIA
```

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.3 |
| ORM | Spring Data JPA + Hibernate |
| Segurança | Spring Security + JWT |
| HATEOAS | Spring HATEOAS |
| Documentação | Swagger / OpenAPI 3 |
| Banco | Oracle XE — oracle.fiap.com.br:1521/orcl |
| Produtividade | Lombok, DevTools |

---

## Como Executar

### Pré-requisitos

- Java 21
- Maven
- Acesso ao Oracle FIAP

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/Challange-Vetflow/satalert-java.git
cd satalert-java

# 2. Rode os scripts SQL no SQL Developer na ordem:
#    satalert_ddl.sql → satalert_dml.sql → satalert_plsql.sql

# 3. Rode a aplicação
mvn spring-boot:run

# 4. Acesse o Swagger
# http://localhost:8080/swagger-ui.html
```

### Configuração do banco (`application.properties`)

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=RM562310
spring.datasource.password=270905
spring.jpa.hibernate.ddl-auto=none
spring.main.allow-circular-references=true
```

---

## Endpoints

### Autenticação

| Método | Rota | Descrição |
|---|---|---|
| POST | /api/auth/registro | Cria novo usuário |
| POST | /api/auth/login | Autentica e retorna JWT |

### Regiões (requer token)

| Método | Rota | Descrição |
|---|---|---|
| GET | /api/regioes | Lista com paginação |
| GET | /api/regioes/{id} | Busca por ID (HATEOAS) |
| POST | /api/regioes | Cria região |
| PUT | /api/regioes/{id} | Atualiza |
| DELETE | /api/regioes/{id} | Remove |

### Ocorrências (requer token)

| Método | Rota | Descrição |
|---|---|---|
| GET | /api/ocorrencias | Lista com paginação |
| GET | /api/ocorrencias/{id} | Busca por ID (HATEOAS) |
| POST | /api/ocorrencias/queimada | Registra queimada |
| POST | /api/ocorrencias/desmatamento | Registra desmatamento |
| PATCH | /api/ocorrencias/{id}/resolver | Marca como resolvida |
| DELETE | /api/ocorrencias/{id} | Remove |

### Usuários (requer token)

| Método | Rota | Descrição |
|---|---|---|
| GET | /api/usuarios | Lista todos |
| GET | /api/usuarios/{id} | Busca por ID (HATEOAS) |
| PUT | /api/usuarios/{id} | Atualiza |
| DELETE | /api/usuarios/{id} | Remove |

---

## Exemplos de Request/Response

### POST /api/auth/registro

**Request:**
```json
{
  "nome": "Arthur Câmara",
  "email": "arthur@satalert.com",
  "senha": "senha123",
  "telefone": "11999999999"
}
```

**Response 201:**
```json
{
  "id": 1,
  "nome": "Arthur Câmara",
  "email": "arthur@satalert.com",
  "telefone": "11999999999",
  "role": "ROLE_USER",
  "dtCadastro": "2026-05-30"
}
```

### POST /api/auth/login

**Response 200:**
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9...",
  "tipo": "Bearer",
  "expiracao": 86400000
}
```

### POST /api/ocorrencias/queimada

**Request** (com `Authorization: Bearer {token}`):
```json
{
  "idRegiao": 1,
  "temperatura": 120,
  "nivelFumaca": 100
}
```

**Response 201:**
```json
{
  "id": 1,
  "nomeRegiao": "Amazônia Central",
  "estado": "Amazonas",
  "tipoAlerta": "QUEIMADA",
  "nivelRisco": "CRITICO",
  "status": "ATIVO",
  "dtOcorrencia": "2026-05-30",
  "dtResolucao": null
}
```

### GET /api/ocorrencias/{id} — com HATEOAS

**Response 200:**
```json
{
  "id": 1,
  "nomeRegiao": "Amazônia Central",
  "tipoAlerta": "QUEIMADA",
  "nivelRisco": "CRITICO",
  "status": "ATIVO",
  "_links": {
    "self": { "href": "http://localhost:8080/api/ocorrencias/1" },
    "ocorrencias": { "href": "http://localhost:8080/api/ocorrencias" },
    "resolver": { "href": "http://localhost:8080/api/ocorrencias/1/resolver" }
  }
}
```

---

## Modelagem Avançada

- **Herança:** `AlertaBase` → `AlertaQueimada` e `AlertaDesmatamento` com `@Inheritance` e `@DiscriminatorValue`
- **Chave composta:** `TB_OCORRENCIA_SATELITE` com `@EmbeddedId`
- **Embedded:** `Coordenada` embutida em `TB_REGIAO`
- **Segurança:** Spring Security com JWT stateless, `@PreAuthorize` nos endpoints