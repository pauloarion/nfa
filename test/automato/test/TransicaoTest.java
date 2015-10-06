/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automato.test;

import automato.Estado;
import automato.Transicao;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gengis
 */
public class TransicaoTest {
    
    @Test
    public void DeveRetornarIgual(){
        Transicao transicao1 = new Transicao("A" , "B", "a");
        Transicao transicao2 = new Transicao("A" , "B", "a");
        assertTrue(transicao1.equals(transicao2));
    }
    
    @Test
    public void DeveRetornarDiferenteSeSimboloDiferente(){
        Transicao transicao1 = new Transicao("A" , "B", "c");
        Transicao transicao2 = new Transicao("A" , "B", "a");
        assertFalse(transicao1.equals(transicao2));
    }
    
    @Test
    public void DeveRetornarDiferenteSeEstadoDiferente(){
        Transicao transicao1 = new Transicao("A" , "B", "a");
        Transicao transicao2 = new Transicao("A" , "C", "a");
        assertFalse(transicao1.equals(transicao2));
    }
    
    @Test
    public void DeveConsumirSimbolo(){
        Transicao transicao1 = new Transicao("A" , "C", "a");
        assertTrue(transicao1.deveConsumirSimbolo("bba"));
    }
    
    @Test
    public void NaoDeveConsumirSimbolo(){
        Transicao transicao1 = new Transicao("A" , "C", "b");
        assertFalse(transicao1.deveConsumirSimbolo("bba"));
    }
    
}
