import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Interfaz extends JFrame {
    private String direccionBaraja = "F:/Baraja";
    private ArrayList<CartaInterfaz> cartas;
    private CartaInterfaz cartaDescarte;
    private CartaInterfaz cartaMazo;
    private Chupate2 chupate2;
    private String turno = "";
    private String mensaje = "";

    public Interfaz(Chupate2 chupate2) {
        this.chupate2 = chupate2;
        setTitle("Chupate 2");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cartas = new ArrayList<>();
        cartaMazo = new CartaInterfaz(direccionBaraja + "/reverso.png", 150, 200);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(new Color(220, 220, 220));
                g.fillRect(0, 0, getWidth(), getHeight());

                Font fontTurno = new Font("Arial", Font.BOLD, 30);
                Font fontMensaje = new Font("Arial", Font.BOLD, 20);

                g.setFont(fontTurno);
                g.setColor(Color.BLACK);
                g.drawString(turno, 149, 51);
                g.drawString(turno, 151, 51);
                g.drawString(turno, 149, 49);
                g.drawString(turno, 151, 49);

                g.setColor(Color.WHITE);
                g.drawString(turno, 150, 50);

                g.setFont(fontMensaje);
                g.setColor(Color.BLACK);
                g.drawString(mensaje, 149, 101);
                g.drawString(mensaje, 151, 101);
                g.drawString(mensaje, 149, 99);
                g.drawString(mensaje, 151, 99);

                g.setColor(Color.WHITE);
                g.drawString(mensaje, 150, 100);

                if (cartaDescarte != null) {
                    cartaDescarte.paintComponent(g);
                }
                cartaMazo.paintComponent(g);

                cartas.forEach(carta -> carta.paintComponent(g));
            }
        };

        panel.addMouseListener((MouseAdapter) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (x >= cartaMazo.getPosicionX() && x <= cartaMazo.getPosicionX() + cartaMazo.getAncho() &&
                        y >= cartaMazo.getPosicionY() && y <= cartaMazo.getPosicionY() + cartaMazo.getAlto()) {
                    chupate2.setTomoCarta(true);
                }

                cartas.stream()
                        .filter(carta -> x >= carta.getPosicionX() && x <= carta.getPosicionX() + carta.getAncho() &&
                                y >= carta.getPosicionY() && y <= carta.getPosicionY() + carta.getAlto())
                        .findFirst()
                        .ifPresent(carta -> chupate2.setCartaSeleccionada(new Carta(carta.getValor(), carta.getPalo())));
            }
        });
        add(panel);
    }

    public void actualizarInterfaz(String valorDescarte, String paloDescarte, List<Carta> manoJugador) {
        cartaDescarte = new CartaInterfaz(direccionBaraja + "/" + valorDescarte + paloDescarte + ".png", 633, 200);
        cartas.clear();

        int anchoCarta = 100;
        int totalCartas = manoJugador.size();
        int separacion = totalCartas > 0 ? (720 - (totalCartas * anchoCarta)) / (totalCartas + 1) : 0;

        for (int i = 0; i < totalCartas; i++) {
            Carta carta = manoJugador.get(i);
            CartaInterfaz cartaJugador = new CartaInterfaz(direccionBaraja + "/" + carta.getValor() + carta.getPalo() + ".png",
                    327 + (i * (anchoCarta + separacion)), 555);
            cartaJugador.setValor(carta.getValor());
            cartaJugador.setPalo(carta.getPalo());
            cartas.add(cartaJugador);
        }
        repaint();
    }

    public void setTurno(String turno) {
        this.turno = turno;
        repaint();
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
        repaint();
    }
}