
 public class Empleado {
   
    private String nombre;
    private String correo_electrónico;
    private String empresa;
    private String cargo;
  
//Constructor por defecto
   public Empleado(String nombre, String correo_electrónico, String empresa,String cargo ) {
          this.nombre = nombre;
          this.correo_electrónico = correo_electrónico;
          this.empresa = empresa;
          this.cargo = cargo;
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

   public String getCargo() {
       return cargo;
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

   public void setCargo(String cargo) {
       this.cargo = cargo;
   }    
   public void mostrarEmpleado(){
    System.out.print("\nNombre:" +getNombre()+"\nCorreo_electrónico: " +getCorreo_electrónico()+"\nEmpresa: "+getEmpresa()+"\nCargo: " +getCargo() ); 
   }          
    //metodo de consulta

    @Override
     public String toString(){
         return "El empleado se llama"+ nombre;     
        
      
     }
      
     
      }