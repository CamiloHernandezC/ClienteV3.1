/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author chernandez
 */
@Entity
@Table(name = "vehiculos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculos.findAll", query = "SELECT v FROM Vehiculos v"),
    @NamedQuery(name = "Vehiculos.findByPlaca", query = "SELECT v FROM Vehiculos v WHERE v.placa = :placa"),
    @NamedQuery(name = "Vehiculos.findByDescripcion", query = "SELECT v FROM Vehiculos v WHERE v.descripcion = :descripcion"),
    @NamedQuery(name = "Vehiculos.findByColor1", query = "SELECT v FROM Vehiculos v WHERE v.color1 = :color1"),
    @NamedQuery(name = "Vehiculos.findByColor2", query = "SELECT v FROM Vehiculos v WHERE v.color2 = :color2"),
    @NamedQuery(name = "Vehiculos.findByFecha", query = "SELECT v FROM Vehiculos v WHERE v.fecha = :fecha")})
public class Vehiculos extends AbstractEntity{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Placa")
    private String placa;
    @Size(max = 140)
    @Column(name = "Descripcion")
    private String descripcion;
    @Size(max = 10)
    @Column(name = "Color1")
    private String color1;
    @Size(max = 10)
    @Column(name = "Color2")
    private String color2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "Usuario", referencedColumnName = "Id_Persona")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Personas usuario;
    @JoinColumn(name = "Estado", referencedColumnName = "Id_Estado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estados estado;
    @JoinColumn(name = "Entidad", referencedColumnName = "Id_Entidad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Entidades entidad;
    @JoinColumn(name = "Marca", referencedColumnName = "Id_Marca")
    @ManyToOne(fetch = FetchType.LAZY)
    private Marcas marca;
    @JoinColumn(name = "Pais", referencedColumnName = "Id_Pais")
    @ManyToOne(fetch = FetchType.LAZY)
    private Paises pais;
    @JoinColumn(name = "Departamento", referencedColumnName = "Id_Departamento")
    @ManyToOne(fetch = FetchType.EAGER)
    private Departamentos departamento;
    @JoinColumn(name = "Municipio", referencedColumnName = "Id_Municipio")
    @ManyToOne(fetch = FetchType.LAZY)
    private Municipios municipio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculos", fetch = FetchType.LAZY)
    private List<VehiculosSucursal> vehiculosSucursalList;
    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
    private List<Notificaciones> notificacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placa", fetch = FetchType.LAZY)
    private List<MovVehiculos> movVehiculosList;

    public Vehiculos() {
    }

    public Vehiculos(String placa) {
        this.placa = placa;
    }

    public Vehiculos(String placa, Date fecha) {
        this.placa = placa;
        this.fecha = fecha;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Personas getUsuario() {
        return usuario;
    }

    public void setUsuario(Personas usuario) {
        this.usuario = usuario;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public Entidades getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidades entidad) {
        this.entidad = entidad;
    }

    public Marcas getMarca() {
        return marca;
    }

    public void setMarca(Marcas marca) {
        this.marca = marca;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public Departamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamentos departamento) {
        this.departamento = departamento;
    }

    public Municipios getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipios municipio) {
        this.municipio = municipio;
    }

    @XmlTransient
    public List<VehiculosSucursal> getVehiculosSucursalList() {
        return vehiculosSucursalList;
    }

    public void setVehiculosSucursalList(List<VehiculosSucursal> vehiculosSucursalList) {
        this.vehiculosSucursalList = vehiculosSucursalList;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<MovVehiculos> getMovVehiculosList() {
        return movVehiculosList;
    }

    public void setMovVehiculosList(List<MovVehiculos> movVehiculosList) {
        this.movVehiculosList = movVehiculosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (placa != null ? placa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculos)) {
            return false;
        }
        Vehiculos other = (Vehiculos) object;
        if ((this.placa == null && other.placa != null) || (this.placa != null && !this.placa.equals(other.placa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Vehiculos[ placa=" + placa + " ]";
    }

    @Override
    public int getPrimaryKey() {
        //Nothing to do here
        return 0;
    }

    @Override
    public void setPrimaryKey(int primaryKey) {
        //Nothing to do here
    }

    @Override
    public void setUser(Personas user) {
        usuario = user;
    }

    @Override
    public void setDate(Date date) {
        fecha = date;
    }
}
