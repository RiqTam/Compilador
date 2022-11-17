package lexico;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class AnalizadorLexico{
        boolean edoAcept;
        int iniLexema, finLexema, token;
        int posAct = 0;
        int afd[][];
        String cadena;
        String yytext;
        Stack<Integer> indices = new Stack<Integer>();

    public int yylex(){
        iniLexema = posAct;
        finLexema = -1;
        edoAcept = false;
        token = -1;
        if(this.posAct == this.cadena.length()){
            this.yytext = null;
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
                    this.yytext = this.cadena.substring(iniLexema, finLexema + 1);
                    this.indices.push(iniLexema);
                    return this.token;
                }else{
                    return -10;
                }
            }
            this.posAct++;
        }
        if(this.edoAcept){
            this.yytext = this.cadena.substring(iniLexema, finLexema + 1);
            this.indices.push(iniLexema);
            return this.token;
        }
        return -10;
    }

    public String yytext(){
        return yytext;
    }

    public void undoToken(){
        this.posAct = this.indices.pop();
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