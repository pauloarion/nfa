package automato;

/**
 *
 * @author arion/humberto
 */
public class Estado {
    boolean aceitador;
    boolean inicial;
    char symbol;
    
    public Estado(char symbol, boolean aceitador, boolean inicial){
        this.aceitador = aceitador;
        this.inicial = inicial;
        this.symbol = symbol;
    }
    
    boolean Aceitador() {
        return aceitador;
    }

    boolean Inicial() {
        return inicial;
    }

    @Override
    public boolean equals(Object obj) {
        return this.symbol == ((Estado)obj).symbol;
    }
    
    
    
}
