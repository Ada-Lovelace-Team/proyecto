package com.UdeA.ciclo3.repo;

import com.UdeA.ciclo3.Modelos.Empleado;
import com.UdeA.ciclo3.Modelos.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository //Anotacion que indica que esta clase es un repositorio
public interface EmpresaRepository extends JpaRepository <Empresa, Integer>{

}
