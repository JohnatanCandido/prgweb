package br.com.john.prgweb.dao;

import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.john.prgweb.domain.Usuario;
import br.com.john.prgweb.util.HibernateUtil;

public class UsuarioDao extends GenericDao<Usuario>{

	public Usuario getUsuario(String login, String senha){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		String hql = "FROM Usuario u where u.login = :username AND u.senha = :password";
		Query query = sessao.createQuery(hql);
		
		query.setParameter("username", login);
		query.setParameter("password", senha);
		
		return (Usuario) query.uniqueResult();
	}
	
	public Usuario getCurrentUser() {
		return (Usuario) FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.get("current_user");
	}
	
}
