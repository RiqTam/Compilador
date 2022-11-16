package pantalla;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Ventana extends Frame
{
	public Ventana(int op)
    {
        setSize(250, 100);
        setLayout(new GridLayout(2, 1));
        Button btn = new Button ("Aceptar");
        switch(op){
            case 1:
                Label lblOp1 = new Label("AFN creado");
                add(lblOp1);
                add(btn);
                break;
            case 2:
                Label lblOp2 = new Label("Operacion realizada");
                add(lblOp2);
                add(btn);
                break;
            case 3:
                Label lblOp3 = new Label("AFN a sido transformado a AFD");
                add(lblOp3);
                add(btn);
                break;
            case 4:
                Label lblOp4 = new Label("Guardado");
                add(lblOp4);
                add(btn);
                break;
        }
        
        btn.addActionListener(new ActionListener()
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
                dispose();
            }
        });
    }
	
}