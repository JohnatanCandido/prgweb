package br.com.john.prgweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.john.prgweb.bean.LoginBean;
import br.com.john.prgweb.domain.Comentario;
import br.com.john.prgweb.domain.Usuario;
import br.com.john.prgweb.util.HibernateUtil;

public class ComentarioDao extends GenericDao<Comentario> {

	@SuppressWarnings("unchecked")
	public List<Comentario> buscarComentario(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Comentario.class);
			consulta.add(Restrictions.eq("arquivo.codigo", codigo));
			List<Comentario> resultado = consulta.list();
			return resultado;
		} catch (Exception e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public void limpar(Usuario usuario) {
		List<Comentario> comments = listarTodos();
		LoginBean lb = new LoginBean();
		if (comments.size() > 0) {
			for (Comentario comment : comments) {
				if(comment.getUsuario().getCodigo() == lb.getUsuario().getCodigo()
						|| comment.getArquivo().getUsuario_upload().getCodigo() == lb.getUsuario().getCodigo())
					excluir(comment);
			}
		}
	}
}
