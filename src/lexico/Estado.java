package lexico;
import java.util.HashSet;

public class Estado {
    private static int contId = 0;
    private int id;
    HashSet<Transicion> trans = new HashSet<Transicion>();
    private boolean edoAcept;
    private int token;
    HashSet<Estado> edos = new HashSet<Estado>();

    public int getId(){
        return this.id;
    }

    public HashSet<Transicion> getTrans(){
        return this.trans;
    }

    public boolean getEdoAcept(){
        return this.edoAcept;
    }

    public int getToken(){
        return this.token;
    }

    public HashSet<Estado> getEdos(){
        return this.edos;
    }

    public void setTrans(Transicion t) {
        this.trans.add(t);
    }

    public void setEdoAcept(boolean edoAcept){
        this.edoAcept = edoAcept;
    }

    public void setEdos(HashSet<Estado> edos){
        this.edos = edos;
    }

    public Estado(boolean edoAcept, int token){
        this.edoAcept = edoAcept;
        this.token = token;
        this.id = contId++;
    }

    public Estado(Transicion t, boolean edoAcept, int token) {
        this.trans.add(t);
        this.edoAcept = edoAcept;
        this.token = token;
        this.id = contId++;
    }

    public Estado(boolean edoAcept, int token, HashSet<Estado> edos){
        this.edoAcept = edoAcept;
        this.token = token;
        this.edos = edos;
    }
}