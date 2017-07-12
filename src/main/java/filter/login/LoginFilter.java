package filter.login;

import java.io.IOException;
import javax.inject.Inject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mb.MBKorisnik;

public class LoginFilter implements Filter {
    @Inject
    MBKorisnik mbKorisnik;
    

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        String url = request.getRequestURI();
        if(mbKorisnik == null || mbKorisnik.getKorisnik() == null || !mbKorisnik.isLoggedIn()){
            response.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
        }
        else{
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

}
