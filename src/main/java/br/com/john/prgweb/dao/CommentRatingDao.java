package br.com.john.prgweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.john.prgweb.bean.LoginBean;
import br.com.john.prgweb.domain.Comentario;
import br.com.john.prgweb.domain.CommentRating;
import br.com.john.prgweb.domain.Usuario;
import br.com.john.prgweb.util.HibernateUtil;

public class CommentRatingDao extends GenericDao<CommentRating> {

	public CommentRating buscaCommentRatingEspecifico(Comentario comment, Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CommentRating.class);
			consulta.add(Restrictions.eq("comentario", comment));
			consulta.add(Restrictions.eq("usuario", usuario));
			CommentRating resultado = (CommentRating) consulta.uniqueResult();
			return resultado;
		} catch (Exception e) {
			throw (e);
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<CommentRating> buscaCommentRating(Comentario comment) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CommentRating.class);
			consulta.add(Restrictions.eq("comentario", comment));
			List<CommentRating> resultado = consulta.list();
			LoginBean lb = new LoginBean();
			for (CommentRating cr : resultado) {
				if (lb.getUsuario() != null) {
					if (cr.getUsuario().getCodigo() == lb.getUsuario().getCodigo()) {
						cr.getComentario().setMeuRating(cr.getRating());
						break;
					}
				}
			}
			return resultado;
		} catch (Exception e) {
			throw (e);
		} finally {
			sessao.close();
		}
	}

	public void limpar(Usuario usuario) {
		List<CommentRating> crs = listarTodos();
		LoginBean lb = new LoginBean();
		if (crs.size() > 0) {
			for (CommentRating cr : crs) {
				if(cr.getUsuario().getCodigo() == lb.getUsuario().getCodigo() 
						|| cr.getComentario().getUsuario().getCodigo() == lb.getUsuario().getCodigo())
					excluir(cr);
			}
		}
	}
}
