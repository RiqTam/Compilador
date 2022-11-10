package pantalla;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import lexico.Thompson;
import lexico.AFN;
import lexico.AFD;

@SuppressWarnings("serial")
public class Operaciones extends Frame
{
	public Operaciones(int op, ArrayList<AFN> afns, Thompson thomp, AFD afd)
	{
        Button btn2 = new Button ("Realizar operacion");
        switch(op){
            case 1:
            setSize(350, 100);
            setLayout(new GridLayout(3, 2));
    
            Label lblOp1 = new Label("Crear AFN basico");
            Label lblOp2 = new Label("");
            Label lblOp3 = new Label("Introduzca el simbolo:");
            TextField txtOp1 = new TextField();
            add(lblOp1);
            add(lblOp2);
            add(lblOp3);
            add(txtOp1);

            Button btn = new Button ("Boton");
            add(btn);
    
            btn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    char simb = txtOp1.getText().charAt(0);
                    afns.add(thomp.basico(simb));
                    Ventana v = new Ventana(1);
                    v.setVisible(true);
                    dispose();
                }
            });
                break;
            case 2:
                setSize(500, 150);
                setLayout(new GridLayout(4, 2));
        
                Label lblOp4 = new Label("OPERACION UNIR");
                Label lblOp5 = new Label("");
                Label lblOp6 = new Label("Introduzca el indice del primer AFN:");
                TextField txtOp2 = new TextField();
                Label lblOp7 = new Label("Introduzca el indice del segundo AFN:");
                TextField txtOp3 = new TextField();
                add(lblOp4);
                add(lblOp5);
                add(lblOp6);
                add(txtOp2);
                add(lblOp7);
                add(txtOp3);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp2.getText());
                        int inAFN1 = Integer.parseInt(txtOp3.getText());
                        afns.set(inAFN0, thomp.unir(afns.get(inAFN0), afns.get(inAFN1)));
                        afns.remove(inAFN1);
                        Ventana v = new Ventana(2);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 3:
                setSize(500, 150);
                setLayout(new GridLayout(4, 2));
        
                Label lblOp8 = new Label("OPERACION CONCATENAR");
                Label lblOp9 = new Label("");
                Label lblOp10 = new Label("Introduzca el indice del primer AFN:");
                TextField txtOp4 = new TextField();
                Label lblOp11 = new Label("Introduzca el indice del segundo AFN:");
                TextField txtOp5 = new TextField();
                add(lblOp8);
                add(lblOp9);
                add(lblOp10);
                add(txtOp4);
                add(lblOp11);
                add(txtOp5);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp4.getText());
                        int inAFN1 = Integer.parseInt(txtOp5.getText());
                        afns.set(inAFN0, thomp.concatenar(afns.get(inAFN0), afns.get(inAFN1)));
                        afns.remove(inAFN1);
                        Ventana v = new Ventana(2);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 4:
                setSize(500, 100);
                setLayout(new GridLayout(3, 2));
        
                Label lblOp12 = new Label("OPERACION CERRADURA MAS");
                Label lblOp13 = new Label("");
                Label lblOp14 = new Label("Introduzca el indice del AFN:");
                TextField txtOp6 = new TextField();
                add(lblOp12);
                add(lblOp13);
                add(lblOp14);
                add(txtOp6);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp6.getText());
                        afns.set(inAFN0, thomp.cMas(afns.get(inAFN0)));
                        Ventana v = new Ventana(2);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 5:
                setSize(500, 100);
                setLayout(new GridLayout(3, 2));
        
                Label lblOp15 = new Label("OPERACION CERRADURA KLEENE");
                Label lblOp16 = new Label("");
                Label lblOp17 = new Label("Introduzca el indice del AFN:");
                TextField txtOp7 = new TextField();
                add(lblOp15);
                add(lblOp16);
                add(lblOp17);
                add(txtOp7);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp7.getText());
                        afns.set(inAFN0, thomp.cKleene(afns.get(inAFN0)));
                        Ventana v = new Ventana(2);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 6:
                setSize(500, 100);
                setLayout(new GridLayout(3, 2));
        
                Label lblOp18 = new Label("OPERACION OPCIONAL");
                Label lblOp19 = new Label("");
                Label lblOp20 = new Label("Introduzca el indice del AFN:");
                TextField txtOp8 = new TextField();
                add(lblOp18);
                add(lblOp19);
                add(lblOp20);
                add(txtOp8);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp8.getText());
                        afns.set(inAFN0, thomp.opcional(afns.get(inAFN0)));
                        Ventana v = new Ventana(2);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 7:
                setSize(500, 150);
                setLayout(new GridLayout(4, 2));
        
                Label lblOp21 = new Label("Convertir a AFD");
                Label lblOp22 = new Label("");
                Label lblOp23 = new Label("Introduzca el indice del AFN:");
                TextField txtOp9 = new TextField();
                Label lblOp24 = new Label("Escribe el nombre del archivo a guardar:");
                TextField txtOp10 = new TextField();
                add(lblOp21);
                add(lblOp22);
                add(lblOp23);
                add(txtOp9);
                add(lblOp24);
                add(txtOp10);

                Button btn3 = new Button ("Convertir y guardar");
                add(btn3);
        
                btn3.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp9.getText());
                        String archivo = txtOp10.getText();
                        afd.crearAFD(afns.get(inAFN0),archivo);
                        Ventana v = new Ventana(3);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
        }
        

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing (WindowEvent e)
            {
                dispose();
            }
        });
	}
}