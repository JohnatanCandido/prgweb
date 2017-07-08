package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.john.prgweb.dao.ComentarioDao;
import br.com.john.prgweb.dao.CommentRatingDao;
import br.com.john.prgweb.dao.RatingArquivoDao;
import br.com.john.prgweb.dao.UsuarioDao;
import br.com.john.prgweb.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	private boolean login, nick, email, allGood;
	
	public void novo() {
		this.usuario = new Usuario();
		UsuarioDao ud = new UsuarioDao();
		this.usuarios = ud.listarTodos();
		login = nick = email = false;
	}
	
	public boolean validaNick(){
		nick = true;
		if(usuario.getNick() != null && !usuario.getNick().equals("")){
			for(Usuario u: usuarios){
				if(u.getNick().toLowerCase().equals(usuario.getNick().toLowerCase())){
					nick = false;
					Messages.addGlobalError("Nick inválido!");
					break;
				}
			}
		}else{
			Messages.addGlobalError("O campo nick é obrigatório!");
			nick = false;
		}
		return nick;
	}
	public boolean validaLogin(){
		login = true;
		if(usuario.getLogin() != null && !usuario.getLogin().equals("")){
			for(Usuario u: usuarios){
				if(u.getLogin().toLowerCase().equals(usuario.getLogin().toLowerCase())){
					login = false;
					Messages.addGlobalError("Login inválido!");
					break;
				}
			}
		}else{
			Messages.addGlobalError("O campo login é obrigatório!");
			login = false;
		}
		return login;
	}
	public boolean validaEmail(){
		email = true;
		if(usuario.getEmail() != null && !usuario.getEmail().equals("")){
			for(Usuario u: usuarios){
				if(u.getEmail().toLowerCase().equals(usuario.getEmail().toLowerCase())){
					email = false;
					Messages.addGlobalError("E-mail inválido!");
					break;
				}
			}
		}else {
			Messages.addGlobalError("O campo e-mail é obrigatório!");
			email = false;
		}
		return email;
	}
	
	public boolean validaCampos(){
		if(usuario.getSenha().equals("")){
			Messages.addGlobalError("O campo senha é obrigatório!");
		}
		allGood = (login && nick && email && (!usuario.getSenha().equals("") && usuario.getSenha() != null));
		return allGood;
	}
	
	public UsuarioBean(){
		this.usuario = new Usuario();
	}
	
	public void excluir(Usuario usuario){
		try {
			ArquivoBean ab = new ArquivoBean();
			RatingArquivoDao rad = new RatingArquivoDao();
			CommentRatingDao crd = new CommentRatingDao();
			ComentarioDao cd = new ComentarioDao();
			rad.limpar(usuario);
			crd.limpar(usuario);
			cd.limpar(usuario);
			ab.excluirTodosArquivos(usuario);
			UsuarioDao dao = new UsuarioDao();
			dao.excluir(usuario);
			Messages.addGlobalInfo("Conta Excluída");
		} catch (Exception exception) {
			Messages.addGlobalError("Deu ruim na exclusão");
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
			LoginBean lb = new LoginBean();
			lb.setUsername(usuario.getLogin());
			lb.setPassword(usuario.getSenha());
			lb.envia();
			novo();
		} catch (Exception exception) {
			Messages.addGlobalError("Deu pau no cadastro");
			exception.printStackTrace();
		}
	}
	
//	UPLOAD DE IMAGEM DA CONTA
	
	private UploadedFile imagemConta;
	
	public void upload(FileUploadEvent arquivoUpload) {
		try {
			LoginBean lb = new LoginBean();
			usuario = lb.getUsuario();
			imagemConta = arquivoUpload.getFile();
			Path caminho = Files.createTempFile(null, null);
			Files.copy(imagemConta.getInputstream(), caminho, StandardCopyOption.REPLACE_EXISTING);
			Path origem = Paths.get(caminho.toString());
			Path destino = Paths.get("C:/Users/Usuário/Desktop/Trabalhos/Uploads/ImgConta/"+usuario.getCodigo() + ".png");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			Messages.addGlobalInfo("Upload de imagem realizado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UploadedFile getImagemConta() {
		return imagemConta;
	}

	public void setImagemConta(UploadedFile imagemConta) {
		this.imagemConta = imagemConta;
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

	public boolean isAllGood() {
		return allGood;
	}
}
