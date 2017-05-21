package br.com.john.prgweb.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Arquivo extends GenericDomain {
	// COLUNAS DO BANCO DE DADOS
	@Column(length = 30)
	private String nome;

	@Column
	private Double tamanho;

	@Transient
	private Double rating_arquivo;

	@Column(length = 10)
	private String tipo;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuario_upload;// codigo do usuário que fez o upload do
									// arquivo

	@Column
	private Integer downloads;// número de downloads

	@Column(length = 5000)
	private String descricao;

	@Transient
	private List<Comentario> comentarios;

	@Column
	private String caminhoArquivo;

	// GETTERS E SETTERS DAS COLUNAS
	public String getNome() {
		return nome;
	}

	public Double getTamanho() {
		return tamanho;
	}

	public void setTamanho(Double tamanho) {
		this.tamanho = tamanho;
	}

	public Double getRating_arquivo() {
		return rating_arquivo;
	}

	public void setRating_arquivo(Double rating_arquivo) {
		this.rating_arquivo = rating_arquivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario_upload() {
		return usuario_upload;
	}

	public void setUsuario_upload(Usuario usuario_upload) {
		this.usuario_upload = usuario_upload;
	}

	public Integer getDownloads() {
		return downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

}