package com.UdeA.ciclo3.controller;

import com.UdeA.ciclo3.Modelos.Empleado;
import com.UdeA.ciclo3.Modelos.Empresa;
import com.UdeA.ciclo3.Modelos.MovimientoDinero;
import com.UdeA.ciclo3.servicios.EmpleadoServicios;
import com.UdeA.ciclo3.servicios.EmpresaServicios;
import com.UdeA.ciclo3.servicios.MovimientosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class controller {
    @Autowired
    EmpresaServicios empresaServicios;
    @Autowired
    EmpleadoServicios empleadoServicios;
    @Autowired
    MovimientosServicios movimientosServicios;

    @GetMapping("/enterprises")
    public List<Empresa> verEmpresas() {
        return empresaServicios.getAllEmpresas();

    }

    @PostMapping("/enterprises")
    public Empresa guardarEmpresa(@RequestBody Empresa emp) {
        return this.empresaServicios.saveOrUpdateEmpresa(emp);


    }

    @GetMapping(path = "enterprises/{id}")
    public Empresa empresaPorID(@PathVariable("id") Integer id) {
        return this.empresaServicios.getEmpresaById(id);
    }

    @PatchMapping("/enterprises/{id}")
    public Empresa actualizarEmpresa(@PathVariable("id") Integer id, @RequestBody Empresa empresa) {
        Empresa emp = empresaServicios.getEmpresaById(id);
        emp.setNombre(empresa.getNombre());
        emp.setDirreccion(empresa.getDirreccion());
        emp.setTelefono(empresa.getTelefono());
        emp.setNIT(empresa.getNIT());
        return empresaServicios.saveOrUpdateEmpresa(emp);
    }

    @DeleteMapping(path = "enterprises/{id}") //Eliminar registro de la bd
    public String DeleteEmpresa(@PathVariable("id") Integer id) {
        boolean respuesta = this.empresaServicios.deleteEmpresa(id);
        if (respuesta) {  //Si respuesta es true?
            return "Se elimino la empresa con id" + id;
        } else {
            return "No se pudo eliminar la empresa con id" + id;
        }
    }

    //Empleados
    @GetMapping("/users")
    public List<Empleado> verEmpleados(){
        return empleadoServicios.getAllEmpleado();
    }

    @PostMapping("/users") //Guardar un empleado nuevo
    public Optional<Empleado> guardarEmpleado(@RequestBody Empleado empl){
        return Optional.ofNullable(this.empleadoServicios.saverOurdateEmpleado(empl));
    }
    @GetMapping(path = "users/{id}")//Consultar empleado por ID
    public Optional<Empleado> empleadoPorID(@PathVariable("id") Integer id){
        return this.empleadoServicios.getEmpleadoById(id);
    }
    @GetMapping("/enterprises/{id}/users")
    public ArrayList<Empleado>EmpleadoPorEmpresa(@PathVariable("id")Integer id){
        return this.empleadoServicios.obtenerPorEmpresa(id);
    }
    @PatchMapping("/users/{id}")
    public Empleado actualizarEmpleado(@PathVariable("id") Integer id, @RequestBody Empleado empleado){
        Empleado empl=empleadoServicios.getEmpleadoById(id).get();
        empl.setNombre(empleado.getNombre());
        empl.setCorreo(empleado.getCorreo());
        empl.setEmpresa(empleado.getEmpresa());
        empl.setCargo(empleado.getCargo());
        return empleadoServicios.saverOurdateEmpleado(empl);
    }
    @DeleteMapping("/users/{id}") //Metodo para eliminar empleados por id
    public String DeleteEmpleado(@PathVariable("id") Integer id){
        boolean respuesta=empleadoServicios.deleteEmpleado(id); //eliminamos usando el servicio de nuestro service
        if (respuesta){ //si la respuesta booleana es true, si se eliminò
            return "Se pudo eliminar correctamente el empleado con id "+id;
        }//Si la respuesta booleana es false, no se eliminó
        return "No se puedo eliminar correctamente el empleado con id "+id;
    }
    //MOVEMENTS

    @GetMapping("/movements") //Consultar todos los movimientos
    public List<MovimientoDinero> verMovimientos(){
        return movimientosServicios.getAllMovimientos();
    }

    @PostMapping("/movements")
    public MovimientoDinero guardarMovimiento(@RequestBody MovimientoDinero movimiento){
        return movimientosServicios.saveOrUpdateMovimiento(movimiento);
    }

    @GetMapping("/movements/{id}") //Consultar movimiento por id
    public MovimientoDinero movimientoPorId(@PathVariable("id") Integer id){
        return movimientosServicios.getMovimientoById(id);
    }

    @PatchMapping("enterprises/{id}/movements")//Editar o actualizar un movimiento
    public MovimientoDinero actualizarMovimiento(@PathVariable("id") Integer id, @RequestBody MovimientoDinero movimiento){
        MovimientoDinero mov=movimientosServicios.getMovimientoById(id);
        mov.setConcepto(movimiento.getConcepto());
        mov.setMonto(movimiento.getMonto());
        mov.setUsuario(movimiento.getUsuario());
        return movimientosServicios.saveOrUpdateMovimiento(mov);
    }
    @DeleteMapping("enterprises/{id}/movements")
    public String deleteMovimiento(@PathVariable("id") Integer id){
        boolean respuesta= movimientosServicios.deleteMovimiento(id);
        if (respuesta){
            return "Se elimino correctamente el movimiento con id " +id;
        }
        return "No se pudo eliminar el movimiento con id "+id;
    }

    @GetMapping("/users/{id}/movements") //Consultar movimientos por id del empleado
    public ArrayList<MovimientoDinero> movimientosPorEmpleado(@PathVariable("id") Integer id){
        return movimientosServicios.obtenerPorEmpleado(id);
    }

    @GetMapping("/enterprises/{id}/movements") //Consultar movimientos que pertenecen a una empresa por el id de la empresa
    public ArrayList<MovimientoDinero> movimientosPorEmpresa(@PathVariable("id") Integer id){
        return movimientosServicios.obtenerPorEmpresa(id);
    }

}


