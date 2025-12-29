🎮 Sistema de Locadora de Videogames

Projeto desenvolvido em Java 17 com Spring Boot, simulando o funcionamento de uma locadora de videogames. O sistema permite o cadastro, aluguel, venda e persistência de informações de jogos, clientes e funcionários por meio de interação com o usuário no console.

💡 Estágio atual do projeto — Feature 4

Nesta etapa, o projeto evoluiu significativamente, aprofundando conceitos de Programação Orientada a Objetos (POO) e boas práticas de desenvolvimento:

Abstração:

  Adição da classe abstrata Pessoa, representando características comuns.
  
  As classes Cliente e Funcionario agora herdam da classe mãe Pessoa.

Herança:

  Reaproveitamento de atributos e métodos comuns entre clientes e funcionários.

Interfaces:

  Implementação das interfaces Alugavel e Vendavel, definindo contratos claros de comportamento para os jogos.
  
Encapsulamento aprimorado:

  Melhoria e aprofundamento no uso de getters e setters, com validações mais robustas.
  
  Uso adequado de modificadores de acesso (private, protected, public) para maior segurança e organização do código.

Classes e Relacionamentos:

  Relacionamentos entre Pessoa, Cliente, Funcionário e Jogo.

Tratamento de exceções aprimorado:

  Uso consistente de try, catch e finally para lidar com erros de execução e leitura/escrita de arquivos.

Manipulação de arquivos (.txt):

  Os dados de funcionários, clientes e jogos agora são armazenados em arquivos de texto.

  Isso garante que as informações não sejam perdidas a cada execução do programa, simulando persistência básica de dados sem uso de banco de dados.

🚀 Tecnologias utilizadas

Java 17

Spring Boot

🧑‍💻 Autor
Gabriel Marques

📘 Projeto acadêmico — INFNET

🗓️ Última atualização: 10/11/2025
