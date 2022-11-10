package pantalla;
import java.awt.*;
import java.awt.event.*;
import lexico.Thompson;
import lexico.AFN;
import lexico.AFD;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Principal extends Frame
{

	public Principal(ArrayList<AFN> afns, Thompson thomp, AFD afd)
    {   
        setSize(1000, 600);
		setLayout(new GridLayout(4, 2));
		
		MenuBar mb = new MenuBar();
		Menu m1 = new Menu ("AFN`S");
		MenuItem mi1 = new MenuItem ("Basico");
		MenuItem mi2 = new MenuItem ("Unir");
        MenuItem mi3 = new MenuItem ("Concatenar");
        MenuItem mi4 = new MenuItem ("Cerradura mas");
        MenuItem mi5 = new MenuItem ("Cerradura de Kleene");
        MenuItem mi6 = new MenuItem ("Opcional");
        MenuItem mi7 = new MenuItem ("Convertir a AFD");
        MenuItem mi8 = new MenuItem ("Salir");
		mb.add(m1);		
		m1.add(mi1);
		m1.add(mi2);
        m1.add(mi3);
        m1.add(mi4);
        m1.add(mi5);
        m1.add(mi6);
        m1.add(mi7);
        m1.add(mi8);
		setMenuBar(mb);

        mi1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                Operaciones m1 = new Operaciones(1,afns,thomp,afd);
		        m1.setVisible(true);
			}
		});

        mi2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                Operaciones m2 = new Operaciones(2,afns,thomp,afd);
		        m2.setVisible(true);
			}
		});

        mi3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                Operaciones m3 = new Operaciones(3,afns,thomp,afd);
		        m3.setVisible(true);
			}
		});

        mi4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                Operaciones m4 = new Operaciones(4,afns,thomp,afd);
		        m4.setVisible(true);
			}
		});

        mi5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                Operaciones m5 = new Operaciones(5,afns,thomp,afd);
		        m5.setVisible(true);
			}
		});

        mi6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                Operaciones m6 = new Operaciones(6,afns,thomp,afd);
		        m6.setVisible(true);
			}
		});

        mi7.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                Operaciones m7 = new Operaciones(7,afns,thomp,afd);
		        m7.setVisible(true);
			}
		});

        mi8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                dispose();
			}
		});

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing (WindowEvent e)
			{
				System.exit(0);
			}
		});     
    }
	
}