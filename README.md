# ED1-projeto

## Integrantes
- Edmundo Vitor de Medeiros
- Guilherme Augusto Saldanha Silva
- Paulo Victor Benevides Marinho
- Ruslan Yuri Gomes

## Contextualização do domínio
  Plataforma de artigos sobre temas da área de TI. Onde os usuários cadastrados podem escrever seus próprios artigos sobre a área de TI e publicar na plataforma
  
## Objetivo
  Incentivar o compartilhamento de experiências e conhecimentos na área de TI (tecnologia da informação).

## Funcionalidades e recursos
### Funcionalidades
- Cadastro de usuários: 
  - Formulário para realizar o cadastro de usuário com todas as verificações necessárias.

- Autenticação: 
  - Formulário para realizar o login dos usuários.

- Criação dos artigos: 
  - Haverá uma caixa de texto onde o usuário poderá digitar o seu artigo que a princípio será limitado a 5 mil caracteres. 
  - Será possível adicionar qualquer tipo de arquivo (.jpg, .pdf, .zip, etc) em anexo ao artigo, limitando o tamanho do arquivo a 20 MB. 

- Avaliação dos artigos:
  - Será possível os usuários que possuem conta na plataforma avaliarem os artigos através dos botões de LIKE e DISLIKE.

- Denunciar artigos:
  - Será possível os usuários denunciarem artigos que infrinjam regras particulares da plataforma ou qualquer direito humano.

### Recursos
- Estruturas de Dados:
 - Inicialmente será implementada a estrutura Set para ser utilizada nos relacionamentos entre as entidades.
 - Conforme a necessidade implementaremos outras estruturas que forem necessárias

- Backend:
 - O backend do projeto será implementado com o Spring Boot.
 - A persistência dos dados será feita com Hibernate e JPA, utilizando o postgreSQL como banco de dados.
 - A autenticação será feita utilizando JWT com Spring Security.

- Frontend:
  - Para a criação da interface utilizaremos o React, que é uma biblioteca JavaScript para criação de interfaces.
  - Utilizaremos o framework NextJs para ter uma melhor otimização dos motores de busca (SEO) e possíveis utilizações de SSG (Static Site Generation) e SSR (Server Side Rendering) em futuras funcionalidades.
  - Utilizaremos também o Chakra UI, uma biblioteca de componentes do React que facilita a construção da IU de um aplicativo ou site.
  - A comunicação com o backend será feita por meio de requisições HTTP utilizando a biblioteca axios.

## Classes-entidade e seus atributos
![](https://i.imgur.com/eB4ZaJb.png)

## Wireframe das telas
Figma - https://www.figma.com/file/VcuoQPY4XKOYP0qX4z11or/projeto-ED1?node-id=4%3A8

## Arquitetura
  A arquitetura utilizada será o MVC (model-view-controller). As camadas “model” e “controller” serão implementadas no backend, enquanto a camada “view” será implementada no frontend.

## Links dos repositórios
Front - https://github.com/GuilhermeA08/ED1-frontend
<br>
Back - https://github.com/GuilhermeA08/ED1-backend
