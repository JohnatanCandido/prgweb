package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.ComentarioDao;
import br.com.john.prgweb.dao.CommentRatingDao;
import br.com.john.prgweb.domain.Arquivo;
import br.com.john.prgweb.domain.Comentario;
import br.com.john.prgweb.domain.CommentRating;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ComentarioBean implements Serializable{

	private Comentario comentario;
	private Arquivo arquivo;
	
	public void novo() {
		this.comentario = new Comentario();
	}
	public ComentarioBean(){
		novo();
	}
	
	public void excluir(ActionEvent evento){
		try {
			comentario = (Comentario)evento.getComponent().getAttributes().get("xxxxxxxxxxxxxxxxx");
			ComentarioDao dao = new ComentarioDao();
			dao.excluir(comentario);
			Messages.addGlobalInfo("Comentário excluido.");
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
		
	}
	
	
	public void alterar(ActionEvent evento) {
		comentario = (Comentario)evento.getComponent().getAttributes().get("xxxxxxxxxxxxxxxxxx");
	}
	
	public List<Comentario> listar(Long codigo){
		try {
			ComentarioDao dao = new ComentarioDao();
			return dao.buscarComentario(codigo);
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar comentários");
			exception.printStackTrace();
		}
		return null;
	}

	
	public void salvar(ActionEvent evento) {
		try {
			LoginBean lb = new LoginBean();
			comentario.setArquivo((Arquivo)evento.getComponent().getAttributes().get("arquivo"));
			comentario.setUsuario(lb.getUsuario());
			ComentarioDao dao = new ComentarioDao();
			dao.Salvar(comentario);
			Messages.addGlobalInfo("Comentado");
			novo();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao comentar");
			exception.printStackTrace();
		}
	}
	
	public String getLikes(Comentario com){
		CommentRatingDao crd = new CommentRatingDao();
		List<CommentRating> comment = crd.buscaCommentRating(com);
		int rating = 0;
		for(CommentRating cr: comment){
			rating += cr.getRating();
		}
		if(rating == 1 || rating == -1){
			return rating+" Ponto";
		}
		return rating+" Pontos";
	}
	
	public List<Comentario> buscaComentarios(Arquivo arquivo) {
		ComentarioBean com = new ComentarioBean();
		List<Comentario> comments = com.listar(arquivo.getCodigo());
		for(Comentario comment: comments){
			comment.setRating_comentario(getLikes(comment));
		}
		return comments;
	}
	
	public Comentario getComentario() {
		return comentario;
	}
	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	
	
}
