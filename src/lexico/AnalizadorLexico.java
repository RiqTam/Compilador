package lexico;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalizadorLexico{
        boolean edoAcept;
        int iniLexema, finLexema, token;
        int posAct = 0;
        int afd[][];
        String cadena;

    public int yylex(){
        iniLexema = posAct;
        finLexema = -1;
        edoAcept = false;
        token = -1;
        if(this.posAct == this.cadena.length()){
            return 0;
        }
        int edoAct = 0;
        while(this.posAct < this.cadena.length()){
            edoAct = this.afd[edoAct][(int) cadena.charAt(posAct)];
            if(edoAct != -1){
                if(this.afd[edoAct][127] != -1){
                    this.edoAcept = true;
                    this.finLexema = posAct;
                    this.token = afd[edoAct][127];
                }
            }else{
                if(this.edoAcept){
                    return this.token;
                }else{
                    return -10;
                }
            }
            this.posAct++;
        }
        return this.token;
    }

    public String getLexema(){
        if(this.finLexema != -1){
            return this.cadena.substring(iniLexema, finLexema + 1);
        }
        return null;
    }

    private int[][] createTabla(String nombreAFD){
        int count = 0;
        int tabla[][] = new int[0][0];
        try{
            File archivo = new File(nombreAFD + ".txt");
            Scanner sc = new Scanner(archivo);
            while(sc.hasNextLine()){
                sc.nextLine();
                count++;
            }
            tabla = new int[count][128];
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        try{
            File archivo = new File(nombreAFD + ".txt");
            Scanner sc = new Scanner(archivo);
            count = 0;
            while(sc.hasNextLine()){
                String[] arreglo = sc.nextLine().split(";");
                for(int i = 0; i < 128; i++){
                    tabla[count][i] = Integer.parseInt(arreglo[i]);
                }
                count++;
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return tabla;
    }

    public AnalizadorLexico(String nombreAFD, String cadena){
        this.afd = createTabla(nombreAFD);
        this.cadena = cadena;
    }
}