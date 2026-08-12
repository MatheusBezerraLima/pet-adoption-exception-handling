# Pet Adoption Exception Handling
 
API REST para gerenciamento de adoção de pets, desenvolvida como parte de um módulo de estudos da Alura focado inteiramente em tratamento de exceções no Spring Boot. O projeto simula o backend de um sistema real de adoção — cadastro de tutores, cadastro de pets com upload de imagem e o fluxo completo de solicitação, aprovação e reprovação de adoções — usado como base prática para exceções customizadas, lançamentos controlados e captura centralizada de erros.
 
## 🔻Sobre o projeto
 
O domínio da aplicação (Adopet) já existia como exercício de API REST, mas aqui ele foi usado como veículo para aplicar, na prática, os conceitos do módulo: criação de exceções próprias da regra de negócio, o momento certo de lançá-las dentro da camada de serviço e a definição de uma classe global com `@RestControllerAdvice` que intercepta essas exceções antes que cheguem ao cliente, padronizando o formato da resposta de erro.
 
A regra de negócio de adoção concentra a maior parte da lógica de validação: um pet não pode ser adotado se já estiver adotado, se já tiver uma solicitação em andamento aguardando avaliação, ou se o tutor solicitante já tiver duas adoções em andamento. Cada uma dessas violações lança uma `AdocaoException`, capturada pelo `GlobalExceptionHandler` e transformada em uma resposta HTTP 400 com mensagem, status e timestamp.
 
## 🔻Tecnologias
 
- Java 17
- Spring Boot 3.1.4
- Spring Data JPA
- Spring Validation (Bean Validation)
- MySQL
- Flyway (versionamento de schema)
- Lombok
- Maven
## 🔻Arquitetura
 
O projeto segue divisão em camadas: `controller`, `service`, `repository`, `model`, `dto` e `exception`. Os controllers recebem a requisição e delegam a regra de negócio aos services; os services validam as regras de domínio e lançam exceções quando necessário; e o `GlobalExceptionHandler`, anotado com `@RestControllerAdvice`, centraliza a captura dessas exceções para toda a aplicação, evitando tratamento repetido em cada endpoint.
 
```
adopet.api
├── controller     # endpoints REST (Pet, Tutor, Adocao)
├── service        # regras de negócio e lançamento de exceções
├── repository     # interfaces Spring Data JPA
├── model           # entidades JPA
├── dto            # objetos de entrada e saída dos endpoints
└── exception      # exceção customizada e handler global
```
 
O tratamento de exceções tem dois níveis no `GlobalExceptionHandler`: um handler específico para `AdocaoException`, cobrindo as violações de regra de negócio, e um handler genérico para `Exception`, garantindo que qualquer erro não previsto também retorne uma resposta padronizada em vez de vazar detalhes internos ou uma stack trace ao cliente.
 
## 🔻Funcionalidades
 
- Cadastro e listagem de tutores
- Cadastro de pets, com upload de imagem via `multipart/form-data`
- Solicitação de adoção, com validação das regras de negócio (pet já adotado, adoção em andamento, limite de adoções por tutor)
- Aprovação e reprovação de solicitações de adoção
- Respostas de erro padronizadas para toda a API através de captura global de exceções
## 🔻Destaques de aprendizado
 
Este módulo teve como foco exclusivo o tratamento de exceções, então os principais aprendizados aplicados aqui foram:
 
- Criar uma exceção customizada (`AdocaoException`) que representa uma violação de regra de negócio, em vez de reaproveitar exceções genéricas do Java
- Lançar a exceção no ponto exato da camada de serviço onde a regra é violada, mantendo o controller livre de lógica de validação
- Centralizar a captura de exceções com `@RestControllerAdvice`, eliminando blocos try/catch repetidos em cada controller
- Diferenciar o tratamento por tipo de exceção usando múltiplos métodos `@ExceptionHandler`, do mais específico ao mais genérico
- Padronizar o corpo da resposta de erro (`ResponseErro`) com mensagem, status HTTP e timestamp, tornando os erros da API previsíveis para quem consome
## 🔻Como executar
 
O projeto usa MySQL como banco de dados e Flyway para aplicar as migrations automaticamente na primeira execução.
 
1. Crie um banco MySQL local (ou ajuste a URL em `application.properties`)
2. Configure `spring.datasource.username` e `spring.datasource.password` em `src/main/resources/application.properties`
3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```
 
A coleção do Postman incluída no repositório (`Adopet.postman_collection.json`) traz os endpoints já configurados para teste.
