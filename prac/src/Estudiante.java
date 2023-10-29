 class Estudiante {
    private String id;
    private String primerNombre;
    private String apellidoPaterno;
    private String genero;
    private double puntaje;
    private String carrera;

    public Estudiante(String id,String primerNombre, String apellidoPaterno,String genero, double puntaje, String carrera) {
        this.id = id;
        this.primerNombre = primerNombre;
        this.apellidoPaterno = apellidoPaterno;
        this.genero = genero;
        this.puntaje = puntaje;
        this.carrera = carrera;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getGenero() {
         return genero;
     }
    public void setGenero(String genero) {
         this.genero = genero;
     }
    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }
    public String getId() {
         return id;
     }
     public void setId(String id) {
         this.id = id;
     }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Aspirante{" + "id=" + id +
                "primerNombre='" + primerNombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' + ", genero='" + genero + '\'' +
                ", Puntaje=" + puntaje +
                ", carrera='" + carrera + '\'' +
                '}';
    }
}