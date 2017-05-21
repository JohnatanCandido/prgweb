package br.com.john.prgweb.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.john.prgweb.dao.ArquivoDao;
import br.com.john.prgweb.dao.UsuarioDao;
import br.com.john.prgweb.domain.Arquivo;
import br.com.john.prgweb.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped 	// estou usando SessionScoped para que quando mudar da tela
				// principal para a tela de busca a variavel "filtro" não seja
				// resetada!
public class ArquivoBean implements Serializable {

	private Arquivo arquivo;
	private List<Arquivo> arquivos;
	private List<Usuario> usuarios;
	private String filtro, ext;
	private boolean jogos;
	private boolean musica;
	private boolean livros;
	private boolean filmes;
	private boolean softwares;

	public void novo() {// MUDAR
		this.arquivo = new Arquivo();
		limpaFiltros();
		carregaUsuarios();
		listar();
	}

// =============================================================================
	private UploadedFile fileUp;
	
	public StreamedContent getFileDown() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(arquivo.getCaminhoArquivo());
		StreamedContent fileDown = new DefaultStreamedContent(stream, "image/jpg", "Arquivo_Baixado.png");
		return fileDown;
	}
	
	public void upload(FileUploadEvent arquivoUpload) {
		try {
			fileUp = arquivoUpload.getFile();
			String str = fileUp.getFileName();
			ext = str.substring(str.lastIndexOf('.'), str.length());
			Path caminho = Files.createTempFile(null, null);
			Files.copy(fileUp.getInputstream(), caminho, StandardCopyOption.REPLACE_EXISTING);
			arquivo.setCaminhoArquivo(caminho.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public UploadedFile getFileUp() {
		return fileUp;
	}

	public void setFileUp(UploadedFile file) {
		this.fileUp = file;
	}
// ====================================================================================================

	public ArquivoBean() {
		this.arquivo = new Arquivo();
		limpaFiltros();
		carregaUsuarios();
		listar();
	}

	public void limpaFiltros() {
		this.filtro = "";
		this.jogos = false;
		this.musica = false;
		this.livros = false;
		this.filmes = false;
		this.softwares = false;
	}

	public void excluir(ActionEvent evento) {
		try {
			arquivo = (Arquivo) evento.getComponent().getAttributes().get("xxxxxxxxxxxxxxxxxx");
			ArquivoDao dao = new ArquivoDao();
			dao.excluir(arquivo);
			//Path caminho = Paths.get("C:/Users/Usuário/Desktop/Trabalhos/Uploads/" + arquivo.getCodigo());
			
			Messages.addGlobalInfo(arquivo.getNome() + " excluido com sucesso");
			// listar();
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
	}

	public void alterar(ActionEvent evento) {
		arquivo = (Arquivo) evento.getComponent().getAttributes().get("xxxxxxxxxxxxxxxx");
		arquivo.setCaminhoArquivo("C:/Users/Usuário/Desktop/Trabalhos/Uploads/Arquivos/"+arquivo.getCodigo());
		carregaUsuarios();

	}

	public void salvar() {
		try {
			if(arquivo.getNome().equals(null) || arquivo.getNome().equals("") || arquivo.getNome().equals(" ")){
				throw new ArrayIndexOutOfBoundsException("O campo Nome é obrigatório!");
			}
			if(arquivo.getTipo().equals("nulo")){
				throw new ArrayIndexOutOfBoundsException("O campo Tipo é obrigatório!");
			}
			if(arquivo.getDescricao().equals(null) || arquivo.getDescricao().equals("") || arquivo.getDescricao().equals(" ")){
				throw new ArrayIndexOutOfBoundsException("O campo Descrição é obrigatório!");
			}
			this.arquivo.setDownloads(0);
			this.arquivo.setRating_arquivo(0.0);
			this.arquivo.setTamanho(0.0);
			LoginBean lb = new LoginBean();
			this.arquivo.setUsuario_upload(lb.getUsuario());
			ArquivoDao dao = new ArquivoDao();
			Arquivo arqUp = dao.merge(arquivo);
			arqUp.setCaminhoArquivo("C:/Users/Usuário/Desktop/Trabalhos/Uploads/Arquivos/"+arqUp.getCodigo() + ext);
			arqUp = dao.merge(arqUp);
			Path origem = Paths.get(arquivo.getCaminhoArquivo());
			Path destino = Paths.get("C:/Users/Usuário/Desktop/Trabalhos/Uploads/Arquivos/"+arqUp.getCodigo() + ext);
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			Messages.addGlobalInfo("Upload de arquivo realizado com sucesso");
			novo();
			listar();
		} catch (ArrayIndexOutOfBoundsException e) {
			Messages.addGlobalInfo(e.getMessage());
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao fazer upload do arquivo");
			exception.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			ArrayList<String> tipo = new ArrayList<String>();
			if (jogos) {
				tipo.add("Jogos");
			}
			if (musica) {
				tipo.add("Músicas");
			}
			if (filmes) {
				tipo.add("Filmes");
			}
			if (livros) {
				tipo.add("Livros");
			}
			if (softwares) {
				tipo.add("Softwares");
			}

			ArquivoDao dao = new ArquivoDao();
			arquivos = dao.listarPorNome(filtro, tipo);
			RatingArquivoBean rab = new RatingArquivoBean();
			ComentarioBean com = new ComentarioBean();
			for (Arquivo arq : arquivos) {
				arq.setComentarios(com.listar(arq.getCodigo()));
				double rating = rab.listarRating(arq.getCodigo());
				arq.setRating_arquivo(rating);
			}
			limpaFiltros();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar arquivos");
			exception.printStackTrace();
		}
	}

	public List<Arquivo> meusUploads(){
		LoginBean lb = new LoginBean();
		if(lb.isLogged()){		
			List<Arquivo> meusUploads = new ArrayList<Arquivo>();
			for(Arquivo arq: arquivos){
				if(arq.getUsuario_upload().getLogin().equals(lb.getUsuario().getLogin())){
					meusUploads.add(arq);
				}
			}
			return meusUploads;
		}
		return null;
	}
	
	private void carregaUsuarios() {
		try {
			UsuarioDao dao = new UsuarioDao();
			usuarios = dao.listarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// GETTERS E SETTERS
	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public boolean isJogos() {
		return jogos;
	}

	public void setJogos(boolean jogos) {
		this.jogos = jogos;
	}

	public boolean isMusica() {
		return musica;
	}

	public void setMusica(boolean musica) {
		this.musica = musica;
	}

	public boolean isLivros() {
		return livros;
	}

	public void setLivros(boolean livros) {
		this.livros = livros;
	}

	public boolean isFilmes() {
		return filmes;
	}

	public void setFilmes(boolean filmes) {
		this.filmes = filmes;
	}

	public boolean isSoftwares() {
		return softwares;
	}

	public void setSoftwares(boolean softwares) {
		this.softwares = softwares;
	}

}