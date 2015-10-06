package automato;

/**
 *
 * @author arion/humberto
 */
public class Transicao {
    
    Estado estadoOrigem;
    Estado estadoDestino;
    char simboloParaConsumir;
    

    public Transicao(Estado estadoOrigem, Estado estadoDestino, char simboloParaConsumir) {
        this.estadoOrigem = estadoOrigem;
        this.estadoDestino = estadoDestino;
        this.simboloParaConsumir = simboloParaConsumir;
        
    }
    
    /*Foi definido que o símbolo que representa transição vazia é o '&'.*/
    boolean isEmpty() {
        if (simboloParaConsumir == Maquina.EpsilonSymbol)return true;
        else return false;
    }

    boolean deveConsumirSimbolo(String input) {
        
        /*Se não existem símbolos para consumir então o processamento acaba.*/
        if (input.isEmpty()) return false;
        
        char simboloConsumido = input.charAt(input.length() - 1);
        /*Se os símbolos para consumir e o consumido são iguais.*/
        if (simboloParaConsumir == simboloConsumido) return true;
        /*Se os símbolos para consumir e o consumido não são iguais.*/
        return false;
        
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
                && ((Transicao)obj).simboloParaConsumir == this.simboloParaConsumir;
    }
    
    

    @Override
    public String toString() {
        return "" + estadoOrigem.symbol + "," + this.simboloParaConsumir + " -> " + estadoDestino.symbol;
    }
}
