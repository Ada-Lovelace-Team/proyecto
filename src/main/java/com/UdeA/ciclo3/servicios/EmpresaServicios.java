package com.UdeA.ciclo3.servicios;

import com.UdeA.ciclo3.Modelos.Empleado;
import com.UdeA.ciclo3.Modelos.Empresa;
import com.UdeA.ciclo3.repo.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServicios {
    @Autowired
    EmpresaRepository empresaRepository;//creacion de un objeto tipo repositorio
    //metodo que me dice todas las empresas que tengo por medio de jpaRepository
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        empresaRepository.findAll().forEach(empresa->empresaList.add(empresa));
        return empresaList;
    }


    public Empresa getEmpresaById(Integer id){
        return empresaRepository.findById(id).get();
    }
    //Metodo para guardar o actializar objetos de tipo empresa
    public Empresa saveOrUpdateEmpresa (Empresa empresa){
        return empresaRepository.save(empresa);

    }
    //Metodo que me permita eliminar una empresa
    public boolean deleteEmpresa (Integer id) {
        empresaRepository.deleteById(id);

        if(empresaRepository.findById(id)==null){
            return true;
        }
        return false;
    }


}
