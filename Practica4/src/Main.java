import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Chupate2 chupate2 = new Chupate2();
        chupate2.parametrosIniciales(scanner);
        chupate2.turnos(scanner);
        scanner.close();
    }
}
