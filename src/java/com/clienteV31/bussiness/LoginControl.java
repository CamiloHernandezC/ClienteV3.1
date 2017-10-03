package com.clienteV31.bussiness;

import com.clienteV31.facades.UsuariosFacade;
import com.clienteV31.entities.Usuarios;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.Email;
import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Result;
import java.sql.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Stateless
public class LoginControl {

    @EJB
    private Email email;

    @EJB
    private UsuariosFacade ejbFacade;
    private Usuarios user;

    public String login(Usuarios pUser) {
        user = pUser;
        //TODO CODIFICACION DE LA CLAVE
        java.util.Date jd = new java.util.Date();
        Date d = new Date(jd.getTime());
        String squery = Querys.USUARIOS_ALL + " WHERE" + Querys.USUARIOS_ID + user.getIdUsuario() + "' AND" + Querys.USUARIOS_PASSWORD + user.getPassword()+"'";
        Result result = ejbFacade.findByQuery(squery, false);
        if (result.errorCode == Constants.OK) {
            user = (Usuarios) result.result;
            Long milisPerDay = 24*60*60*1000L;
            if(user.getFechaDesde().getTime()<=jd.getTime()  && (user.getFechaHasta().getTime()+milisPerDay)>=jd.getTime()){
                createHttpSession();
                return Constants.LOGIN_OK;
            }
            return Constants.EXPIRED_USER;
        } else {//If no user was found
            return Constants.LOGIN_FAIL;
        }
    }

    private void createHttpSession() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute(Constants.SESSION_USER, user);
        String IDSesion = String.valueOf(Math.random());
        user.setIDSesion(IDSesion);
        user.setSesion(true);
        //TODO SET THEME
        //ThemeController themeController = JsfUtil.findBean("themeController");
        //themeController.setSelectedLogin(user.getTema());
        //usuariosController.setSelected(user);
        ejbFacade.update(user);
    }
    
    public boolean validSession(){
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(httpSession==null){
            return false;
        }
        Usuarios httpUser = (Usuarios) httpSession.getAttribute(Constants.SESSION_USER);
        if(httpUser==null){
            return false;
        }
        String squery = Querys.USUARIOS_ALL + " WHERE" + Querys.USUARIOS_ID + httpUser.getIdUsuario() + "' AND" + Querys.USUARIOS_SESION + "true" + "' AND"+ Querys.USUARIOS_ID_SESION + httpUser.getIDSesion()+"'";
        Result result = ejbFacade.findByQuery(squery, false);
        return result.errorCode==Constants.OK;
    }
    
    public Result recoverPassword(Usuarios pUser){
        String squery = Querys.USUARIOS_ALL + " WHERE" + Querys.USUARIOS_ID + pUser.getIdUsuario()+"'";
        Result result = ejbFacade.findByQuery(squery, false);
        if(result.errorCode==Constants.OK){//VALID USER
            user = (Usuarios) result.result;
            email.sendEmail(user.getMail(),"Recover Password", createBodyRecoverPassword(user), null);//TODO create bundle property for recover password
            return new Result(user, Constants.OK);
        }
        return new Result(null, Constants.NO_RESULT_EXCEPTION);
    }
    
    private String createBodyRecoverPassword(Usuarios user) {
        return new java.util.Date()+"\n"
                + " \n"
                + "Your requested password\n"
                + " \n"
                + "This is an automatically generated message.\n"
                + "\n"
                + "Below you will find the correct username and password.\n"
                + "\n"
                + "Your username : "+user.getIdUsuario()+"\n"
                + "\n"
                + "Your password : "+user.getPassword()+"\n"
                + "\n"
                + "Best regards, Support Team";
    }

    public void logout() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(httpSession==null){
            return;
        }
        user = (Usuarios) httpSession.getAttribute(Constants.SESSION_USER);
        if(user==null){
            return;
        }
        user.setSesion(false);
        ejbFacade.update(user);
        httpSession.invalidate();//Finally we invalidate session, if we invalidate before update it will throws an exception
    }
    
}
