package br.com.john.prgweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain{

//COLUNAS DO BANCO DE DADOS
	@Column(length=15)
	private String nick;
	
	@Column(length=20, nullable=false)
	private String login;
	
	@Column(length=20, nullable=false)
	private String senha;
	
	@Column(length=50, nullable=false)
	private String email;

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
	
	

}