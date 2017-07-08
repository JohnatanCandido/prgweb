package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.RatingArquivoDao;
import br.com.john.prgweb.domain.Arquivo;
import br.com.john.prgweb.domain.RatingArquivo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RatingArquivoBean implements Serializable{
	
	private RatingArquivo rating;
	private List<RatingArquivo> ratings;
	
	public RatingArquivoBean(){
		novo();
	}
	public void novo() {
		this.rating = new RatingArquivo();
	}
	
	public void salvar(Arquivo arquivo, int valor) {
		try {
			LoginBean lb = new LoginBean();
			RatingArquivoDao dao = new RatingArquivoDao();
			rating.setRating(valor);
			RatingArquivo r = dao.buscarRatingEspecifico(lb.getUsuario(), arquivo);
			if(r != null){
				r.setRating(rating.getRating());
				dao.merge(r);
			}else{
				rating.setArquivo(arquivo);
				rating.setUsuario(lb.getUsuario());
				dao.Salvar(rating);
			}
			novo();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao avaliar");
			exception.printStackTrace();
		}
	}
	
	public void excluir(Arquivo arquivo){
		try {
			LoginBean lb = new LoginBean();
			RatingArquivoDao dao = new RatingArquivoDao();
			RatingArquivo r = dao.buscarRatingEspecifico(lb.getUsuario(), arquivo);
			if(r != null){
				dao.excluir(r);
			}
			Messages.addGlobalInfo("Avaliação excluida");
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
		
	}
	
	public double listarRating(Long codigo){
		try {
			RatingArquivoDao dao = new RatingArquivoDao();
			ratings = dao.buscarRating(codigo);
			double retorno = 0;
			for(RatingArquivo r: ratings){
				retorno += (double)r.getRating();
			}
			if(ratings.size()!=0){
				retorno /= (double)ratings.size();
				retorno *= 2;
			}
			return retorno;
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar ratings");
			exception.printStackTrace();
		}
		return 0.0;
	}	
	
	public RatingArquivo getRating() {
		return rating;
	}
	public void setRating(RatingArquivo rating) {
		this.rating = rating;
	}
	public List<RatingArquivo> getRatings() {
		return ratings;
	}
	public void setRatings(List<RatingArquivo> ratings) {
		this.ratings = ratings;
	}
}
