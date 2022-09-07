package com.UdeA.ciclo3.controller;

import com.UdeA.ciclo3.Modelos.Empresa;
import com.UdeA.ciclo3.servicios.EmpresaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;


@Controller
public class controller {
    @Autowired
    EmpresaServicios empresaServicios;

    @GetMapping({"/","/verEmpresas"})
    public String viewEmpresas(Model model){
        List<Empresa> ListaEmpresas=empresaServicios.getAllEmpresas();
        model.addAttribute("empList",ListaEmpresas);
        return "verEmpresas";
    }
    @GetMapping ("/AgregarEmpresa")
    public String nuevaEmpresa(Model model){
        Empresa emp= new Empresa();
        model.addAttribute("emp",emp);
        return "agregarEmpresa";
    }
    @PostMapping ("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if (empresaServicios.saveOrUpdateEmpresa(emp)==true){
            return "redirect:/verEmpresas";
        }
        return "redirect:/AgregarEmpresa";
    }
    @GetMapping("/EditarEmpresa")
    public String editarEmpresa(Model model, @PathVariable Integer id){
         Empresa emp=empresaServicios.getEmpresaById(id);
         model.addAttribute("emp",emp);
         return "editarEmpresa";
    }
}

