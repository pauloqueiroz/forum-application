package br.com.alura.forum.controller;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.alura.forum.controller.form.LoginForm;
import br.com.alura.forum.modelo.Perfil;
import br.com.alura.forum.modelo.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
@ActiveProfiles("test")
public class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	public void deveriaRetornarErroAoLogarComDadosInvalidos() throws Exception {
		LoginForm loginForm = new LoginForm();
		loginForm.setEmail("aluno@email.com");
		loginForm.setSenha("123456");
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(loginForm);
	    System.out.println("Json: "+requestJson);
		
	    URI uri = new URI("/auth");
		
	    mockMvc.perform(MockMvcRequestBuilders.post(uri)
	    									.contentType(MediaType.APPLICATION_JSON)
	    									.content(requestJson))
	    		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	public void deveriaRetornarSucessoAoLogarComUsuarioValido() throws Exception {
		String email = "aluno@email.com";
		String senha = "123456";
		
		Perfil perfil = new Perfil();
		perfil.setNome("ROLE_ALUNO");
		em.persist(perfil);
		
		Usuario usuario = new Usuario();
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		usuario.setEmail(email);
		usuario.setNome(email);
		usuario.getPerfis().add(perfil);
		em.persist(usuario);
		
		LoginForm loginForm = new LoginForm();
		loginForm.setEmail(email);
		loginForm.setSenha(senha);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(loginForm);
	    System.out.println("Json: "+requestJson);
		
	    URI uri = new URI("/auth");
		
	    mockMvc.perform(MockMvcRequestBuilders.post(uri)
	    									.contentType(MediaType.APPLICATION_JSON)
	    									.content(requestJson))
	    		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

}
