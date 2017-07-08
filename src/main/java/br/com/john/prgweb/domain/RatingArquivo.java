package br.com.john.prgweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class RatingArquivo extends GenericDomain{

//COLUNAS DO BANCO DE DADOS
	@ManyToOne
	@JoinColumn(nullable=false)
	private Arquivo arquivo;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Usuario usuario;
	
	@Column
	private int rating;//MUDAR PARA INTEGER QUANDO TIVER TEMPO

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	
}