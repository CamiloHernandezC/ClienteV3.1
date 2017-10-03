/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.facades.MenuClienteFacade;
import com.clienteV31.entities.MenuCliente;
import com.clienteV31.entities.Usuarios;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.BundleUtils;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author MAURICIO
 */
@Named
@SessionScoped
public class MenuBean implements Serializable {

    @EJB
    private MenuClienteFacade menuClienteFacade;

    private MenuModel menu;

    /**
     * Creates a new instance of MenuController
     */
    public MenuBean() {
    }

    //<editor-fold desc="Menu construction" defaultstate="collapsed">
    @PostConstruct
    public void init() {
        menu = new DefaultMenuModel();
        Usuarios user = JsfUtil.getSessionUser();
        String menuQuery = Querys.MENU_CLIENTE_JOIN_PRIVILEGIOS + " WHERE (" + Querys.MENU_CLIENTE_NIVEL_MORE_EQUAL + user.getPrivilegios() + "' AND " + Querys.MENU_CLIENTE_TIPO + Constants.MENU_TYPE_SUPER_FATHER + "'" + Querys.MENU_CLIENTE_HAS_PRIVILEGE + Querys.MENU_CLIENTE_ACTIVE;
        List<MenuCliente> menuItems = (List<MenuCliente>) menuClienteFacade.findByQueryArray(menuQuery).result;
        for (MenuCliente item : menuItems) {
            DefaultSubMenu subMenu = new DefaultSubMenu(BundleUtils.getBundleProperty(item.getNombre()));
            subMenu = loadSubMenu(item.getMenuClienteList(), subMenu);
            menu.addElement(subMenu);
        }
    }
    //</editor-fold>

    private DefaultSubMenu loadSubMenu(List<MenuCliente> childrens, DefaultSubMenu subMenu) {
        for (MenuCliente children : childrens) {
            if (!children.getEstado()) {
                continue;
            }
            if (children.getTipo() == Constants.MENU_TYPE_FATHER) {
                DefaultSubMenu subSubMenu = new DefaultSubMenu(BundleUtils.getBundleProperty(children.getNombre()));
                subSubMenu = loadSubMenu(children.getMenuClienteList(), subSubMenu);
                subMenu.addElement(subSubMenu);
                continue;
            }
            DefaultMenuItem menuItem = new DefaultMenuItem(BundleUtils.getBundleProperty(children.getNombre()));
            menuItem.setUrl(children.getUrl());
            subMenu.addElement(menuItem);
        }
        return subMenu;
    }

    public MenuModel getMenu() {
        return menu;
    }

}
