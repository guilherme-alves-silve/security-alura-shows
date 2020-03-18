# Projeto Security Alura Shows!

## Sobre

Este projeto é um estudo de como se defender de ataques em aplicações, tendo o foco a linguagem Java.

Os cursos estudados do Alura são:

* [Segurança web em Java: Evitando SQL Injection, força bruta e outros ataques](https://www.alura.com.br/curso-online-seguranca-web-em-java-parte-1)
* [Segurança web em Java parte 2: XSS, Mass Assignment e Uploads de arquivos!](https://www.alura.com.br/curso-online-seguranca-web-em-java-parte-2)

## Exemplos de ataques:

### Na pasta src/main/resources possue os seguintes exemplos:

* Para upload de arquivos: imagemfalsa.jpg e imagemfalsa.jsp
* Para Mass Assignment: mass_assignment_exemplo.html
* Para XSS: xss_script_exemplo.js

### O [Kali Linux](https://www.kali.org/) foi utilizado para testar os outros cenários

* sqlmap: SQL Injection
* Burp Suite: Força bruta

## Execute o banco utilizando docker:

docker run -d -p 3306:3306 -it --name mysql_alura_shows mysql/mysql-server

docker start mysql_alura_shows

## Se quiser conectar no banco:

docker logs mysql_alura_shows 2>&1 | grep GENERATED

docker exec -it mysql_alura_shows mysql -uroot -p

mysql -h localhost -u root -p

ALTER USER 'root'@'localhost' IDENTIFIED BY 'admin';

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

ou abaixo, dependendo da versão

CREATE USER 'root'@'%' IDENTIFIED BY 'admin';

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

docker exec -it mysql_alura_shows mysql -uroot -padmin

## Projeto que contém o tratamento para os ataques

* Evitando SQL Injection
* Proteção contra Força bruta usando Recaptcha
* Protegendo senha com Hash
* Tratando conteúdo para evitar XSS
* Utilizando DTO para evitar Mass Assignment
* Evitando ataque do lado do servidor validando upload de arquivos