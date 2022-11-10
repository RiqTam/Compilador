import java.awt.*;
import java.awt.event.*;
import lexico.Thompson;
import lexico.AFN;
import lexico.AFD;
import pantalla.Principal;
import pantalla.Operaciones;
import pantalla.Ventana;
import java.util.ArrayList;

public class Compilador
{
    public static void main(String[] args) throws Exception {
		ArrayList<AFN> afns = new ArrayList<AFN>();
        Thompson thomp = new Thompson();
        AFD afd = new AFD();
        Principal p = new Principal(afns,thomp,afd);
        p.setVisible(true);
    }
	
}