import java.util.ArrayList;
import java.util.Scanner;

class Chupate2 {
    private ArrayList<Jugador> jugadores;
    private Mazo mazo;
    private Interfaz interfaz;
    private Carta cartaDescarte;
    private Carta cartaSeleccionada;
    private int turnoActual;
    private boolean tomoCarta;
    private boolean comodin;

    public Chupate2() {
        jugadores = new ArrayList<>();
        mazo = new Mazo();
        interfaz = new Interfaz(this);
        cartaDescarte = mazo.tomarCarta();
        turnoActual = 0;
    }

    public void parametrosIniciales(Scanner scanner) {
        int numeroJugadores = 0;

        while (numeroJugadores < 2 || numeroJugadores > 4) {
            System.out.print("Cuantos jugadores (2-4)? ");
            if (scanner.hasNextInt()) {
                numeroJugadores = scanner.nextInt();
                if (numeroJugadores < 2 || numeroJugadores > 4) {
                    System.out.println("Por favor, ingrese un numero valido de jugadores.");
                }
            } else {
                System.out.println("Entrada invalida. Debe ser un numero.");
                scanner.next();
            }
        }

        scanner.nextLine();

        for (int i = 1; i <= numeroJugadores; i++) {
            System.out.print("Ingrese el nombre del jugador " + i + ": ");
            String nombre = scanner.nextLine();
            Jugador jugador = new Jugador(nombre);
            for (int j = 0; j < 7; j++) {
                Carta carta = mazo.tomarCarta();
                if (carta != null) {
                    jugador.tomarCarta(carta);
                }
            }
            jugadores.add(jugador);
        }
    }

    public void turnos(Scanner scanner) {
        interfaz.setVisible(true);
        while (true) {
            Jugador jugador = jugadores.get(turnoActual);
            interfaz.setTurno("Turno del jugador '" + jugador.getNombre() + "': ");
            interfaz.actualizarInterfaz(cartaDescarte.getValor(), cartaDescarte.getPalo(), jugador.getMano());

            while (cartaSeleccionada == null && !tomoCarta) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (tomoCarta) {
                tomarCartaJugador();
                tomoCarta = false;
            } else {
                if (cartaSeleccionada.getValor().equals("12")) {
                    interfaz.setMensaje("El jugador '" + jugador.getNombre() + "' ha usado un comodin (12), por lo que puede jugar cualquier carta.");
                    cartaDescarte = cartaSeleccionada;
                    jugador.setCartaSeleccionada(cartaSeleccionada);
                    jugador.jugarCarta();
                    verificarGanador(jugador);
                    comodin = true;
                    cartaSeleccionada = null;
                    continue;
                } else {
                    boolean coincideValor = cartaSeleccionada.getValor().equals(cartaDescarte.getValor());
                    boolean coincidePalo = cartaSeleccionada.getPalo().equals(cartaDescarte.getPalo());

                    if (coincideValor || coincidePalo || comodin) {
                        cartaDescarte = cartaSeleccionada;
                        jugador.setCartaSeleccionada(cartaSeleccionada);
                        jugador.jugarCarta();

                        boolean repetirTurno = cartaEspecial(cartaSeleccionada);
                        verificarGanador(jugador);

                        if (!repetirTurno) {
                            turnoActual = (turnoActual + 1) % jugadores.size();
                        }
                    } else {
                        interfaz.setMensaje("La carta ingresada por el jugador '" + jugador.getNombre() + "' NO coincide");
                    }
                }
            }
            cartaSeleccionada = null;
            comodin = false;
        }
    }

    public void tomarCartaJugador() {
        Jugador jugadorActual = jugadores.get(turnoActual);
        Carta nuevaCarta = mazo.tomarCarta();
        if (nuevaCarta != null) {
            jugadorActual.tomarCarta(nuevaCarta);
            interfaz.setMensaje("El jugador '" + jugadorActual.getNombre() + "' ha tomado una carta");
            interfaz.actualizarInterfaz(cartaDescarte.getValor(), cartaDescarte.getPalo(), jugadorActual.getMano());
        } else {
            interfaz.setMensaje("El mazo esta vacio");
        }
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public boolean cartaEspecial(Carta cartaSeleccionada) {
        int valor = Integer.parseInt(cartaSeleccionada.getValor());
        Jugador siguienteJugador = jugadores.get((turnoActual + 1) % jugadores.size());

        switch (valor) {
            case 1:
                interfaz.setMensaje("El jugador '" + jugadores.get(turnoActual).getNombre() + "' uso la carta '" + cartaSeleccionada + "' por lo que repite su turno.");
                return true;
            case 2:
                interfaz.setMensaje("El jugador '" + jugadores.get(turnoActual).getNombre() + "' uso la carta '" + cartaSeleccionada + "' por lo que '" + siguienteJugador.getNombre() + "' tomaste 2 cartas.");
                for (int i = 0; i < 2; i++) {
                    Carta nuevaCarta = mazo.tomarCarta();
                    if (nuevaCarta != null) {
                        siguienteJugador.tomarCarta(nuevaCarta);
                    } else {
                        interfaz.setMensaje("El mazo esta vacio, no se puede tomar mas cartas.");
                        break;
                    }
                }
                break;
            case 3:
                interfaz.setMensaje("El jugador '" + jugadores.get(turnoActual).getNombre() + "' uso la carta '" + cartaSeleccionada + "' por lo que '" + siguienteJugador.getNombre() + "' tomaste 4 cartas.");
                for (int i = 0; i < 4; i++) {
                    Carta nuevaCarta = mazo.tomarCarta();
                    if (nuevaCarta != null) {
                        siguienteJugador.tomarCarta(nuevaCarta);
                    } else {
                        interfaz.setMensaje("El mazo esta vacio, no se puede tomar mas cartas.");
                        break;
                    }
                }
                break;
            case 10:
                interfaz.setMensaje("El jugador '" + jugadores.get(turnoActual).getNombre() + "' uso la carta '" + cartaSeleccionada + "' por lo que se salta el turno del siguiente jugador.");
                turnoActual = (turnoActual + 1) % jugadores.size();
                return false;
            default:
                interfaz.setMensaje("El jugador '" + jugadores.get(turnoActual).getNombre() + "' uso la carta '" + cartaSeleccionada + "'");

                break;
        }
        return false;
    }

    public void setCartaSeleccionada(Carta cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    private void verificarGanador(Jugador jugador) {
        if (jugador.getMano().isEmpty()) {
            interfaz.setMensaje("El jugador '" + jugador.getNombre() + "' ha ganado el juego!");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    public void setTomoCarta(boolean tomoCarta) {
        this.tomoCarta = tomoCarta;
    }
}
