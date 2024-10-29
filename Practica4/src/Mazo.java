import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Mazo {
    public ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
        inicializarMazo();
        Collections.shuffle(cartas);
    }

    private void inicializarMazo() {
        String[] palos = {"Espadas", "Copas", "Oros", "Bastos"};
        String[] valores = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        Arrays.stream(palos)
                .forEach(palo -> Arrays.stream(valores)
                        .forEach(valor -> cartas.add(new Carta(valor, palo))));
    }


    public Carta tomarCarta() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(0);
    }
}