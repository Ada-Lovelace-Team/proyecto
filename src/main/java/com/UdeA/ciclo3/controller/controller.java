package com.UdeA.ciclo3.controller;


import com.UdeA.ciclo3.Modelos.Empleado;
import com.UdeA.ciclo3.Modelos.Empresa;
import com.UdeA.ciclo3.Modelos.MovimientoDinero;
import com.UdeA.ciclo3.repo.MovimientosRepository;
import com.UdeA.ciclo3.servicios.EmpleadoServicios;
import com.UdeA.ciclo3.servicios.EmpresaServicios;
import com.UdeA.ciclo3.servicios.MovimientosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    @GetMapping({"/","/VerEmpresas"})
    public String viewEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> listaEmpresas=empresaServicios.getAllEmpresas();
        model.addAttribute("emplist",listaEmpresas);
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
    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaServicios.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpresa";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje")String mensaje) {
        Empresa emp = empresaServicios.getEmpresaById(id);
        model.addAttribute("emp", emp);
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
        return "redirect: /EditarEmpresa/"+emp.getId();
    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (empresaServicios.deleteEmpresa(id) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerEmpresas";
    }
    //METODO LO RELACIONADO CON EMPLEADOS
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
        String passEncriptada=passwordEncoder().encode(empleado.getPassword());
        empleado.setPassword(passEncriptada);
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
        Integer id=empleado.getId(); //Sacamos el id del objeto empleado
        String Oldpass=empleadoServicios.getEmpleadoById(id).get().getPassword(); //Con ese id consultamos la contraseña que ya esta en la base
        if(!empleado.getPassword().equals(Oldpass)){
            String passEncriptada=passwordEncoder().encode(empleado.getPassword());
            empleado.setPassword(passEncriptada);
        }
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

    //Movimientos
    @GetMapping ("/VerMovimientos")
    public String viewMovimientosviewMovimientos(@RequestParam(value="pagina", required=false, defaultValue = "1") int NumeroPagina,
                                                 @RequestParam(value="medida", required=false, defaultValue = "20") int medida,
                                                 Model model, @ModelAttribute("mensaje") String mensaje){
        Page<MovimientoDinero> paginaMovimientos= movimientosRepository.findAll(PageRequest.of(NumeroPagina,medida));
        model.addAttribute("movlist",paginaMovimientos.getContent());
        model.addAttribute("paginas",new int[paginaMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual", NumeroPagina);
        model.addAttribute("mensaje",mensaje);
        Long sumaMonto=movimientosServicios.obtenerSumaMontos();
        model.addAttribute("SumaMontos",sumaMonto);//Mandamos la suma de todos los montos a la plantilla
        return "verMovimientos"; //Llamamos al HTML
    }
    //Agrega nuevo movimiento
    @GetMapping("/AgregarMovimiento")
    public String nuevoMovimiento(Model model, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero movimiento= new MovimientoDinero();
        model.addAttribute("mov",movimiento);
        model.addAttribute("mensaje",mensaje);
        List<Empleado> listaEmpleados= empleadoServicios.getAllEmpleado();
        model.addAttribute("emplelist",listaEmpleados); //
        return "agregarMovimiento"; //Llamamos al HTML
    }
    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosServicios.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarMovimiento";
    }
    @GetMapping("/EditarMovimiento/{id}")
    public String editarMovimiento(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero mov=movimientosServicios.getMovimientoById(id);
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("mov",mov);
        model.addAttribute("mensaje", mensaje);
        List<Empleado> listaEmpleados= empleadoServicios.getAllEmpleado();
        model.addAttribute("emplelist",listaEmpleados);
        return "editarMovimiento";
    }

    @PostMapping("/ActualizarMovimiento")
    public String updateMovimiento(@ModelAttribute("mov") MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosServicios.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarMovimiento/"+mov.getId();

    }
    @GetMapping("/EliminarMovimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (movimientosServicios.deleteMovimiento(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerMovimientos";

    }
     // Filtro de movimientos por empleado
    @GetMapping("/Empleado/{id}/Transacciones")
    public String movimientosPorEmpleado(@PathVariable("id") Integer id, Model model){
        List<MovimientoDinero> movlist =movimientosServicios.obtenerPorEmpleado(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto=movimientosServicios.MontosPorEmpleado(id);
        model.addAttribute("SumaMontos",sumaMonto);// Enviamos la información a la plantilla del html
        return "verMovimientos"; //Llamamos al html verMovimientos
    }
    // Filtro de movimientos por empleado
    @GetMapping("/Empresa/{id}/Transacciones")
    public String movimientosPorEmpresa(@PathVariable("id")Integer id, Model model){
        List<MovimientoDinero> movlist=movimientosServicios.obtenerPorEmpresa(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto=movimientosServicios.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos",sumaMonto);// Enviamos la información a la plantilla del html
        return "verMovimientos"; //Llamamos al html verMovimientos
    }
        @Bean
          public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();

}
}





