public class Main {
    //funcion pura.
    public static String combineNames(String firstName, String lastName, Concatenator concatenator) {
        return concatenator.concatenate(firstName, lastName);
    }

    public static void main(String[] args) {


        Concatenator concatenator = (firstName, lastName) -> firstName + " " + lastName;

        // Probar la función de orden superior
        String firstName = "Jhon";
        String lastName = "Vargas";
        String fullName = combineNames(firstName, lastName, concatenator);

        System.out.println("Nombre completo: " + fullName);
    }
}