package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.ArquivoDao;
import br.com.john.prgweb.dao.RatingArquivoDao;
import br.com.john.prgweb.dao.UsuarioDao;
import br.com.john.prgweb.domain.Arquivo;
import br.com.john.prgweb.domain.RatingArquivo;
import br.com.john.prgweb.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RatingArquivoBean implements Serializable{
	
	private RatingArquivo rating;
	private List<RatingArquivo> ratings;
	private List<Arquivo> arquivos;
	private List<Usuario> usuarios;
	
	public void novo() {
		this.rating = new RatingArquivo();
		carregaUsuarios();
		carregaArquivos();
	}
	
	public void salvar(Arquivo arquivo) {
		try {
			LoginBean lb = new LoginBean();
			RatingArquivoDao dao = new RatingArquivoDao();
			RatingArquivo r = dao.buscarRatingEspecifico(lb.getUsuario(), arquivo);
			if(r != null){
				r.setRating(rating.getRating());
				dao.merge(r);
			}else{
				rating.setArquivo(arquivo);
				rating.setUsuario(lb.getUsuario());
				dao.Salvar(rating);
			}
			Messages.addGlobalInfo("Avaliado");
			novo();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao avaliar");
			exception.printStackTrace();
		}
	}
	
	/*public void excluir(ActionEvent evento){
		try {
			rating = (RatingArquivo)evento.getComponent().getAttributes().get("xxxxxxxxxxxxxxx");
			RatingArquivoDao dao = new RatingArquivoDao();
			dao.excluir(rating);
			Messages.addGlobalInfo("Avaliação excluida");
			listar();
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
		
	}
	
	
	public void alterar(ActionEvent evento) {
		rating = (RatingArquivo)evento.getComponent().getAttributes().get("xxxxxxxxxxxxxx");
		carregaUsuarios();
		carregaArquivos();
		
	}*/
	
	public double listarRating(Long codigo){
		try {
			RatingArquivoDao dao = new RatingArquivoDao();
			ratings = dao.buscarRating(codigo);
			double retorno = 0;
			for(RatingArquivo r: ratings){
				retorno += (double)r.getRating();
			}
			if(ratings.size()!=0){
				retorno /= ratings.size();
			}
			return retorno;
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar ratings");
			exception.printStackTrace();
		}
		return 0.0;
	}
	

	private void carregaUsuarios(){
		try {
			UsuarioDao dao = new UsuarioDao();
			usuarios = dao.listarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void carregaArquivos(){
		try {
			ArquivoDao dao = new ArquivoDao();
			arquivos = dao.listarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public List<Arquivo> getArquivos() {
		return arquivos;
	}
	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
	
	
}
