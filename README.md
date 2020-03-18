# Projeto Alura Shows!

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