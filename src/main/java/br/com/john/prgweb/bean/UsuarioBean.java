package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.UsuarioDao;
import br.com.john.prgweb.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	
	public void novo() {
		this.usuario = new Usuario();
	}
	
	public UsuarioBean(){
		this.usuario = new Usuario();
	}
	
	public void excluir(ActionEvent evento){
		try {
			usuario = (Usuario)evento.getComponent().getAttributes().get("xxxxxxxxxxxxx");
			UsuarioDao dao = new UsuarioDao();
			dao.excluir(usuario);
			Messages.addGlobalInfo(usuario.getNick()+" Excluido com sucesso");
			listar();
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
		
	}
	
	public void alterar(ActionEvent evento) {
		usuario = (Usuario)evento.getComponent().getAttributes().get("xxxxxxxxxxxxxx");
		
	}
	
	public void salvar() {
		try {
			UsuarioDao dao = new UsuarioDao();
			dao.Salvar(usuario);
			Messages.addGlobalInfo("Conta cadastrada com sucesso");
			novo();
			listar();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao cadastar esta conta");
			exception.printStackTrace();
		}
	}
	
	@PostConstruct
	public void listar(){
		try {
			UsuarioDao dao = new UsuarioDao();
			usuarios = dao.listarTodos();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar usuarios");
			exception.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	//COPIADO DO PRIMEFACES
	/*public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        
        for(Usuario u: usuarios){
        	if(usuario.getLogin() != null && usuario.getLogin().equals(u.getLogin()) && usuario.getSenha() != null && usuario.getSenha().equals(u.getSenha())) {
                loggedIn = true;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem Vindo", usuario.getLogin());
                break;
            } 
        }
        if(!loggedIn){
        	loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro no Loggin", "Login ou senha incorretos");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    } */ 
	

}
