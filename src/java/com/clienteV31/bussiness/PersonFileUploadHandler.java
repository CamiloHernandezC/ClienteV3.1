/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.AreasEmpresa;
import com.clienteV31.entities.Arl;
import com.clienteV31.entities.Departamentos;
import com.clienteV31.entities.Entidades;
import com.clienteV31.entities.Eps;
import com.clienteV31.entities.Municipios;
import com.clienteV31.entities.Paises;
import com.clienteV31.entities.Personas;
import com.clienteV31.entities.PersonasSucursal;
import com.clienteV31.entities.Sucursales;
import com.clienteV31.entities.TiposDocumento;
import com.clienteV31.facades.PersonasFacade;
import com.clienteV31.facades.PersonasSucursalFacade;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.FileUploadHandler;
import com.clienteV31.utils.Result;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author chernandez
 */
@Stateless
public class PersonFileUploadHandler extends FileUploadHandler {

    @EJB
    private AreasControl areasControl;
    @EJB
    private PersonasControl personasControl;
    @EJB
    private PersonasSucursalControl personasSucursalControl;

    @EJB
    private PersonasSucursalFacade personasSucursalFacade;
    @EJB
    private PersonasFacade personasFacade;

    private PersonasSucursal personasSucursal;
    private Personas person;

    @Override
    public Result handleUploadedFile(UploadedFile uploadedFile, int numberOfColums) {
        Result result = super.handleUploadedFile(uploadedFile, numberOfColums); //To change body of generated methods, choose Tools | Templates.
        if (result.errorCode != Constants.OK) {
            return result;
        }
        if (!verifyStructure((String[][]) result.result)) {
            result.errorCode = Constants.WRONG_STRUCTURE_ERROR;
            result.result = "THE STRUCTURE DOESN'T COINCIDE WHIT TEMPLATE, PLEASE DOWNLOAD TEMPLATE AND TRY AGAIN";//TODO CREATE BUNDLE PROPERTY HERE
            return result;
        }
        ArrayList<PersonasSucursal> personsToSave = castFileToEntity((String[][]) result.result);
        return saveFile(personsToSave);
    }

    /**
     * This method checks primary keys are not null, in order to create person
     * and persona sucursal entities to be more efficient, only use it in update
     * from file
     *
     * @param t
     * @return
     */
    private boolean checkNotNullFieldsToUpdate(PersonasSucursal t) {
        return t.getSucursales() == null || t.getPersonas().getTipoDocumento() == null || t.getPersonas().getNumeroDocumento() == null;
    }

    private Result saveFile(ArrayList<PersonasSucursal> array) {

        ArrayList<PersonasSucursal> arrayError = new ArrayList<>();
        for (PersonasSucursal t : array) {
            if (checkNotNullFieldsToUpdate(t)) { //NOT NULL FIELDS ARE CHECKED IN CREATE, BUT TO BE MORE EFFICIENT WE CHECK THEM HERE
                t.setErrorObservation("Los campos obligatorios no están completos");//TODO CREATE PROPERTY HERE                
                arrayError.add(t);
                continue;
            }

            Result existPerson = personasControl.findPersonByDocument(t.getPersonas());//Assig to find person by document
            if (existPerson.errorCode == Constants.NO_RESULT_EXCEPTION) {
                if (checkNotNullFieldsToCreate(t)) {
                    t.setErrorObservation("Los campos obligatorios no están completos");//TODO CREATE PROPERTY HERE                
                    arrayError.add(t);
                    continue;

                }
                Result createResult = personasControl.create(t.getPersonas(), t);
                if (createResult.errorCode != 0) {
                    t.setErrorObservation((String) createResult.result);
                    arrayError.add(t);
                }
                continue;
            }
            if (existPerson.errorCode != Constants.OK) {
                //LOGGER
                continue;
            }
            Result existSpecificPerson = personasSucursalControl.findSpecificPerson(((Personas) existPerson.result).getIdPersona(), t.getSucursales().getIdSucursal());
            person = t.getPersonas();
            personasSucursal = t;
            if (existSpecificPerson.errorCode == Constants.OK) {
                updateFromFile((PersonasSucursal) existSpecificPerson.result);
            } else {
                personasSucursalFacade.create(t);
            }
            updateFromFile(((Personas) existPerson.result));
        }
        if (arrayError.isEmpty()) {
            return new Result(null, Constants.OK);
        } else {
            return new Result(arrayError, Constants.PARTIAL_UPLOADED_FILE_ERROR);
        }
    }

    /**
     * Update person's properties from file
     *
     * @param existingPerson
     * @return
     */
    public Result updateFromFile(Personas existingPerson) {
        person.setIdPersona(existingPerson.getIdPersona());
        //<editor-fold desc="update properties if added" defaultstate="collapsed">
        if (person.getEmpresaOrigen() == null) {
            person.setEmpresaOrigen(existingPerson.getEmpresaOrigen());
        }
        if (person.getNombre1() == null && existingPerson.getNombre1() != null) {
            person.setNombre1(existingPerson.getNombre1());
        }
        if (person.getNombre2() == null && existingPerson.getNombre2() != null) {
            person.setNombre2(existingPerson.getNombre2());
        }
        if (person.getApellido1() == null && existingPerson.getApellido1() != null) {
            person.setApellido1(existingPerson.getApellido2());
        }
        if (person.getApellido2() == null && existingPerson.getApellido2() != null) {
            person.setApellido2(existingPerson.getApellido2());
        }
        if (person.getDireccion() == null && existingPerson.getDireccion() != null) {
            person.setDireccion(existingPerson.getDireccion());
        }
        if (person.getTelefono() == null && existingPerson.getTelefono() != null) {
            person.setTelefono(existingPerson.getTelefono());
        }
        if (person.getCelular() == null && existingPerson.getCelular() != null) {
            person.setCelular(existingPerson.getCelular());
        }
        if (person.getMail() == null && existingPerson.getMail() != null) {
            person.setMail(existingPerson.getMail());
        }
        if (person.getPersonaContacto() == null && existingPerson.getPersonaContacto() != null) {
            person.setPersonaContacto(existingPerson.getPersonaContacto());
        }
        if (person.getTelPersonaContacto() == null && existingPerson.getTelPersonaContacto() != null) {
            person.setTelPersonaContacto(existingPerson.getPersonaContacto());
        }
        if (person.getPais() == null && existingPerson.getPais() != null) {
            person.setPais(existingPerson.getPais());
        }
        if (person.getDepartamento() == null && existingPerson.getDepartamento() != null) {
            person.setDepartamento(existingPerson.getDepartamento());
        }
        if (person.getMunicipio() == null && existingPerson.getMunicipio() != null) {
            person.setMunicipio(existingPerson.getMunicipio());
        }
        if (person.getEps() == null && existingPerson.getEps() != null) {
            person.setEps(existingPerson.getEps());
        }
        if (person.getFechaVigenciaSS() == null && existingPerson.getFechaVigenciaSS() != null) {
            person.setFechaVigenciaSS(existingPerson.getFechaVigenciaSS());
        }
        if (person.getArl() == null && existingPerson.getArl() != null) {
            person.setArl(existingPerson.getArl());
        }
        if (person.getSexo() == null && existingPerson.getSexo() != null) {
            person.setSexo(existingPerson.getSexo());
        }
        if (person.getRh() == null && existingPerson.getRh() != null) {
            person.setRh(existingPerson.getRh());
        }
        if (person.getFechaNacimiento() == null && existingPerson.getFechaNacimiento() != null) {
            person.setFechaNacimiento(existingPerson.getFechaNacimiento());
        }
        person.setEstado(existingPerson.getEstado());

        return personasFacade.update(person);
        //</editor-fold>

    }

    private Result updateFromFile(PersonasSucursal existingPerson) {
        personasSucursal.setPersonasSucursalPK(existingPerson.getPersonasSucursalPK());
        personasSucursal.setPersonas(existingPerson.getPersonas());
        personasSucursal.setSucursales(existingPerson.getSucursales());
        if (personasSucursal.getArea() == null) {
            personasSucursal.setArea(existingPerson.getArea());
        }
        if (personasSucursal.getEntidad() == null) {
            personasSucursal.setEntidad(existingPerson.getEntidad());
        }
        if (personasSucursal.getIdExterno() == null) {
            personasSucursal.setIdExterno(existingPerson.getIdExterno());
        }
        if (personasSucursal.getEstado() == null) {
            personasSucursal.setEstado(existingPerson.getEstado());//TODO ADD STATUS IN EXCEL FILE
        }
        return personasSucursalFacade.update(personasSucursal);
    }

    /**
     * This method check not null to create person and persona sucursal entities
     * to be more efficient, only use it in create from file
     *
     * @param t
     * @return
     */
    private boolean checkNotNullFieldsToCreate(PersonasSucursal t) {
        return t.getArea() == null || t.getEntidad() == null || t.getSucursales() == null || t.getPersonas().getNombre1() == null || t.getPersonas().getApellido1() == null || t.getPersonas().getTipoDocumento() == null || t.getPersonas().getNumeroDocumento() == null;
    }

    //<editor-fold desc="castFileToEntity" defaultstate="collapsed">
    private ArrayList<PersonasSucursal> castFileToEntity(String[][] values) {
        ArrayList<PersonasSucursal> personsToSave = new ArrayList<>();

        //loading areas lists
        List<AreasEmpresa> areas;
        if (values[1][0] == null) {//Branch office
            return personsToSave;
        }
        areas = areasControl.getAreasByBranchOffice(Integer.valueOf(values[1][0]));
        for (int x = 1; x < values.length; x++) {//x=1 is where data begin, x=0 fields name are defined
            PersonasSucursal entity = new PersonasSucursal();
            //<editor-fold desc="master data person" defaultstate="collapsed">
            Personas person = new Personas();

            if (values[x][0] != null) {
                entity.setSucursales(new Sucursales(Integer.valueOf(values[x][0])));
            }

            if (values[x][2] != null) {
                switch (values[x][2]) {
                    case "Cedula de ciudadania"://TODO CREATE CONSTANST HERE
                        person.setTipoDocumento(new TiposDocumento(1));//TODO CREATE CONSTANST HERE
                        break;
                    case "Cedula de extrangeria"://TODO CREATE CONSTANST HERE
                        person.setTipoDocumento(new TiposDocumento(13));//TODO CREATE CONSTANST HERE
                        break;
                }
            }

            person.setNumeroDocumento(values[x][3]);
            person.setNombre1(values[x][4]);
            person.setNombre2(values[x][5]);
            person.setApellido1(values[x][6]);
            person.setApellido2(values[x][7]);

            if (values[x][9] != null) {
                switch (values[x][9]) {
                    case "M":
                        person.setSexo(true);
                        break;
                    case "F":
                        person.setSexo(false);
                        break;
                }
            }

            person.setRh(values[x][10]);

            if (values[x][11] != null) {
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date;
                try {
                    date = format.parse(values[x][11]);
                    person.setFechaNacimiento(date);
                } catch (ParseException ex) {
                    System.out.println("Seracis cliente exception PersonaCliControl: castFileToModel fecha de nacimiento");
                }
            }

            person.setDireccion(values[x][12]);
            person.setTelefono(values[x][13]);
            person.setCelular(values[x][14]);
            person.setMail(values[x][15]);
            person.setPersonaContacto(values[x][16]);
            person.setTelPersonaContacto(values[x][17]);
            if (values[x][18] != null) {
                switch (values[x][18]) {
                    case "COLOMBIA"://TODO CREATE CONSTANT PROPERTY HERE
                        person.setPais(new Paises(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            if (values[x][19] != null) {
                switch (values[x][19]) {
                    case "BOGOTA"://TODO CREATE CONSTANT PROPERTY HERE
                        person.setDepartamento(new Departamentos(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            if (values[x][20] != null) {
                switch (values[x][20]) {
                    case "BOGOTA"://TODO CREATE CONSTANT PROPERTY HERE
                        person.setMunicipio(new Municipios(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            if (values[x][21] != null) {
                switch (values[x][21]) {
                    case "EPS CARGA"://TODO CREATE CONSTANT PROPERTY HERE
                        person.setEps(new Eps(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            if (values[x][22] != null) {
                switch (values[x][22]) {
                    case "ARL CARGA"://TODO CREATE CONSTANT PROPERTY HERE
                        person.setArl(new Arl(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }

            if (values[x][23] != null) {
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date;
                try {
                    date = format.parse(values[x][23]);
                    person.setFechaVigenciaSS(date);
                } catch (ParseException ex) {
                    System.out.println("Seracis cliente exception PersonaCliControl: castFileToModel fecha de vigencia seguridad social");
                }
            }
            entity.setPersonas(person);
            //</editor-fold>

            if (values[x][1] != null) {
                switch (values[x][1]) {
                    case "TRABAJADOR"://TODO CREATE CONSTANT PROPERTY HERE
                        entity.setEntidad(new Entidades(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            if (values[x][8] != null && areas != null) {//AREA
                for (AreasEmpresa area : areas) {
                    if (values[x][8].equals(area.getDescripcion())) {
                        entity.setArea(area);
                    }
                }
            }
            entity.setIdExterno(values[x][24]);

            personsToSave.add(entity);
        }
        return personsToSave;
    }
    //</editor-fold>

    private boolean verifyStructure(String[][] values) {
        try {
            if ("Sucursal".equals(values[0][0]) && "Tipo Persona".equals(values[0][1]) && "Tipo Documento".equals(values[0][2]) && "Numero de documento".equals(values[0][3]) && "Primer Nombre".equals(values[0][4]) && "Segundo Nombre".equals(values[0][5]) && "Primer Apellido".equals(values[0][6]) && "Segundo Apellido".equals(values[0][7])
                    && "Area".equals(values[0][8]) && "Sexo".equals(values[0][9]) && "RH".equals(values[0][10]) && "Fecha de nacimiento".equals(values[0][11]) && "Direccion".equals(values[0][12]) && "Telefono Fijo".equals(values[0][13]) && "Celular".equals(values[0][14]) && "Email".equals(values[0][15])
                    && "Nombre contacto Emergencia".equals(values[0][16]) && "Telefono Contacto Emergencia".equals(values[0][17]) && "Pais".equals(values[0][18]) && "Departamento".equals(values[0][19]) && "Municipio".equals(values[0][20]) && "EPS".equals(values[0][21]) && "ARL".equals(values[0][22]) && "Fecha Vigencia Seguridad Social".equals(values[0][23]) && "ID integracion".equals(values[0][24])) {
                return true;
            }
        } catch (Exception e) {
            //Nothing to do here
        }
        return false;
    }
}
