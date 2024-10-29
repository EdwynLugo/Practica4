import java.util.ArrayList;

class Jugador {
    private String nombre;
    private ArrayList<Carta> mano;
    private Carta cartaSeleccionada;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Carta> getMano() {
        return new ArrayList<>(mano);
    }

    public void tomarCarta(Carta carta) {
        if (carta != null) {
            mano.add(carta);
        }
    }

    public void setCartaSeleccionada(Carta carta) {
        this.cartaSeleccionada = carta;
    }

    public void jugarCarta() {
        if (cartaSeleccionada != null) {
            boolean cartaJugada = mano.removeIf(carta ->
                    carta.getValor().equals(cartaSeleccionada.getValor()) &&
                            carta.getPalo().equals(cartaSeleccionada.getPalo())
            );
            cartaSeleccionada = null;
        }
    }
}
