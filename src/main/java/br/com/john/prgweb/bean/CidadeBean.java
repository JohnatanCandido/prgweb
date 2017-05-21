package br.com.john.prgweb.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.john.prgweb.dao.CidadeDao;
import br.com.john.prgweb.dao.EstadoDao;
import br.com.john.prgweb.domain.Cidade;
import br.com.john.prgweb.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable{
	
	private Cidade cidade;
	private List<Cidade> cidades;
	private List<Estado> estados;
	
	public void novo() {
		this.cidade = new Cidade();
		carregaUfs();
	}
	
	public void excluir(ActionEvent evento){
		try {
			cidade = (Cidade)evento.getComponent().getAttributes().get("cidadeExcluir");
			CidadeDao dao = new CidadeDao();
			dao.excluir(cidade);
			Messages.addGlobalInfo(cidade.getNome()+" Excluido com sucesso");
			listar();
		} catch (Exception exception) {
			Messages.addGlobalInfo("Erro ao excluir");
			exception.printStackTrace();
		}
		
	}
	
	@PostConstruct
	public void listar(){
		try {
			CidadeDao dao = new CidadeDao();
			cidades = dao.listarTodos();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao listar cidades");
			exception.printStackTrace();
		}
	}
	

	private void carregaUfs(){
		try {
			EstadoDao dao = new EstadoDao();
			estados = dao.listarTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvar() {
		try {
			CidadeDao dao = new CidadeDao();
			dao.Salvar(cidade);
			Messages.addGlobalInfo("UF cadastrada com sucesso");
			novo();
			listar();
		} catch (Exception exception) {
			Messages.addGlobalError("Erro ao cadastar UF");
			exception.printStackTrace();
		}
	}
	
	public void alterar(ActionEvent evento) {
		cidade = (Cidade)evento.getComponent().getAttributes().get("cidadeAlterar");
		carregaUfs();
		
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	
	
}
