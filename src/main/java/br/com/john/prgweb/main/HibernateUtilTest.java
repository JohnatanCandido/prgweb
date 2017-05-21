package br.com.john.prgweb.main;

//import java.util.ArrayList;

import org.hibernate.Session;

//import br.com.john.prgweb.dao.UsuarioDao;
//import br.com.john.prgweb.domain.Usuario;
import br.com.john.prgweb.util.HibernateUtil;

public class HibernateUtilTest {

	public static void main(String[]args){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
		/*
		Usuario u = new Usuario();
		u.setNick("Aernaur");
		u.setLogin("seila123");
		u.setSenha("1234");
		u.setEmail("john.acdc@hotmail.com");
		
		UsuarioDao dao = new UsuarioDao();
		dao.Salvar(u);
		
		UsuarioDao usuario = new UsuarioDao();
		ArrayList<Usuario> lista = (ArrayList<Usuario>)usuario.listarTodos();
		for(Usuario u: lista){
			System.out.println(u.getLogin()+" - "+u.getSenha());
		}*/
		
	}

}