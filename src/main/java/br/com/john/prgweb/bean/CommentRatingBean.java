package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.CommentRatingDao;
import br.com.john.prgweb.domain.Comentario;
import br.com.john.prgweb.domain.CommentRating;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CommentRatingBean implements Serializable{

	private CommentRating rating;
	private List<CommentRating> ratings;
	
	public void novo() {
		this.rating = new CommentRating();
		
	}
	
	public void salvar(Comentario comentario, int vote) {
		try {
			LoginBean lb = new LoginBean();
			if(lb.getUsuario().getCodigo() == comentario.getUsuario().getCodigo()){
				Messages.addGlobalError("Você não pode avaliar o próprio comentário!");
				return;
			}
			CommentRatingDao dao = new CommentRatingDao();
			rating = dao.buscaCommentRatingEspecifico(comentario, lb.getUsuario());
			if(rating != null){
				if(rating.getRating() != vote){
					rating.setRating(vote);
					dao.merge(rating);
				}else{
					dao.excluir(rating);
				}
			}else{
				rating = new CommentRating();
				rating.setComentario(comentario);
				rating.setUsuario(lb.getUsuario());
				rating.setRating(vote);
				dao.Salvar(rating);
			}
			Messages.addGlobalInfo("Avaliado com sucesso");
			novo();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao avaliar");
			exception.printStackTrace();
		}
	}
	
	public boolean isMeuComment(Comentario comment){
		LoginBean lb = new LoginBean();
		if(lb.isLogged()){
			return lb.getUsuario().getCodigo() == comment.getUsuario().getCodigo();
		}
		return true;
	}
	
	public String likeDislike(Comentario comment, int like){
		LoginBean lb = new LoginBean();
		CommentRatingDao dao = new CommentRatingDao();
		rating = dao.buscaCommentRatingEspecifico(comment, lb.getUsuario());
		if(rating != null){
			if(rating.getRating() == 1 && like == 1){
				return "#0000FF";
			}else if(rating.getRating() == -1 && like == -1){
				return "#FF0000";
			}
		}
		return "#000000";
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
}
