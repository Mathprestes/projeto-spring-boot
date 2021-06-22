package br.com.springboot.curso_jdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.springboot.curso_jdev.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {          // "trim" serve para tirar os espaços na hora de pesquisar
	                                                                                 // "upper" serve para ignorar se o caracter é maiusculo ou minusculo                           
	@Query (value = "select u from Usuario u where upper(trim(u.nome)) like %?1%")    //selecionar (u) de usuario da tabela Usuario (u) onde no u.nome(variavel nome) faça um like(pesquisar por partes)                
	List<Usuario> buscarPorNomeList (String name);                                   // %?!1% passando apenas 1 paramentro, se quisesse fazer a pesquisa com duas variaveis ficaria %?2%

}
