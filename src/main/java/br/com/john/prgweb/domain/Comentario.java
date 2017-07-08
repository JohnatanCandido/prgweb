package br.com.john.prgweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Comentario extends GenericDomain{
//COLUNAS DO BANCO DE DADOS
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Arquivo arquivo;
	
	@Column(length=1000)
	private String comentario;
	
	@Transient
	private String rating_comentario;
	
	@Transient
	private int meuRating;
	
//GETTERS E SETTERS DAS COLUNAS

	public String getComentario() {
		return comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getRating_comentario() {
		return rating_comentario;
	}

	public void setRating_comentario(String rating_comentario) {
		this.rating_comentario = rating_comentario;
	}

	public int getMeuRating() {
		return meuRating;
	}

	public void setMeuRating(int meuRating) {
		this.meuRating = meuRating;
	}

}