import javax.swing.*;
import java.awt.*;

public class CartaInterfaz extends JPanel {
    private String direccionImagen;
    private int posicionX;
    private int posicionY;
    private int ancho;
    private int alto;
    private String palo;
    private String valor;

    public CartaInterfaz(String direccionImagen, int posicionX, int posicionY) {
        this.direccionImagen = direccionImagen;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.ancho = 100;
        this.alto = 150;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public String getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = new ImageIcon(direccionImagen).getImage();
        g.drawImage(img, posicionX, posicionY, ancho, alto, this);
    }
}
