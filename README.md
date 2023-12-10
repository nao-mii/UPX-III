# UPX-III

# Sistema de Não Conformidade - Gestão de Tickets

Este projeto Java é um sistema simples de gestão de tickets para monitorar ocorrências de não conformidade em diferentes categorias. O sistema consiste em duas principais telas: MainScreen (Tela Principal) e UserProfile (Perfil do Usuário). Além disso, há formulários específicos para cada categoria de não conformidade.

## Funcionalidades Principais
Tela Principal (MainScreen):

Boas-vindas ao usuário.
Botões para acessar diferentes tipos de não conformidade (Energia, Água, Descarte de Resíduos, Acessibilidade, Manutenção, Áreas Verdes, Segurança).
Botão para acessar o perfil do usuário.
Perfil do Usuário (UserProfile):

Exibe o nome do usuário.
Tabela para listar tickets relacionados ao usuário.
Botões para atualizar a lista de tickets e voltar à Tela Principal.
Estatísticas sobre o total de tickets e o total de tickets fechados.
Estrutura do Projeto
O projeto é dividido em duas classes principais:

MainScreen (Tela Principal):

Responsável por exibir a tela principal com botões para acessar diferentes tipos de não conformidade.
UserProfile (Perfil do Usuário):

Exibe o perfil do usuário, lista de tickets e estatísticas.
Permite atualizar a lista de tickets e retornar à Tela Principal.
Configuração do Banco de Dados
O projeto utiliza um banco de dados MySQL para armazenar informações sobre tickets. Certifique-se de configurar corretamente as informações de conexão no código:

java
Copy code
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NaoConformidadeUPX", "root", "suaSenhaDoBanco");
Como Executar o Projeto
Clone o repositório para a sua máquina local.
Importe o projeto em um ambiente de desenvolvimento Java, como o IntelliJ ou Eclipse.
Certifique-se de ter um servidor MySQL em execução e crie o banco de dados conforme necessário.
Execute a classe MainScreen para iniciar o sistema.
Requisitos do Sistema
Java 8 ou superior.
Servidor MySQL.
Observações
Este é um projeto educacional e pode exigir ajustes conforme necessário.
Certifique-se de adaptar as configurações do banco de dados de acordo com o seu ambiente.
Sinta-se à vontade para contribuir, reportar problemas ou fazer sugestões para a melhoria deste projeto. Apreciamos a sua colaboração!
