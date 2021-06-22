package br.com.springboot.curso_jdev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev.model.Usuario;
import br.com.springboot.curso_jdev.repository.UsuarioRepository;

@RestController
public class GreetingsController {
	

	@Autowired // *IC/CD OU CDI - Injeção de Dependencia
	private UsuarioRepository usuarioRepository;
	
    // ----------------------------------------------------------------------- //
    
	@RequestMapping (value = "/olaMundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public String retornaOla (@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Seja Bem Vindo " + nome;
    }
    
    // ----------------------------------------------------------------------- //
    
    @GetMapping (value = "listatodos")     //Primeira API ( LIST )
    @ResponseBody              //retorna os dados para o corpo da responta (JSON)
    public ResponseEntity<List<Usuario>> listaUsuario() {
    	
    	List<Usuario> usuarios = usuarioRepository.findAll();   //executa a consulta no bando de dados
    	
    	return new ResponseEntity<List<Usuario>> (usuarios, HttpStatus.OK);    //retorna a lista em JSON
    }
    
    // ----------------------------------------------------------------------- //
    
    @PostMapping (value = "salvar")     //mapeia a url
    @ResponseBody                     //descrição da resposta
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){    //recebe os dados para salvar
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    // ----------------------------------------------------------------------- //
    
    @DeleteMapping (value = "delete")     //mapeia a url
    @ResponseBody                     //descrição da resposta
    public ResponseEntity<String> delete (@RequestParam Long iduser){    
    	
    	usuarioRepository.deleteById (iduser);
    	
    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
    }
   
    // ----------------------------------------------------------------------- //
    
    @GetMapping (value = "buscaruserid")     //mapeia a url
    @ResponseBody                           //descrição da resposta
    public ResponseEntity<Usuario> buscaruserid (@RequestParam (name = "iduser") Long iduser){    
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario> (usuario, HttpStatus.OK);
    }
    
    // ----------------------------------------------------------------------- //
    
    @PutMapping (value = "atualizar")     //mapeia a url
    @ResponseBody                     //descrição da resposta
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){    //recebe os dados para salvar
    	
    	if (usuario.getId() == null) {   //se o ID for igual a nulo(nao existe) exibir essa mensagem
    		return new ResponseEntity<String> ("Id nao foi encontrado para atualização.", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    // ----------------------------------------------------------------------- //
    
    @GetMapping (value = "buscarPorNome")
    @ResponseBody ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam (name = "name") String name){     //retorna uma lista de usuarios, e passa o paramentro "name" para fazer a busca por nomes
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorNomeList(name.trim().toUpperCase() );    // "trim" serve para tirar os espaços na hora de pesquisar                                                   
    	                                                                                             // "toUpperCase" serve para ignorar se o caracter é maiusculo ou minusculo 
    	return new ResponseEntity<List<Usuario>> (usuario, HttpStatus.OK);
    }
    
    // ----------------------------------------------------------------------- //
}