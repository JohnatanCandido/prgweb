package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.ComentarioDao;
import br.com.john.prgweb.dao.CommentRatingDao;
import br.com.john.prgweb.dao.UsuarioDao;
import br.com.john.prgweb.domain.Comentario;
import br.com.john.prgweb.domain.CommentRating;
import br.com.john.prgweb.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CommentRatingBean implements Serializable{

	private CommentRating rating;
	private List<CommentRating> ratings;
	private List<Usuario> usuarios;
	private List<Comentario> comentarios;
	
	public void novo() {
		this.rating = new CommentRating();
		
	}
	
	public void salvar() {
		try {
			CommentRatingDao dao = new CommentRatingDao();
			dao.Salvar(rating);
			Messages.addGlobalInfo("Avaliado com sucesso");
			novo();
			listar();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao avaliar");
			exception.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento){
		try {
			rating = (CommentRating)evento.getComponent().getAttributes().get("xxxxxxxxxxxxxxxxx");
			CommentRatingDao dao = new CommentRatingDao();
			dao.excluir(rating);
			Messages.addGlobalInfo("Avaliação excluida");
			listar();
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
		
	}
	
	public void alterar(ActionEvent evento) {
		rating = (CommentRating)evento.getComponent().getAttributes().get("xxxxxxxxxxxxxxxxx");
		carregaUsuarios();
		carregaComentarios();
		
	}
	
	@PostConstruct
	public void listar(){
		try {
			CommentRatingDao dao = new CommentRatingDao();
			ratings = dao.listarTodos();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar ratings");
			exception.printStackTrace();
		}
	}
	

	private void carregaUsuarios(){
		try {
			UsuarioDao dao = new UsuarioDao();
			usuarios = dao.listarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void carregaComentarios(){
		try {
			ComentarioDao dao = new ComentarioDao();
			comentarios = dao.listarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CommentRating getRating() {
		return rating;
	}

	public void setRating(CommentRating rating) {
		this.rating = rating;
	}

	public List<CommentRating> getRatings() {
		return ratings;
	}

	public void setRatings(List<CommentRating> ratings) {
		this.ratings = ratings;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	
		
}
