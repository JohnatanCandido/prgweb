package br.com.john.prgweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class CommentRating extends GenericDomain{
//COLUNAS DO BANCO DE DADOS
	@ManyToOne
	@JoinColumn(nullable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Comentario comentario;
	
	@Column
	private Double rating;

//GETTERS E SETTERS DAS COLUNAS
	public Double getRating() {
		return rating;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
}