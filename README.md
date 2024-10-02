# Application Web Notification

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-000?style=for-the-badge&logo=postgresql)
![Vscode](https://img.shields.io/badge/Vscode-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-000?style=for-the-badge&logo=linux&logoColor=FCC624)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

## Apresentação do Projeto 
Este projeto é um sistema de vendas de produtos desenvolvido em Java com Spring, focado exclusivamente no backend. O sistema permite que os usuários se cadastrem, visualizem seus pedidos e recebam notificações. Além disso, os usuários podem optar por não receber mais notificações ou agendar essas notificações conforme desejarem.

## Funcionalidades Principais 
- Cadastro de Usuário: Permite que novos usuários se cadastrem no sistema.
- Visualização de Pedidos: Os usuários podem consultar suas pedidos anteriores.
- Notificação de Prazo de Entrega: Envio de notificações aos usuários sobre o prazo de entrega de seus pedidos pagos.
- Gerenciamento de Notificações: Os usuários podem optar por não receber mais notificações ou agendar a frequência das mesmas.

## Domain Model 
![domainModel](https://github.com/user-attachments/assets/b4f39efc-a345-4b0f-b401-e793b1945dd3)

## Como Executar o Projeto

1. Inicie a aplicação com o maven

```
 mvn clean package -DskipTests
```
2. Rode o comando para buildar a imagem com docker compose

```
 docker-compose build
```
3. Suba a aplicação 

```
 docker-compose up
```

4. A API estará acessível em http://localhost:8080

## API Endpoints 

Para realizar as requisições, primeiro terá de logar para pegar o token, já que se trata de uma sessão STATELESS.

```
// Token de exemplo
 {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6InplIiwiZXhwIjoxNzI3MTYwMTE4fQ.RNbPVNF1T4i2OfkP3VVf-N-_oRTcKZNIKe-OiKaCV08"
 }
```

Aqui estão alguns exemplos de requisições para o nosso projeto:

- POST /auth/register -Registro de usuário (Permission All).

- POST /auth/login - Login do usuário (Permission All).

- GET /users - Listagem de usuários (Authenticated ADMIN). 

- DELETE /users/${id} - Apagar usuário (Authenticated ADMIN).

- GET /orders - Listagem de pedidos (Authenticated USER).

- GET /orders/${id} - Listagem de pedidos por usuário (Authenticated USER).

- POST /orders/register - Registrar novo pedido (Authenticated USER).

- POST /products - Registrar novo produto (Authenticated ADMIN).

- PUT /users/${id}/optout - Cancelar envio de notificação (Authenticated USER).

- POST /notification_preferences/ - Agendar notificação (Authenticated USER).

- DELETE /notificacoes/cancel/${id} - Cancelar agendamento (Authenticated USER).

## Body Request para requisições POST e PUT
 - Register
```
     {
          "login": "jose",
          "password": "senha123",
          "role": "USER",
          "name": "José",
          "email": "jose@example.com",
          "cell": "123456789"
     }
```
 - Login 
```
    {
        "login": "ze",
        "password": "8989"
    }
```
- Registrar novo pedido 
```
    {
        "items": [
            {
                "product": {
                    "id": 2
                },
                "quantity": 2
            }
        ]
    }
```
- Cancelar envio de notificação 

```
    {
        "optOut": true
    }
```
- Agendar notificação 
```
    {
        "userId": 3,
        "orderId": 1,
        "scheduledTime": "2024-09-30T14:30:00",
        "optOut": true
    }
```
- Criar novo produto
````
    {
        "name": "IPhone 16 pro Max",
        "description": "IPhone 15 pro Max 256Gb",
        "price": 9000.0,
        "imgUrl": "www.jose.com.br/iphone15"
    }
````

### Observação
Na class Config que está dentro do package config, deixei descomentado alguns objetos instanciados que facilitaram na visualização da aplicação com os campos já preenchidos, lembre-se de comentar caso rode novamente.





