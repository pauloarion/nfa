package automato;

import java.util.Objects;

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
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estado other = (Estado) obj;
        if (!Objects.equals(this.simbolo, other.simbolo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + simbolo;
    }
}
