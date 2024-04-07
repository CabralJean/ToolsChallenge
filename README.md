# ToolsChallenge
ToolsChallenge

# Documentação do Projeto

Este é um exemplo de documentação para um projeto que inclui código de teste em Java usando Spring Boot e Spring MVC.

Para o projeto foram utilizados os princípios do Clean Code, mantendo um conjunto de boas práticas para facilitar a leitura e menutenção do código.
A API teve como utilização os conceitos REST, JSON e protocolo padrão HTTP de retorno.
O servidor da aplicação utilizado foi o SpringBoot.
Foi utilizado o banco de dados em memória `H2` para guardar as transações.
Para persistir os dados foi utilizado o `Spring JPA`.


## Descrição do Projeto

O projeto tem como objetivo implementar uma API de Pagamentos.
As operações serão as seguintes:
- Pagamento:
- Solicitação e resposta
- Estorno:
- Efetiva o estorno pelo ID
- Consulta:
- consulta: todos e por ID


## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring MVC
- JUnit
- Mockito
- H2 Database
- Spring JPA


## Configuração do Ambiente

Para configurar o ambiente de desenvolvimento, siga estas etapas:

1. Clone o repositório do projeto.
2. Certifique-se de ter o Java 17 instalado em sua máquina.
3. Instale o Gradle.
4. Instale o Lombok na sua IDE
4. Execute `./gradlew build` para construir o projeto.


## Exemplos de Uso

Execute a aplicação em seguida acesse o link `http://localhost:8080/swagger-ui/index.html`.
Lá será encontrada toda documentação do projeto.
Também poderão ser executadas as funcionalidades do projeto.
