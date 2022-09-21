package com.UdeA.ciclo3.controller;


import com.UdeA.ciclo3.Modelos.Empresa;
import com.UdeA.ciclo3.servicios.EmpresaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller

public class controller {
    @Autowired
    EmpresaServicios empresaServicios;

    @GetMapping({"/","/verEmpresas"})
    public String viewEmpresas(Model model){
        List<Empresa> ListaEmpresas=empresaServicios.getAllEmpresas();
        model.addAttribute("empList",ListaEmpresas);
        return "VerEmpresas";
    }

}
