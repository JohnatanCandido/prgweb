package br.com.john.prgweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain{

//COLUNAS DO BANCO DE DADOS
	@Column(length=15, unique=true)
	private String nick;
	
	@Column(length=20, nullable=false, unique=true)
	private String login;
	
	@Column(length=20, nullable=false)
	private String senha;
	
	@Column(length=50, nullable=false, unique=true)
	private String email;
	
	@Transient
	private String nova_senha, senha_confirm;
	
	@Transient
	int total_downloads = 0;
	
	@Transient
	String media_rating = "-";

//GETTERS E SETTERS DAS COLUNAS
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNova_senha() {
		return nova_senha;
	}

	public void setNova_senha(String nova_senha) {
		this.nova_senha = nova_senha;
	}

	public String getSenha_confirm() {
		return senha_confirm;
	}

	public void setSenha_confirm(String senha_confirm) {
		this.senha_confirm = senha_confirm;
	}

	public int getTotal_downloads() {
		return total_downloads;
	}

	public void setTotal_downloads(int total_downloads) {
		this.total_downloads = total_downloads;
	}

	public String getMedia_rating() {
		return media_rating;
	}

	public void setMedia_rating(String media_rating) {
		this.media_rating = media_rating;
	}
	
}