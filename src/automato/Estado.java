package automato;

/**
 *
 * @author arion/humberto
 */
public class Estado {
    boolean aceitador;
    boolean inicial;
    public String simbolo;
    
    public Estado(String simbolo, boolean aceitador, boolean inicial){
        this.aceitador = aceitador;
        this.inicial = inicial;
        this.simbolo = simbolo;
    }
    
    public boolean Aceitador() {
        return aceitador;
    }

    public boolean Inicial() {
        return inicial;
    }

    @Override
    public boolean equals(Object obj) {
        return this.simbolo.equals(((Estado)obj).simbolo);
    }

    @Override
    public String toString() {
        return "" + simbolo;
    }
}
