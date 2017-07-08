package br.com.john.prgweb.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.UsuarioDao;
import br.com.john.prgweb.domain.Usuario;

@ManagedBean
public class LoginBean {
	
	private UsuarioDao usuarioDao = new UsuarioDao();
	private Usuario usuario = new Usuario();
	
	private String username, password;
	
	private FacesContext context = FacesContext.getCurrentInstance();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void envia() {
		try {
			usuario = usuarioDao.getUsuario(username, password);
			
			if (usuario == null) {
				Messages.addGlobalError("Login ou senha incorreto!");
			} else {
				context.getExternalContext().getSessionMap().put("current_user", usuario);
				Messages.addGlobalInfo("Bem vindo " + usuario.getNick());
			}
		}catch (Exception e) {
			Messages.addGlobalError("Ops :(");
			e.printStackTrace();
		}	
	}
	
	public void excluirConta(){
		usuario = getUsuario();
		if(usuario.getLogin().equals(username) && usuario.getSenha().equals(password)){
			UsuarioBean ub = new UsuarioBean();
			ub.excluir(usuario);
			sair();
		}
	}
	
	public void sair(){
		this.usuario = null;
		this.username = null;
		this.password = null;
		context.getExternalContext().getSessionMap().put("current_user", null);
	}
	
	public boolean isLogged(){
		ExternalContext a = context.getExternalContext();
		if(a == null){
			return false;
		}
		usuario = (Usuario)context.getExternalContext().getSessionMap().get("current_user");
		return usuario!=null;

	}

	public Usuario getUsuario() {
		if(isLogged())
			return (Usuario)context.getExternalContext().getSessionMap().get("current_user");
		else
			return null;
	}
}
