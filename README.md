# Sharkar Back-end

Essa é a API do projeto https://www.sharkar-pv.com/. Ela é responsável por fazer toda a intermediação das operações realizadas, seja como um gateway para a api Fipe, seja para persistência no banco e também, principalmente, para toda lógica de autenticação dos usuários do sistema.

Caso queira conferir um pouco de como foi a trajetória de construção desse projeto, fique a vontade: https://pvrosendo.vercel.app/article/sharkar-article

## Table of Contents

- [Instalação](#instalação)
- [Technologies](#technologies)
- [Endpoints da API](#endpoints)


## Technologies

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)

## Endpoints

### Authentication

| rota               | descrição                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /sharkar/auth/signin</kbd>      |  Fazer login
| <kbd>PUT /sharkar/auth/refresh/{username}</kbd>      |  Obter access_token através do refresh token
| <kbd>POST /sharkar/auth/signout</kbd>     |  Deslogar e limpar cookies
| <kbd>GET /sharkar/auth/validate-token</kbd>      |   Validação do token utilizado
| <kbd>POST /sharkar/auth/createUser</kbd> |   Criação de um novo usuário
| <kbd>GET /sharkar/auth/user/{username}</kbd>   |  Buscar informações principais do usuário
| <kbd>GET /sharkar/auth/user/updateInfo</kbd>   |  Atualizar informações do usuário
| <kbd>GET /sharkar/auth/user/updatePassword</kbd>   |  Atualizar senha do usuário


### Consult External API

| rota               | descrição                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /sharkar/fipe/brands</kbd>      |  Busca todas as marcas de carros
| <kbd>GET /sharkar/fipe/models</kbd>      |  Busca todos os modelos registrados de determinada marca
| <kbd>GET /sharkar/fipe/years</kbd>     |  Busca os anos do modelo da marca
| <kbd>GET /sharkar/fipe/info</kbd>      |   Traz as informações completas do modelo de determinado ano


### Records of user

| rota               | descrição                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /sharkar</kbd>      |  Registrar um novo carro
| <kbd>GET /sharkar</kbd>      |  Buscar todos os carros registrados
| <kbd>DELETE /sharkar</kbd>     |  Deletar o registro de um carro


## Colaboração

Caso tenha alguma intenção de ajudar ou colaborar com o projeto de alguma forma, sinta-se livre para fazer um PR que eu estarei analisando. Obrigado, tmj.