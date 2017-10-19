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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author chernandez
 */
@Entity
@Table(name = "mov_personas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovPersonas.findAll", query = "SELECT m FROM MovPersonas m"),
    @NamedQuery(name = "MovPersonas.findByIdMovPersona", query = "SELECT m FROM MovPersonas m WHERE m.idMovPersona = :idMovPersona"),
    @NamedQuery(name = "MovPersonas.findByMomentoEntrada", query = "SELECT m FROM MovPersonas m WHERE m.momentoEntrada = :momentoEntrada"),
    @NamedQuery(name = "MovPersonas.findByMetodoEntrada", query = "SELECT m FROM MovPersonas m WHERE m.metodoEntrada = :metodoEntrada"),
    @NamedQuery(name = "MovPersonas.findByMomentoSalida", query = "SELECT m FROM MovPersonas m WHERE m.momentoSalida = :momentoSalida"),
    @NamedQuery(name = "MovPersonas.findByMetodoSalida", query = "SELECT m FROM MovPersonas m WHERE m.metodoSalida = :metodoSalida"),
    @NamedQuery(name = "MovPersonas.findBySalidaForzosa", query = "SELECT m FROM MovPersonas m WHERE m.salidaForzosa = :salidaForzosa"),
    @NamedQuery(name = "MovPersonas.findByIngresoForzado", query = "SELECT m FROM MovPersonas m WHERE m.ingresoForzado = :ingresoForzado"),
    @NamedQuery(name = "MovPersonas.findByFecha", query = "SELECT m FROM MovPersonas m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "MovPersonas.findByFechaEntrada", query = "SELECT m FROM MovPersonas m WHERE m.fechaEntrada = :fechaEntrada"),
    @NamedQuery(name = "MovPersonas.findByHoraEntrada", query = "SELECT m FROM MovPersonas m WHERE m.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "MovPersonas.findByFechaSalida", query = "SELECT m FROM MovPersonas m WHERE m.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "MovPersonas.findByHoraSalida", query = "SELECT m FROM MovPersonas m WHERE m.horaSalida = :horaSalida")})
public class MovPersonas extends AbstractEntity{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id_Mov_Persona")
    private Integer idMovPersona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Momento_Entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date momentoEntrada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Metodo_Entrada")
    private int metodoEntrada;
    @Column(name = "Momento_Salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date momentoSalida;
    @Column(name = "Metodo_Salida")
    private Integer metodoSalida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Salida_Forzosa")
    private boolean salidaForzosa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ingreso_Forzado")
    private boolean ingresoForzado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "Fecha_Entrada")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrada;
    @Column(name = "Hora_Entrada")
    @Temporal(TemporalType.TIME)
    private Date horaEntrada;
    @Column(name = "Fecha_Salida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @Column(name = "Hora_Salida")
    @Temporal(TemporalType.TIME)
    private Date horaSalida;
    @JoinColumn(name = "Usuario", referencedColumnName = "Id_Persona")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Personas usuario;
    @JoinColumn(name = "Area", referencedColumnName = "Id_Area_Empresa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AreasEmpresa area;
    @JoinColumn(name = "Persona_Autoriza", referencedColumnName = "Id_Persona")
    @ManyToOne(fetch = FetchType.LAZY)
    private Personas personaAutoriza;
    @JoinColumns({
    @JoinColumn(name = "Id_Persona", referencedColumnName = "Id_Persona"),
    @JoinColumn(name = "Sucursal", referencedColumnName = "Sucursal")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PersonasSucursal personasSucursal;
    @JoinColumn(name = "Porteria", referencedColumnName = "Id_Porteria")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Porterias porteria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimientoPersona", fetch = FetchType.LAZY)
    private List<MovDocumentos> movDocumentosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimientoPersona", fetch = FetchType.LAZY)
    private List<MovMateriales> movMaterialesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movPersonaEntrada", fetch = FetchType.LAZY)
    private List<MovObjetos> movObjetosList;
    @OneToMany(mappedBy = "movPersonaSalida", fetch = FetchType.LAZY)
    private List<MovObjetos> movObjetosList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movPersonaEntrada", fetch = FetchType.LAZY)
    private List<MovVehiculos> movVehiculosList;
    @OneToMany(mappedBy = "movPersonaSalida", fetch = FetchType.LAZY)
    private List<MovVehiculos> movVehiculosList1;

    public MovPersonas() {
    }

    public MovPersonas(Integer idMovPersona) {
        this.idMovPersona = idMovPersona;
    }

    public MovPersonas(Integer idMovPersona, Date momentoEntrada, int metodoEntrada, boolean salidaForzosa, boolean ingresoForzado, Date fecha) {
        this.idMovPersona = idMovPersona;
        this.momentoEntrada = momentoEntrada;
        this.metodoEntrada = metodoEntrada;
        this.salidaForzosa = salidaForzosa;
        this.ingresoForzado = ingresoForzado;
        this.fecha = fecha;
    }

    public PersonasSucursal getPersonasSucursal() {
        return personasSucursal;
    }

    public void setPersonasSucursal(PersonasSucursal personasSucursal) {
        this.personasSucursal = personasSucursal;
    }

    public Integer getIdMovPersona() {
        return idMovPersona;
    }

    public void setIdMovPersona(Integer idMovPersona) {
        this.idMovPersona = idMovPersona;
    }

    public Date getMomentoEntrada() {
        return momentoEntrada;
    }

    public void setMomentoEntrada(Date momentoEntrada) {
        this.momentoEntrada = momentoEntrada;
    }

    public int getMetodoEntrada() {
        return metodoEntrada;
    }

    public void setMetodoEntrada(int metodoEntrada) {
        this.metodoEntrada = metodoEntrada;
    }

    public Date getMomentoSalida() {
        return momentoSalida;
    }

    public void setMomentoSalida(Date momentoSalida) {
        this.momentoSalida = momentoSalida;
    }

    public Integer getMetodoSalida() {
        return metodoSalida;
    }

    public void setMetodoSalida(Integer metodoSalida) {
        this.metodoSalida = metodoSalida;
    }

    public boolean getSalidaForzosa() {
        return salidaForzosa;
    }

    public void setSalidaForzosa(boolean salidaForzosa) {
        this.salidaForzosa = salidaForzosa;
    }

    public boolean getIngresoForzado() {
        return ingresoForzado;
    }

    public void setIngresoForzado(boolean ingresoForzado) {
        this.ingresoForzado = ingresoForzado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Personas getUsuario() {
        return usuario;
    }

    public void setUsuario(Personas usuario) {
        this.usuario = usuario;
    }

    public AreasEmpresa getArea() {
        return area;
    }

    public void setArea(AreasEmpresa area) {
        this.area = area;
    }

    public Personas getPersonaAutoriza() {
        return personaAutoriza;
    }

    public void setPersonaAutoriza(Personas personaAutoriza) {
        this.personaAutoriza = personaAutoriza;
    }

    public Porterias getPorteria() {
        return porteria;
    }

    public void setPorteria(Porterias porteria) {
        this.porteria = porteria;
    }

    @XmlTransient
    public List<MovDocumentos> getMovDocumentosList() {
        return movDocumentosList;
    }

    public void setMovDocumentosList(List<MovDocumentos> movDocumentosList) {
        this.movDocumentosList = movDocumentosList;
    }

    @XmlTransient
    public List<MovMateriales> getMovMaterialesList() {
        return movMaterialesList;
    }

    public void setMovMaterialesList(List<MovMateriales> movMaterialesList) {
        this.movMaterialesList = movMaterialesList;
    }

    @XmlTransient
    public List<MovObjetos> getMovObjetosList() {
        return movObjetosList;
    }

    public void setMovObjetosList(List<MovObjetos> movObjetosList) {
        this.movObjetosList = movObjetosList;
    }

    @XmlTransient
    public List<MovObjetos> getMovObjetosList1() {
        return movObjetosList1;
    }

    public void setMovObjetosList1(List<MovObjetos> movObjetosList1) {
        this.movObjetosList1 = movObjetosList1;
    }

    @XmlTransient
    public List<MovVehiculos> getMovVehiculosList() {
        return movVehiculosList;
    }

    public void setMovVehiculosList(List<MovVehiculos> movVehiculosList) {
        this.movVehiculosList = movVehiculosList;
    }

    @XmlTransient
    public List<MovVehiculos> getMovVehiculosList1() {
        return movVehiculosList1;
    }

    public void setMovVehiculosList1(List<MovVehiculos> movVehiculosList1) {
        this.movVehiculosList1 = movVehiculosList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovPersona != null ? idMovPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovPersonas)) {
            return false;
        }
        MovPersonas other = (MovPersonas) object;
        if ((this.idMovPersona == null && other.idMovPersona != null) || (this.idMovPersona != null && !this.idMovPersona.equals(other.idMovPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.MovPersonas[ idMovPersona=" + idMovPersona + " ]";
    }
    
    @Override
    public int getPrimaryKey() {
        return idMovPersona;
    }
    
    @Override
    public void setPrimaryKey(int primaryKey) {
        idMovPersona = primaryKey;
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
