# SpringSecurityRBAC


# Projeto Spring Security com RBAC (Role-Based Access Control)
Este projeto demonstra a implementação de controle de acesso baseado em papéis (RBAC) em uma aplicação Spring Boot, utilizando Spring Security e JSON Web Tokens (JWT). O objetivo é estender um sistema de autenticação básico para incluir autorização granular para diferentes tipos de usuários (ex: USER e ADMIN).

# Funcionalidades Implementadas
Autenticação de usuários via JWT.

Registro de novos usuários.

Implementação de papéis (Roles) para usuários (ex: ROLE_USER, ROLE_ADMIN).

Controle de acesso a endpoints baseado no papel do usuário.

Endpoints de teste para validar o acesso de USER e ADMIN.

# Visão Geral da Implementação
A implementação do RBAC seguiu os seguintes passos principais:

Enum de Papéis (Role): Criação de um enum para definir os papéis disponíveis (ex: USER, ADMIN).

Atualização da Entidade User: Adição de um atributo Set<Role> à entidade User. Foram utilizadas as anotações @Enumerated(EnumType.STRING), @ElementCollection e @CollectionTable para mapear a relação. O método getAuthorities() foi atualizado para converter os Roles em GrantedAuthority para o Spring Security.

Migração de Banco de Dados (Flyway): Criação de um novo script de migração (V2) para gerar a tabela user_roles, responsável por associar os IDs de usuário aos seus respectivos papéis.

Atualização de DTOs: Modificação do DTO de registro (RegisterUserRequest) para incluir a especificação de um Role.

Ajustes no AuthController: O método de registro foi modificado para atribuir o Role vindo da requisição. Foi implementada uma lógica de fallback para atribuir um papel padrão (ex: USER) se nenhum for fornecido.

Configuração de Segurança (SecurityConfig): Atualização do SecurityFilterChain para definir regras de autorização baseadas em hasRole(), por exemplo:

Rotas /admin/** exigem o papel ADMIN.

Rotas /user/** exigem os papéis USER ou ADMIN.

Atualização da Geração e Validação de Token (JWT):

Geração: O método generateToken foi atualizado para incluir os papéis (roles) como uma claim no payload do JWT.

DTO/Record: O DTO (JWTUserData) que armazena os dados do usuário extraídos do token foi atualizado para incluir a lista de papéis.

Filtro: O SecurityFilter foi modificado para, após validar o token, extrair os papéis (roles) da claim e injetá-los no UsernamePasswordAuthenticationToken (Contexto de Segurança do Spring).

Controladores de Teste: Adição de controladores (AdminController, UserController) com endpoints distintos para validar as regras de autorização de cada papel.


Registrar Usuário Comum:

POST para /auth/register sem passar o campo role.

O usuário será criado com o papel padrão (USER).

Registrar Usuário Admin:

POST para /auth/register passando "role": "ADMIN" no corpo.

O usuário será criado com o papel ADMIN.

Acesso como Admin:

login com o usuário ADMIN (/auth/login) para obter um token JWT.

acessar GET /user/dashboard (Resultado esperado: Permitido).

acessar GET /admin/panel (Resultado esperado: Permitido).

Acesso como User:

login com o usuário USER (/auth/login) para obter um token JWT.

acessar GET /user/dashboard (Resultado esperado: Permitido).

acessar GET /admin/panel (Resultado esperado: Bloqueado - 403 Access Denied).


# Spring Security Project with RBAC (Role-Based Access Control)
This project demonstrates the implementation of Role-Based Access Control (RBAC) in a Spring Boot application using Spring Security and JSON Web Tokens (JWT). The objective is to extend a basic authentication system to include granular authorization for different user types (e.g., USER and ADMIN).

# Implemented Features
User authentication via JWT.

New user registration.

Implementation of user roles (e.g., ROLE_USER, ROLE_ADMIN).

Endpoint access control based on user role.

Test endpoints to validate USER and ADMIN access.

# Implementation Overview
The RBAC implementation followed these main steps:

Role Enum: A Role enum was created to define available roles (e.g., USER, ADMIN).

User Entity Update: Added a Set<Role> attribute to the User entity. Used @Enumerated(EnumType.STRING), @ElementCollection, and @CollectionTable annotations to map the relationship. The getAuthorities() method was updated to convert Roles into GrantedAuthority for Spring Security.

Database Migration (Flyway): A new migration script (V2) was created to generate the user_roles table, which links user IDs to their respective roles.

DTO Updates: The registration DTO (RegisterUserRequest) was modified to include a Role specification.

Controller Adjustments (AuthController): The registration method was modified to assign the Role from the request. Fallback logic was implemented to assign a default role (e.g., USER) if none is provided.

Security Configuration (SecurityConfig): The SecurityFilterChain was updated to define authorization rules based on hasRole(), for example:

/admin/** routes require the ADMIN role.

/user/** routes require either USER or ADMIN roles.

JWT Generation and Validation Update:

Generation: The generateToken method was updated to include the roles as a claim in the JWT payload.

DTO/Record: The DTO (JWTUserData) storing user data extracted from the token was updated to include the list of roles.

Filter: The SecurityFilter was modified to extract roles from the token's claim after validation and inject them into the UsernamePasswordAuthenticationToken (Spring Security Context).

Test Controllers: Added controllers (AdminController, UserController) with distinct endpoints to validate the authorization rules for each role.


Register Standard User:

Make a POST request to /auth/register without providing the role field.

The user will be created with the default role (USER).

Register Admin User:

Make a POST request to /auth/register, passing "role": "ADMIN" in the body.

The user will be created with the ADMIN role.

Access as Admin:

Log in with the ADMIN user (/auth/login) to obtain a JWT.

Attempt to access GET /user/dashboard (Expected: Allowed).

Attempt to access GET /admin/panel (Expected: Allowed).

Access as User:

Log in with the USER user (/auth/login) to obtain a JWT.

Attempt to access GET /user/dashboard (Expected: Allowed).

Attempt to access GET /admin/panel (Expected: Forbidden - 403 Access Denied).
