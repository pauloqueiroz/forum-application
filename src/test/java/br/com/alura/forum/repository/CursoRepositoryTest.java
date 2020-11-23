package br.com.alura.forum.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CursoRepositoryTest {

	@Autowired
	private CursoRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	public void deveriaCarregarCursoCadastradoPeloNome() {
		String nomeCurso = "Spring Boot";
		Curso novoCurso = new Curso();
		novoCurso.setNome(nomeCurso);
		novoCurso.setCategoria("Programação");
		em.persist(novoCurso);
		
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNotNull(curso);
		Assert.assertEquals(curso.getNome(), nomeCurso);
	}
	
	@Test
	public void naoDeveriaCarregarCursoNaoCadastradoPeloNome() {
		String nomeCurso = "Python";
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNull(curso);
	}

}
