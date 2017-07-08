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
	private int rating;

//GETTERS E SETTERS DAS COLUNAS

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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}