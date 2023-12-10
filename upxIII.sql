drop database if exists NaoConformidadeUPX;
create database NaoConformidadeUPX;
use	NaoConformidadeUPX;

create table usuarios(
	id 				int			primary key auto_increment,
    nome			varchar (30),
    email			varchar (30),
    data_nasc		varchar(10),
    senha			varchar (10),
    tipo			varchar(10)
);

INSERT INTO usuarios VALUES (null, 'Naomi Rodrigues Teixeira', 'teste@teste.com', '19/08/2002', 'senha321', 'usuario');
INSERT INTO usuarios VALUES (null, 'Naomi Rodrigues Teixeira', 'teste@master.com', '19/08/2002', 'senha456', 'master');

create table tickets(
	id 						int				primary key auto_increment,
    tipo_ocorrencia			varchar (100),
    titulo					varchar (200),
    descricao				varchar(300),
    status_call				varchar (20),
	usuario					varchar(30),
    tipo 					varchar (30),
    data_ticket            	TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




