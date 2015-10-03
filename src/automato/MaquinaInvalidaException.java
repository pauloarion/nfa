/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automato;

/**
 *
 * @author gengis
 */
class MaquinaInvalidaException extends RuntimeException {
    public String mensagem;
    public MaquinaInvalidaException(String msg) {
        this.mensagem = msg;
    }
}
