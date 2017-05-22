package br.com.john.prgweb.bean;

import java.io.Serializable;

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
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
		
	}
	
	public void alterar(ActionEvent evento) {
		usuario = (Usuario)evento.getComponent().getAttributes().get("usuarioAlterar");
		try {
			if(usuario.getSenha_confirm().equals(usuario.getSenha())){
				if(usuario.getNova_senha() != null && !usuario.getNova_senha().equals("") && !usuario.getNova_senha().equals(" ")){
					usuario.setSenha(usuario.getNova_senha());
				}
				UsuarioDao dao = new UsuarioDao();
				dao.merge(usuario);
				Messages.addGlobalInfo("Dados alterados com sucesso!");
				usuario.setNova_senha(null);
				usuario.setSenha_confirm(null);
				novo();
			}else{
				throw new NumberFormatException("Senha incorreta!");
			}
		} catch(NumberFormatException e){
			Messages.addGlobalError(e.getMessage());
		} catch (Exception e) {
			Messages.addGlobalError("Deu ruim");
			e.printStackTrace();
		}
	}
	
	public void salvar() {
		try {
			UsuarioDao dao = new UsuarioDao();
			dao.Salvar(usuario);
			Messages.addGlobalInfo("Conta cadastrada com sucesso");
			novo();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao cadastar esta conta");
			exception.printStackTrace();
		}
	}
	
	/*@PostConstruct
	public void listar(){
		try {
			UsuarioDao dao = new UsuarioDao();
			usuarios = dao.listarTodos();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar usuarios");
			exception.printStackTrace();
		}
	}*/

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
