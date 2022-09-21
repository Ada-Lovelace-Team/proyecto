package com.UdeA.ciclo3.servicios;


import com.UdeA.ciclo3.Modelos.Empresa;
import com.UdeA.ciclo3.repo.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class EmpresaServicios {
    @Autowired
    EmpresaRepository empresaRepository;//creacion de un objeto tipo repositorio
    //metodo que me dice todas las empresas que tengo por medio de jpaRepository
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa));  //Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista creada
        return empresaList;
    }


    public Empresa getEmpresaById(Integer id){
        return empresaRepository.findById(id).get();
    }
    //Metodo para guardar o actializar objetos de tipo empresa
   // public Empresa saveOrUpdateEmpresa (Empresa empresa){
        //return empresaRepository.save(empresa);

    //Metodo para guardar o actializar objetos de tipo empresa
    public boolean saveOrUpdateEmpresa (Empresa empresa){
        Empresa emp=empresaRepository.save(empresa);
        if (empresaRepository.findById(emp.getId())!=null){
            return true;
        }
        return false;
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
