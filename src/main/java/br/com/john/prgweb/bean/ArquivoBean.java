package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.john.prgweb.dao.ArquivoDao;
import br.com.john.prgweb.dao.ComentarioDao;
import br.com.john.prgweb.dao.CommentRatingDao;
import br.com.john.prgweb.dao.RatingArquivoDao;
import br.com.john.prgweb.domain.Arquivo;
import br.com.john.prgweb.domain.Comentario;
import br.com.john.prgweb.domain.CommentRating;
import br.com.john.prgweb.domain.RatingArquivo;
import br.com.john.prgweb.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped // estou usando SessionScoped para que quando mudar da tela
				// principal para a tela de busca a variavel "filtro" não seja
				// resetada!
public class ArquivoBean implements Serializable {

	private Arquivo arquivo;
	private List<Arquivo> arquivos;
	private List<Usuario> usuarios;
	private String filtro, ext;
	private boolean jogos, musica, livros, filmes, softwares;
	private boolean disabled;

	public void novo() {
		this.arquivo = new Arquivo();
		this.arquivo.setDownloads(0);
		this.arquivo.setRating_arquivo(0.0);
		this.arquivo.setTamanho(0.0);
		this.disabled = true;
		limpaFiltros();
		listar();
	}

	public ArquivoBean() {
		novo();
	}

	// =============================================================================
	private UploadedFile fileUp;

	public void upload(FileUploadEvent arquivoUpload) {
		try {
			fileUp = arquivoUpload.getFile();
			String str = fileUp.getFileName();
			ext = str.substring(str.lastIndexOf('.'), str.length());
			Path caminho = Files.createTempFile(null, null);
			Files.copy(fileUp.getInputstream(), caminho, StandardCopyOption.REPLACE_EXISTING);
			arquivo.setCaminhoArquivo(caminho.toString());
			disabled = false;
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

	private UploadedFile imagemConta;

	public void uploadImg(FileUploadEvent arquivoUpload) {
		try {
			imagemConta = arquivoUpload.getFile();
			Path caminho = Files.createTempFile(null, null);
			Files.copy(imagemConta.getInputstream(), caminho, StandardCopyOption.REPLACE_EXISTING);
			Path origem = Paths.get(caminho.toString());
			Path destino = Paths
					.get("C:/Users/Usuário/Desktop/Trabalhos/Uploads/ImgArquivo/" + arquivo.getCodigo() + ".png");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			Messages.addGlobalInfo("Upload de imagem realizado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			arquivo = (Arquivo) evento.getComponent().getAttributes().get("arquivoExcluir");
			ArquivoDao dao = new ArquivoDao();
			RatingArquivoDao rad = new RatingArquivoDao();
			CommentRatingDao crd = new CommentRatingDao();
			ComentarioDao cd = new ComentarioDao();
			for(RatingArquivo ra: rad.listarTodos()){
				if(ra.getArquivo().getCodigo() == arquivo.getCodigo()){
					rad.excluir(ra);
				}
			}
			for(CommentRating cr: crd.listarTodos()){
				if(cr.getComentario().getArquivo().getCodigo() == arquivo.getCodigo()){
					crd.excluir(cr);
				}
			}
			for(Comentario c: cd.listarTodos()){
				if(c.getArquivo().getCodigo() == arquivo.getCodigo()){
					cd.excluir(c);
				}
			}
			Path caminho = Paths.get(arquivo.getCaminhoArquivo());
			dao.excluir(arquivo);
			Files.delete(caminho);
			listar();
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
	}

	public void excluirTodosArquivos(Usuario usuario) {
		try {
			ArquivoDao dao = new ArquivoDao();
			List<Arquivo> arqs = meusUploads();
			if (arqs.size() > 0) {
				for (Arquivo arq : arqs) {
					if (arq.getUsuario_upload().getCodigo() == usuario.getCodigo()) {
						Path caminho = Paths.get(arq.getCaminhoArquivo());
						dao.excluir(arq);
						Files.delete(caminho);
						listar();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void alterar(ActionEvent evento) {
		disabled = false;
		arquivo = (Arquivo) evento.getComponent().getAttributes().get("arquivoAlterarar");
		arquivo.setCaminhoArquivo(arquivo.getCaminhoArquivo());
	}

	public void salvar() {
		try {
			if (arquivo.getNome().equals(null) || arquivo.getNome().equals("") || arquivo.getNome().equals(" ")) {
				Messages.addGlobalError("O campo Nome é obrigatório!");
				return;
			}
			if (arquivo.getTipo().equals("nulo")) {
				Messages.addGlobalError("O campo Tipo é obrigatório!");
				return;
			}
			if (arquivo.getDescricao().equals(null) || arquivo.getDescricao().equals("")
					|| arquivo.getDescricao().equals(" ")) {
				Messages.addGlobalError("O campo Descrição é obrigatório!");
				return;
			}
			LoginBean lb = new LoginBean();
			this.arquivo.setUsuario_upload(lb.getUsuario());
			Double tamanho = (double) fileUp.getSize() / 1024;
			this.arquivo.setTamanho(BigDecimal.valueOf(tamanho).setScale(1, RoundingMode.HALF_UP).doubleValue());
			ArquivoDao dao = new ArquivoDao();
			Arquivo arqUp = dao.merge(arquivo);
			arqUp.setCaminhoArquivo("C:/Users/Usuário/Desktop/Trabalhos/Uploads/Arquivos/" + arqUp.getCodigo() + ext);
			arqUp = dao.merge(arqUp);
			Path origem = Paths.get(arquivo.getCaminhoArquivo());
			Path destino = Paths.get("C:/Users/Usuário/Desktop/Trabalhos/Uploads/Arquivos/" + arqUp.getCodigo() + ext);
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			Messages.addGlobalInfo("Upload de arquivo realizado com sucesso");
			novo();
			listar();
			disabled = true;
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao fazer upload do arquivo");
			exception.printStackTrace();
		}
	}

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
			ComentarioBean cb = new ComentarioBean();
			for (Arquivo arq : arquivos) {
				arq.setComentarios(cb.buscaComentarios(arq));
				double rating = rab.listarRating(arq.getCodigo());
				arq.setRating_arquivo(rating);
				LoginBean lb = new LoginBean();
				if (lb.isLogged()) {
					RatingArquivoDao rad = new RatingArquivoDao();
					RatingArquivo meuRating = rad.buscarRatingEspecifico(lb.getUsuario(), arq);
					if (meuRating != null)
						arq.setMeuRating(meuRating.getRating());
					else
						arq.setMeuRating(0);
				}
			}
			limpaFiltros();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar arquivos");
			exception.printStackTrace();
		}
	}

	public List<Arquivo> meusUploads() {
		LoginBean lb = new LoginBean();
		if (lb.isLogged()) {
			List<Arquivo> meusUploads = new ArrayList<Arquivo>();
			Usuario usu = lb.getUsuario();
			usu.setTotal_downloads(0);
			double media = 0.0;
			for (Arquivo arq : arquivos) {
				if (arq.getUsuario_upload().getLogin().equals(usu.getLogin())) {
					usu.setTotal_downloads(usu.getTotal_downloads() + arq.getDownloads());
					media += arq.getRating_arquivo();
					meusUploads.add(arq);
				}
			}
			media /= meusUploads.size();
			usu.setMedia_rating(String.format("%.2f", media));
			return meusUploads;
		}
		return null;
	}

	public boolean isMeuArquivo() {
		LoginBean lb = new LoginBean();
		if (lb.isLogged()) {
			return lb.getUsuario().getCodigo() == arquivo.getUsuario_upload().getCodigo();
		}
		return false;
	}
	
	public boolean isNotMeuArquivo() {
		LoginBean lb = new LoginBean();
		if (lb.isLogged()) {
			return lb.getUsuario().getCodigo() != arquivo.getUsuario_upload().getCodigo();
		}
		return false;
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

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}