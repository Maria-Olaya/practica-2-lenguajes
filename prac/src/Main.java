import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.*;


class Main {




    static List<Estudiante> estudiantes;


    public static void main(String[] args) throws IOException {
        cargarArchivo();//cargo el archivo
        mostrarEmpleadosPorCarr();// muestro los empleados por carrera
        cantEmpleadosPorCarr();//muestro la cantidad de empleados por departamento
        cantEmpleadosPorGen();//muestro la cantidad de empleados por genero
        imprimirCantidadMujeresHombresPorCarrera();//muestro la cantidad de mujeres y hombres por carrera
        EmpleadoGanaMasPorCarr();//muestro el empleado que gana mas por departamento
        EmpleadoMayPun();//muestro el empleado que gana mas
        promPunCarr();//muestro el promedio de puntaje por carrera
        //EstudianteMasPorCarr();//muestro si todos los estudiantes tienen el puntaje maximo
    }


    static void cargarArchivo() throws IOException {
        Pattern pattern = Pattern.compile(",");
        String filename = "student-scores.csv";

        try (Stream<String> lines = Files.lines(Path.of(filename))) {
            estudiantes = lines.skip(1).map(line -> { //esta es la lambda de la interfaz Stream para el manejo de arhicos es una de bloque
                String[] arr = pattern.split(line);// aqui patterb es el separador que se establesio en lineas anteriores, split es el metodo que divide el string cada que se encuentra con el separador
                return new Estudiante(arr[0], arr[1], arr[2], arr[4], Double.parseDouble(arr[10]), arr[9]);// crea el objeto empleado con los datos del archivo
            }).collect(Collectors.toList()); //se utiliza en combinación con las operaciones de Streams para recopilar y transformar los elementos de un flujo (Stream) en una colección o en otro tipo de resultado. Es una operación de terminal, lo que significa que es la última operación que se llama en un flujo y que produce un resultado final.
            estudiantes.forEach(System.out::println);//en Java se utiliza para aplicar una operación a cada elemento de un Stream. Es una operación de terminal, lo que significa que es la última operación que se llama en un flujo y generalmente se utiliza para realizar acciones en lugar de transformar o recopilar datos.(com un for general)
        }
    }

    static void mostrarEmpleadosPorCarr() {
        System.out.printf("%nEmpleados por Carrera:%n");
        Map<String, List<Estudiante>> agrupadoPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Estudiante::getCarrera));//Aquí se utiliza la operación collect para agrupar los empleados por su departamento.
//Collectors.groupingBy(Empleado::getDepartamento) se utiliza para agrupar los empleados en un mapa donde las claves son los departamentos y los valores son listas de empleados pertenecientes a ese departamento.
        agrupadoPorCarrera.forEach(
                //agrupadoPorDepartamento, se itera mediante forEach. En este caso, el mapa tiene pares de clave-valor donde la clave es el nombre del departamento y el valor es una lista de empleados en ese departamento.

                (carrera, estudiantesEnCarrera) ->
                        //Se utiliza una expresión lambda para procesar cada par clave-valor en el mapa. departamento representa el nombre del departamento y empleadosEnDepartamento es la lista de empleados en ese departamento.
                {

                    System.out.println(carrera);
                    estudiantesEnCarrera.forEach(
                            //empleadosEnDepartamento.forEach(...): Se utiliza forEach nuevamente para iterar a través de la lista de empleados en el departamento actual.

                            estudiante -> System.out.printf(" %s%n", estudiante));

                }
        );
    }

    static void cantEmpleadosPorGen() {
        // cuenta el número de empleados en cada departamento
        System.out.printf("%nConteo de estudiantes por genero:%n");
        Map<String, Long> conteoEmpleadosPorGenero =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Estudiante::getGenero,
                                TreeMap::new, Collectors.counting()));
        //Empleado::getDepartamento se utiliza como función de agrupación. Esto significa que los empleados se agruparán por el departamento al que pertenecen.
        //TreeMap::new especifica que se debe utilizar un TreeMap para mantener los departamentos ordenados alfabéticamente en el mapa resultante.
        //Collectors.counting() cuenta el número de empleados en cada departamento.

        conteoEmpleadosPorGenero.forEach(
                (Genero, conteo) -> System.out.printf(
                        "%s tiene %d estudiante(s)%n", Genero, conteo));
    }

    static Map<String, Map<String, Long>> obtenerCantidadMujeresHombresPorCarrera() {
        // Crea un TreeMap para almacenar la cantidad de mujeres y hombres por carrera
        Map<String, Map<String, Long>> cantidadMujeresHombresPorCarrera = new TreeMap<>();

        // Agrupa a los estudiantes por carrera usando TreeMap
        Map<String, List<Estudiante>> agrupadoPorCarrera = estudiantes.stream()
                .collect(Collectors.groupingBy(Estudiante::getCarrera, TreeMap::new, Collectors.toList()));

        // Para cada carrera, cuenta la cantidad de mujeres y hombres y guárdalo en el mapa
        agrupadoPorCarrera.forEach((carrera, estudiantesEnCarrera) -> {
            Map<String, Long> cantidadMujeresHombres = estudiantesEnCarrera.stream()
                    .collect(Collectors.groupingBy(
                            Estudiante::getGenero,
                            TreeMap::new, Collectors.counting()
                    ));
            cantidadMujeresHombresPorCarrera.put(carrera, cantidadMujeresHombres);
        });

        return cantidadMujeresHombresPorCarrera;
    }

    static void imprimirCantidadMujeresHombresPorCarrera() {
        Map<String, Map<String, Long>> cantidadMujeresHombresPorCarrera = obtenerCantidadMujeresHombresPorCarrera();

        cantidadMujeresHombresPorCarrera.forEach((carrera, cantidadMujeresHombres) -> {
            System.out.println("Carrera: " + carrera+ "\n");
            System.out.println("Mujeres: " + cantidadMujeresHombres.get("female"));
            System.out.println("Hombres: " + cantidadMujeresHombres.get("male")+"\n");
        });
    }

    static void cantEmpleadosPorCarr() {
        // cuenta el número de empleados en cada departamento
        System.out.printf("%nConteo de estudiantes por carrera:%n");
        Map<String, Long> conteoEmpleadosPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Estudiante::getCarrera,
                                TreeMap::new, Collectors.counting()));
        //Empleado::getDepartamento se utiliza como función de agrupación. Esto significa que los empleados se agruparán por el departamento al que pertenecen.
        //TreeMap::new especifica que se debe utilizar un TreeMap para mantener los departamentos ordenados alfabéticamente en el mapa resultante.
        //Collectors.counting() cuenta el número de empleados en cada departamento.

        conteoEmpleadosPorCarrera.forEach(
                (Carrera, conteo) -> System.out.printf(
                        "%s tiene %d estudiante(s)%n", Carrera, conteo));
    }

    static void EmpleadoGanaMasPorCarr() {
    Function<Estudiante, Double> porPuntaje = Estudiante::getPuntaje;
    Comparator<Estudiante> PuntajeDescendete = Comparator.comparing(porPuntaje);
    System.out.printf("%nEstudiantes con el puntaje más alto por Carrera: %n");
    Map<String, List<Estudiante>> agrupadoPorCarrera =
            estudiantes.stream().collect(Collectors.groupingBy(Estudiante::getCarrera));

    agrupadoPorCarrera.forEach(
        (Carrera, estudiantesEnCarrera) -> {
            System.out.println(Carrera + ":");
            Double maxPuntaje = estudiantesEnCarrera.stream().mapToDouble(Estudiante::getPuntaje).max().orElse(0.0);

            estudiantesEnCarrera.stream()
                .filter(estudiante -> estudiante.getPuntaje() == maxPuntaje)
                .forEach(estudiante -> {
                    System.out.println(estudiante.getPrimerNombre() + " " + estudiante.getApellidoPaterno() +
                            " ///Cuanto puntaje ==> puntaje: " + estudiante.getPuntaje());
                });
        }
    );
}


    static void EmpleadoMayPun() {
        Function<Estudiante, Double> porPuntaje = Estudiante::getPuntaje;
        Comparator<Estudiante> PuntajeCarrera = Comparator.comparing(porPuntaje);
        Double maxPuntaje = estudiantes.stream().mapToDouble(Estudiante::getPuntaje).max().orElse(0.0);

        System.out.printf("%nEstudiantes con el puntaje más alto (Puntaje máximo: %.2f)%n", maxPuntaje);

        estudiantes.stream()
                .filter(estudiante -> estudiante.getPuntaje() == maxPuntaje)
                .forEach(estudiante -> {
                    System.out.println(estudiante.getPrimerNombre() + " " + estudiante.getApellidoPaterno());
                });
    }

    static void promPunCarr(){
        Map<String, List<Estudiante>> agrupadoPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Estudiante::getCarrera));
        System.out.println("\nPromedio de puntaje de los estudiantes por Carrera:");
        agrupadoPorCarrera.forEach((carrera, estudiantesporCarr)-> {
            System.out.print(carrera+": ");
            System.out.println(estudiantesporCarr
                    .stream()
                    .mapToDouble(Estudiante::getPuntaje)
                    .average()
                    .getAsDouble());
        });


    }

}
