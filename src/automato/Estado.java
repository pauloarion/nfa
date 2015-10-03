package automato;

/**
 *
 * @author arion/humberto
 */
class Estado {
    boolean aceitador;
    boolean inicial;
    
    Estado(boolean aceitador, boolean inicial){
        this.aceitador = aceitador;
        this.inicial = inicial;
    }
    
    boolean Aceitador() {
        return aceitador;
    }

    boolean Inicial() {
        return inicial;
    }
    
}
