package automato;

/**
 *
 * @author arion/humberto
 */
public class Transicao {
    
    String estadoOrigem;
    String estadoDestino;
    String simbolo;
    

    public Transicao(String estadoOrigem, String estadoDestino, String simbolo) {
        this.estadoOrigem = estadoOrigem;
        this.estadoDestino = estadoDestino;
        this.simbolo = simbolo;
        
    }
    
    /*Foi definido que o símbolo que representa transição vazia é o '&'.*/
    boolean isEmpty() {
        if (simbolo.equals(Maquina.Epsilon))return true;
        else return false;
    }

    public boolean deveConsumirSimbolo(String input) {
        
        /*Se não existem símbolos para consumir então o processamento acaba.*/
        if (input.isEmpty()) return false;
        
        String simboloConsumido = extrairSimboloDaPalavra(input);

        return simbolo.equals(simboloConsumido);
    }

    private String extrairSimboloDaPalavra(String input) {
        try {
            return input.substring(input.length() - simbolo.length());
        } catch (Exception e) {return null;}
    }

    /*Transação atual é uma transação inversa da transação testada.*/
    boolean isReverse(Transicao transicao) {
        return this.estadoDestino.equals(transicao.estadoOrigem) && this.estadoOrigem.equals(transicao.estadoDestino);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)return false;
        return ((Transicao)obj).estadoOrigem.equals(this.estadoOrigem)
                && ((Transicao)obj).estadoDestino.equals(this.estadoDestino)
                && ((Transicao)obj).simbolo.equals(this.simbolo);
    }
    
    

    @Override
    public String toString() {
        return "" + estadoOrigem + "," + this.simbolo + " -> " + estadoDestino;
    }
}
