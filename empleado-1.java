
 public class Empleado {
   
     private String nombre;
     private String correo_electrónico;
     private String empresa;
     private char administrador;  //S ó N
   
 //Constructor por defecto
    public Empleado(String nombre, String correo_electrónico, String empresa, char administrador ) {
           this.nombre = nombre;
           this.correo_electrónico = correo_electrónico;
           this.empresa = empresa;
           this.administrador = administrador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo_electrónico() {
        return correo_electrónico;
    }

    public String getEmpresa() {
        return empresa;
    }

    public char getAdministrador() {
        return administrador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo_electrónico(String correo_electrónico) {
        this.correo_electrónico = correo_electrónico;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setAdministrador(char administrador) {
        this.administrador = administrador;
    }
    
    //metodo de consulta
   
     @Override
   public String toString(){
       return "El empleado se llama"+ nombre;     
    }
}
