package pantalla;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import lexico.Thompson;
import lexico.AnalizadorLexico;
import lexico.AFN;
import lexico.AFD;

@SuppressWarnings("serial")
public class Operaciones extends Frame
{
	public Operaciones(int op, ArrayList<AFN> afns, ArrayList<String> afds, Thompson thomp, AFD afd)
	{
        Button btn2 = new Button ("Realizar operacion");
        switch(op){
            case 1:
                setSize(400, 150);
                setLayout(new GridLayout(4, 2));
        
                Label lblOp1 = new Label("Crear AFN basico");
                Label lblOp2 = new Label("");
                Label lblOp3 = new Label("Introduzca el simbolo inferior:");
                TextField txtOp1 = new TextField();
                Label lblOp4 = new Label("Introduzca el simbolo superior:");
                TextField txtOp2 = new TextField();
                add(lblOp1);
                add(lblOp2);
                add(lblOp3);
                add(txtOp1);
                add(lblOp4);
                add(txtOp2);

                Button btn = new Button ("Boton");
                add(btn);
        
                btn.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        char simbInf = txtOp1.getText().charAt(0);
                        char simbSup = txtOp2.getText().charAt(0);
                        afns.add(thomp.basico(simbInf, simbSup));
                        Ventana v = new Ventana(1);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 2:
                setSize(500, 150);
                setLayout(new GridLayout(4, 2));
        
                Label lblOp5 = new Label("OPERACION UNIR");
                Label lblOp6 = new Label("");
                Label lblOp7 = new Label("Introduzca el indice del primer AFN:");
                TextField txtOp3 = new TextField();
                Label lblOp8 = new Label("Introduzca el indice del segundo AFN:");
                TextField txtOp4 = new TextField();
                add(lblOp5);
                add(lblOp6);
                add(lblOp7);
                add(txtOp3);
                add(lblOp8);
                add(txtOp4);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp3.getText());
                        int inAFN1 = Integer.parseInt(txtOp4.getText());
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
        
                Label lblOp9 = new Label("OPERACION CONCATENAR");
                Label lblOp10 = new Label("");
                Label lblOp11 = new Label("Introduzca el indice del primer AFN:");
                TextField txtOp5 = new TextField();
                Label lblOp12 = new Label("Introduzca el indice del segundo AFN:");
                TextField txtOp6 = new TextField();
                add(lblOp9);
                add(lblOp10);
                add(lblOp11);
                add(txtOp5);
                add(lblOp12);
                add(txtOp6);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp5.getText());
                        int inAFN1 = Integer.parseInt(txtOp6.getText());
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
        
                Label lblOp13 = new Label("OPERACION CERRADURA TRANSITIVA");
                Label lblOp14 = new Label("");
                Label lblOp15 = new Label("Introduzca el indice del AFN:");
                TextField txtOp7 = new TextField();
                add(lblOp13);
                add(lblOp14);
                add(lblOp15);
                add(txtOp7);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp7.getText());
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
        
                Label lblOp16 = new Label("OPERACION CERRADURA KLEENE");
                Label lblOp17 = new Label("");
                Label lblOp18 = new Label("Introduzca el indice del AFN:");
                TextField txtOp8 = new TextField();
                add(lblOp16);
                add(lblOp17);
                add(lblOp18);
                add(txtOp8);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp8.getText());
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
        
                Label lblOp19 = new Label("OPERACION OPCIONAL");
                Label lblOp20 = new Label("");
                Label lblOp21 = new Label("Introduzca el indice del AFN:");
                TextField txtOp9 = new TextField();
                add(lblOp19);
                add(lblOp20);
                add(lblOp21);
                add(txtOp9);
                add(btn2);
        
                btn2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp9.getText());
                        afns.set(inAFN0, thomp.opcional(afns.get(inAFN0)));
                        Ventana v = new Ventana(2);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 7:
                
                break;
            case 8:
                setSize(500, 150);
                setLayout(new GridLayout(4, 2));
        
                Label lblOp22 = new Label("Convertir a AFD");
                Label lblOp23 = new Label("");
                Label lblOp24 = new Label("Introduzca el indice del AFN:");
                TextField txtOp10 = new TextField();
                Label lblOp25 = new Label("Escribe el nombre del archivo a guardar:");
                TextField txtOp11 = new TextField();
                add(lblOp22);
                add(lblOp23);
                add(lblOp24);
                add(txtOp10);
                add(lblOp25);
                add(txtOp11);

                Button btn3 = new Button ("Convertir y guardar");
                add(btn3);
        
                btn3.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp10.getText());
                        String archivo = txtOp11.getText();
                        afds.add(afd.crearAFD(afns.get(inAFN0),archivo));
                        Ventana v = new Ventana(3);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 9:
                setSize(500, 100);
                setLayout(new GridLayout(3, 2));
        
                Label lblOp26 = new Label("Cargar AFD");
                Label lblOp27 = new Label("");
                Label lblOp28 = new Label("Introduzca el nombre del AFD:");
                TextField txtOp12 = new TextField();
                add(lblOp26);
                add(lblOp27);
                add(lblOp28);
                add(txtOp12);

                Button btn4 = new Button ("Cargar");
                add(btn4);
                btn4.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        
                        Ventana v = new Ventana(4);
                        v.setVisible(true);
                        dispose();
                    }
                });
                break;
            case 10:
                setSize(500, 150);
                setLayout(new GridLayout(4, 2));
        
                Label lblOp29 = new Label("Analizador Lexico");
                Label lblOp30 = new Label("");
                Label lblOp31 = new Label("Introduzca el indice del AFD:");
                TextField txtOp13 = new TextField();
                Label lblOp32 = new Label("Introduzca la cadena a analizar:");
                TextField txtOp14 = new TextField();
                add(lblOp29);
                add(lblOp30);
                add(lblOp31);
                add(txtOp13);
                add(lblOp32);
                add(txtOp14);

                Button btn5 = new Button ("Analizar");
                add(btn5);
                btn5.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int inAFN0 = Integer.parseInt(txtOp13.getText());
                        AnalizadorLexico lexico = new AnalizadorLexico(afds.get(inAFN0), txtOp14.getText());
                        boolean continuarLexema = true;
                        while(continuarLexema){
                            int token = lexico.yylex();
                            System.out.println("Token: " + token);
                            System.out.println("Lexema: " + lexico.getLexema() + "\n");
                            if(token == 0 || token == -10){
                                continuarLexema = false;
                            }
                        }
                        Ventana v = new Ventana(4);
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