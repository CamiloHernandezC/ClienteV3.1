/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chernandez
 */
@Entity
@Table(name = "config_form_gerencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigFormGerencia.findAll", query = "SELECT c FROM ConfigFormGerencia c"),
    @NamedQuery(name = "ConfigFormGerencia.findById", query = "SELECT c FROM ConfigFormGerencia c WHERE c.id = :id"),
    @NamedQuery(name = "ConfigFormGerencia.findByFormulario", query = "SELECT c FROM ConfigFormGerencia c WHERE c.formulario = :formulario"),
    @NamedQuery(name = "ConfigFormGerencia.findByCampo", query = "SELECT c FROM ConfigFormGerencia c WHERE c.campo = :campo"),
    @NamedQuery(name = "ConfigFormGerencia.findByMostrar", query = "SELECT c FROM ConfigFormGerencia c WHERE c.mostrar = :mostrar")})
public class ConfigFormGerencia extends AbstractConfigForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "Formulario")
    private String formulario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "Campo")
    private String campo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Mostrar")
    private boolean mostrar;
    @JoinColumn(name = "Sucursal", referencedColumnName = "Id_Sucursal")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sucursales sucursal;

    public ConfigFormGerencia() {
    }

    public ConfigFormGerencia(Integer id) {
        this.id = id;
    }

    public ConfigFormGerencia(Integer id, String formulario, String campo, boolean mostrar) {
        this.id = id;
        this.formulario = formulario;
        this.campo = campo;
        this.mostrar = mostrar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormulario() {
        return formulario;
    }

    @Override
    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    @Override
    public String getCampo() {
        return campo;
    }

    @Override
    public void setCampo(String campo) {
        this.campo = campo;
    }

    public boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public Sucursales getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursales sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigFormGerencia)) {
            return false;
        }
        ConfigFormGerencia other = (ConfigFormGerencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clienteV31.entities.ConfigFormGerencia[ id=" + id + " ]";
    }
    
}
