/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.bussiness.LoginControl;
import com.clienteV31.entities.Usuarios;
import com.clienteV31.utils.BundleUtils;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Navigation;
import com.clienteV31.utils.Result;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author chernandez
 */
@Named
@RequestScoped
public class LoginBean {
    
    @EJB
    private LoginControl loginControl;
    private Usuarios user;
    
     public Usuarios getUser() {
        if (user == null) {
            user = new Usuarios();
        }
        return user;
    }

    public void setUser(Usuarios pUser) {
        this.user = pUser;
    }
    
    public String login(){
        switch(loginControl.login(user)){
            case Constants.LOGIN_OK:
                return Navigation.PAGE_INDEX;
            case Constants.EXPIRED_USER:
                JsfUtil.addErrorMessage(BundleUtils.getBundleProperty("ExpiredError"));
                return "";
            case Constants.LOGIN_FAIL:
                JsfUtil.addErrorMessage(BundleUtils.getBundleProperty("LoginError"));
                return "";
            default:
                JsfUtil.addTecnicalErrorMessage();
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, Constants.LOGGER_ERROR_MESSAGE+"unexpected behavior: Tecnical error in login method");
                return "";
        }
    }
    
    public void recoverPassword(){
        Result result = loginControl.recoverPassword(user);
        if(result.errorCode==Constants.OK){
            user = (Usuarios) result.result;
            JsfUtil.addSuccessMessage("REVISE SU CORREO ELECTRÓNICO: "+user.getMail());//TODO CREATE BUNDLE PROPERTIE HERE
        }
        else{
            JsfUtil.addErrorMessage("EL ID DE USUARIO NO SE ENCUENTRA");
        }//TODO CREATE BUNDLE PROPERTIE HERE
        JsfUtil.redirectTo(Navigation.PAGE_LOGIN);
    }
    
    public String logout(){
        loginControl.logout();
        return Navigation.PAGE_LOGIN;//Redirect to login
    }
    
    public void validSession(){
        if(!loginControl.validSession()){
            JsfUtil.redirectTo("");//Redirect to login  
        }
    }
}
