package Ejercicio5;

public class Main {

    public static void main(String[] args) {

        PersonaService service = new PersonaService();

        // Filtrar personas mayores de 30 años
        System.out.println("Personas mayores de 30 años:");
        service.filtrarPersonasMayoresDe30();

        // Mostrar nombre y apellido de la primera persona
        System.out.println("\nNombre y apellido de la primera persona:");
        service.mostrarNombreYApellidoDePrimeraPersona();

        // Agrupar personas por signo
        System.out.println("\nAgrupación por signo del zodiaco:");
        service.agruparPersonasPorSigno();

        // Obtener personas por edad
        System.out.println("\nPersonas con 28 años:");
        service.obtenerPersonasPorEdad(28).subscribe();

        // Obtener personas por signo
        System.out.println("\nPersonas con signo Virgo:");
        service.obtenerPersonasPorSigno("Virgo").subscribe();

        // Obtener persona por teléfono
        System.out.println("\nPersona con teléfono 123456789:");
        service.obtenerPersonaPorTelefono("123456789").subscribe();

        // Agregar nueva persona
        System.out.println("\nAgregando nueva persona:");
        Persona nuevaPersona = new Persona("Luis", "Ortiz", "223344556", 26, "Libra");
        service.agregarPersona(nuevaPersona).subscribe();

        // Eliminar persona
        System.out.println("\nEliminando persona:");
        service.eliminarPersona(nuevaPersona).subscribe();

        // Pruebas de manejo de errores
        System.out.println("\n----- Pruebas de manejo de errores:-----------");

        // Provocar un error en obtenerPersonasPorEdad
        System.out.println("\nIntentando obtener personas con edad -1 (provocará un error):");
        service.obtenerPersonasPorEdad(-1).subscribe();

        // Provocar un error en obtenerPersonasPorSigno
        System.out.println("\nIntentando obtener personas con signo 'Error' (provocará un error):");
        service.obtenerPersonasPorSigno("Error").subscribe();

        // Provocar un error en obtenerPersonaPorTelefono
        System.out.println("\nIntentando obtener persona con teléfono '000000000' (provocará un error):");
        service.obtenerPersonaPorTelefono("000000000").subscribe();
    }

    }



