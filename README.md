# Vitta API - Backend
API RESTful desenvolvida como o backend para o aplicativo Vitta, uma solução mobile para gestão pessoal de medicamentos.

## 📝 Descrição do Projeto
O Vitta é um aplicativo mobile projetado para auxiliar usuários no controle de seus tratamentos, medicamentos, horários, e todos os detalhes que o usuário precisa saber para ter certeza que sua saúde está em dia. Esta API é o cérebro por trás do aplicativo, responsável por gerenciar toda a lógica de negócio, persistência de dados e segurança das informações dos usuários.
A solução foi projetada para trazer praticidade, segurança e confiabilidade no acompanhamento da rotina medicamentosa dos usuários.

## ✨ Funcionalidades Principais
- 👤 Gestão de Usuários: Cadastro, autenticação e gerenciamento do perfil do usuário.
- 💊 Gestão de Medicamentos: CRUD completo que permite os usuários cadastrarem seus medicamentos.
- 🩺 Gestão de Tratamentos: Criação e acompanhamento de tratamentos, definindo períodos, instruções e medicamentos que serão utilizados em cada tratamento.
- ⏰ Agendamentos Inteligentes: Criação de agendamentos por meio dos períodos definidos em cada Tratamento, e gerenciamento para a toma de medicamentos, com sistema de alertas.
- 📜 Histórico de Uso: Registro de quando o usuário tomou cada dose, permitindo um acompanhamento preciso da adesão ao tratamento.

## 🛠️ Tecnologias Utilizadas
- Linguagem: Java 17
- Framework: Spring Boot 3
- Persistência: Spring Data JPA com Hibernate
- Banco de Dados:
- Desenvolvimento: Banco de dados em memória H2
- Produção: PostgreSQL
- Build Tool: Maven ou Gradle
- Versionamento: Git & GitHub
