package com.UdeA.ciclo3.controller;

import com.UdeA.ciclo3.Modelos.Empleado;
import com.UdeA.ciclo3.Modelos.Empresa;
import com.UdeA.ciclo3.Modelos.MovimientoDinero;
import com.UdeA.ciclo3.repo.MovimientosRepository;
import com.UdeA.ciclo3.servicios.EmpleadoServicios;
import com.UdeA.ciclo3.servicios.EmpresaServicios;
import com.UdeA.ciclo3.servicios.MovimientosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class controller {
    @Autowired
    EmpresaServicios empresaServicios;
    @Autowired
    EmpleadoServicios empleadoServicios;
    @Autowired
    MovimientosServicios movimientosServicios;

    @Autowired
    MovimientosRepository movimientosRepository;
    ///ETodo lo relacionado con la Empresa//
    @GetMapping({"/","/verEmpresas"})
    public String viewEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> ListaEmpresas=empresaServicios.getAllEmpresas();
        model.addAttribute("empList",ListaEmpresas);
        model.addAttribute("mensaje",mensaje);
        return "verEmpresas";
    }
    @GetMapping ("/AgregarEmpresa")
    public String nuevaEmpresa(Model model, @ModelAttribute("mensaje")String mensaje){
        Empresa emp= new Empresa();
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "agregarEmpresa";
    }
    @PostMapping ("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if (empresaServicios.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpresa";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje")String mensaje){
        Empresa emp=empresaServicios.getEmpresaById(id);
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }
    @PostMapping("/ActualizarEmpresa")
    public String updateEmpresa (@ModelAttribute("emp") Empresa emp,RedirectAttributes redirectAttributes){
        if(empresaServicios.saveOrUpdateEmpresa(emp)){
            redirectAttributes.addFlashAttribute("mensaje","updateOk");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect: /EditarEmpresa";
    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empresaServicios.deleteEmpresa(id)==true){;

            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","deleteError");
        return "redirect:/VerEmpresas";
    }
    //ETODO LO RELACIONADO CON EMPLEADOS
    @GetMapping ("/VerEmpleados")
    public String viewEmpleados(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empleado> listaEmpleados=empleadoServicios.getAllEmpleado();
        model.addAttribute("emplelist",listaEmpleados);
        model.addAttribute("mensaje",mensaje);
        return "verEmpleados"; //Llamamos al HTML
    }

    @GetMapping("/AgregarEmpleado")
    public String nuevoEmpleado(Model model, @ModelAttribute("mensaje") String mensaje){
        Empleado empleado= new Empleado();
        model.addAttribute("empleado",empleado);
        model.addAttribute("mensaje",mensaje);
        List<Empresa> listaEmpresas= empresaServicios.getAllEmpresas();
        model.addAttribute("emprelist",listaEmpresas);
        return "agregarEmpleado"; //Llamar HTML
    }

    @PostMapping("/GuardarEmpleado")
    public String guardarEmpleado(Empleado empleado, RedirectAttributes redirectAttributes){
        if(empleadoServicios.saveOrUpdateEmpleado(empleado)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpleado";
    }

    @GetMapping("/EditarEmpleado/{id}")
    public String editarEmpleado(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empleado empleado=empleadoServicios.getEmpleadoById(id).get();
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("empleado",empleado);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas= empresaServicios.getAllEmpresas();
        model.addAttribute("emprelist",listaEmpresas);
        return "editarEmpleado";
    }

    @PostMapping("/ActualizarEmpleado")
    public String updateEmpleado(@ModelAttribute("empleado") Empleado empleado, RedirectAttributes redirectAttributes){
        Integer id=empleado.getId(); //Sacamos el id del objeto empl
        if(empleadoServicios.saveOrUpdateEmpleado(empleado)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpleado/"+empleado.getId();

    }

    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empleadoServicios.deleteEmpleado(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerEmpleados";
    }

    @GetMapping("/Empresa/{id}/Empleados") //Filtrar los empleados por empresa
    public String verEmpleadosPorEmpresa(@PathVariable("id") Integer id, Model model){
        List<Empleado> listaEmpleados = empleadoServicios.obtenerPorEmpresa(id);
        model.addAttribute("emplelist",listaEmpleados);
        return "verEmpleados"; //Llamamos al html con el emplelist de los empleados filtrados
    }



}


