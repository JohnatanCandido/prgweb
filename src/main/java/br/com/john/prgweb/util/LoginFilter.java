package br.com.john.prgweb.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.john.prgweb.domain.Usuario;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Usuario user = null;
        HttpSession sess = ((HttpServletRequest)request).getSession(false);
        
        if (sess != null){
            user = (Usuario) sess.getAttribute("current_user");
        }      

        if (user == null) {
           String contextPath = ((HttpServletRequest)request).getContextPath();
           ((HttpServletResponse) response).sendRedirect(contextPath + "/pages/index.xhtml");
        } else {
              chain.doFilter(request, response);
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
