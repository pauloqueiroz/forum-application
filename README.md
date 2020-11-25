# forum-application

Aplicação de testes para aprendizado de Spring boot pelo curso da Alura. 


Passos para execução do projeto em um container docker

1. Buildar/criar a imagem:
	docker build -t pauloqueiroz/forum .

2. Verificar criação da imagem:(opcional)
	docker images

3. Rodar container: (Alterar os dados da conexão com o banco e JWT Secret)
	docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE='prod' -e FORUM_DATABASE_URL='jdbc:h2:mem:alura-forum' -e FORUM_DATABASE_USER='sa' -e FORUM_DATABASE_PASSWORD='' -e FORUM_JWT_SECRET='123456' pauloqueiroz/forum 