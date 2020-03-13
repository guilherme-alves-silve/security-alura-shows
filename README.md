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

### Index

![index](https://media.giphy.com/media/xT9IgwKKAKMcrhV5Be/giphy.gif)

### Admin

![admin](https://media.giphy.com/media/xT9IgKhcbcvAcx4Eec/giphy.gif)

### Login

![login](https://media.giphy.com/media/xT9IgO0n4cb311s06Y/giphy.gif)

### Blog

![blog](https://media.giphy.com/media/3o7aD52RkT29CAdd1S/giphy.gif)

### Registrar

![registrar](https://media.giphy.com/media/3ov9jNDmww9BGZR25q/giphy.gif)
