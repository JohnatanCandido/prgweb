package br.com.john.prgweb.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.john.prgweb.domain.Arquivo;
import br.com.john.prgweb.domain.Usuario;
import br.com.john.prgweb.util.HibernateUtil;


public class GenericDao <Entidade>{
	
	private Class<Entidade> classe;
	
	@SuppressWarnings("unchecked")
	public GenericDao() {
		classe = (Class<Entidade>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void Salvar(Entidade entidade){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.save(entidade);
			t.commit();
		} catch (Exception e) {
			if(t!=null){
				t.rollback();
			}
			throw(e);
		}finally{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> listarTodos(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (Exception e) {
			throw(e);
		}finally{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> listarPorNome(String filtro, ArrayList<String> tipo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.like("nome", filtro, MatchMode.ANYWHERE));
			if(tipo.size() != 0){
				consulta.add(Restrictions.in("tipo", tipo));
			}
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (Exception e) {
			throw(e);
		}finally{
			sessao.close();
		}
	}	
	
	@SuppressWarnings("unchecked")
	public Entidade buscar(Long codigo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.idEq(codigo));
			Entidade resultado = (Entidade)consulta.uniqueResult();
			return resultado;
		} catch (Exception e) {
			throw e;
		}finally{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> buscarRating(Long codigo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.eq("arquivo.codigo", codigo));
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (Exception e) {
			throw e;
		}finally{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entidade buscarRatingEspecifico(Usuario usuario, Arquivo arquivo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.eq("usuario", usuario));
			consulta.add(Restrictions.eq("arquivo", arquivo));
			Entidade resultado = (Entidade)consulta.uniqueResult();
			return resultado;
		} catch (Exception e) {
			throw e;
		}finally{
			sessao.close();
		}
	}

	
	public void excluir(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.delete(entidade);
			t.commit();
		} catch (Exception exception) {
			if(t!=null){
				t.rollback();
			}
			throw exception;
		}finally{
			sessao.close();
		}


	}
	
	
	@SuppressWarnings("unchecked")
	public Entidade merge(Entidade entidade){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			Entidade retorno = (Entidade)sessao.merge(entidade);
			t.commit();
			return retorno;
		} catch (Exception exception) {
			if(t!=null){
				t.rollback();
			}
			throw exception;
		}finally{
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entidade> buscarComentario(Long codigo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.eq("arquivo.codigo", codigo));
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (Exception e) {
			throw e;
		}finally{
			sessao.close();
		}
	}

}
