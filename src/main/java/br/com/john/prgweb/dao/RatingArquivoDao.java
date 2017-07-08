package br.com.john.prgweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.john.prgweb.bean.LoginBean;
import br.com.john.prgweb.domain.Arquivo;
import br.com.john.prgweb.domain.RatingArquivo;
import br.com.john.prgweb.domain.Usuario;
import br.com.john.prgweb.util.HibernateUtil;

public class RatingArquivoDao extends GenericDao<RatingArquivo> {

	public RatingArquivo buscarRatingEspecifico(Usuario usuario, Arquivo arquivo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RatingArquivo.class);
			consulta.add(Restrictions.eq("usuario", usuario));
			consulta.add(Restrictions.eq("arquivo", arquivo));
			RatingArquivo resultado = (RatingArquivo) consulta.uniqueResult();
			if (resultado != null) {
				return resultado;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<RatingArquivo> buscarRating(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RatingArquivo.class);
			consulta.add(Restrictions.eq("arquivo.codigo", codigo));
			List<RatingArquivo> resultado = consulta.list();
			return resultado;
		} catch (Exception e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public void limpar(Usuario usuario) {
		List<RatingArquivo> ras = listarTodos();
		LoginBean lb = new LoginBean();
		if (ras.size() > 0) {
			for (RatingArquivo ra : ras) {
				if(ra.getUsuario().getCodigo() == lb.getUsuario().getCodigo() 
						|| ra.getArquivo().getUsuario_upload().getCodigo() == lb.getUsuario().getCodigo())
					excluir(ra);
			}
		}
	}
}
