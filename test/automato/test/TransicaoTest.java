/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automato.test;

import automato.Estado;
import automato.Transicao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gengis
 */
public class TransicaoTest {
    
    @Test
    public void DeveRetornarIgual(){
        Estado estado1a = new Estado('a', true, true);
        Estado estado1b = new Estado('b', true, false);
        char simbolo1 = 'a';
        Transicao transicao1 = new Transicao(estado1a , estado1b, simbolo1);
        Estado estado2a = new Estado('a', true, true);
        Estado estado2b = new Estado('b', true, false);
        char simbolo2 = 'a';
        Transicao transicao2 = new Transicao(estado2a , estado2b, simbolo2);
        assertTrue(transicao1.equals(transicao2));
    }
    
    @Test
    public void DeveRetornarDiferenteSeSimboloDiferente(){
        Estado estado1a = new Estado('a', true, true);
        Estado estado1b = new Estado('b', true, false);
        char simbolo1 = 'c';
        Transicao transicao1 = new Transicao(estado1a , estado1b, simbolo1);
        Estado estado2a = new Estado('a', true, true);
        Estado estado2b = new Estado('b', true, false);
        char simbolo2 = 'a';
        Transicao transicao2 = new Transicao(estado2a , estado2b, simbolo2);
        assertFalse(transicao1.equals(transicao2));
    }
    
    @Test
    public void DeveRetornarDiferenteSeEstadoDiferente(){
        Estado estado1a = new Estado('a', true, true);
        Estado estado1b = new Estado('c', true, false);
        char simbolo1 = 'a';
        Transicao transicao1 = new Transicao(estado1a , estado1b, simbolo1);
        Estado estado2a = new Estado('a', true, true);
        Estado estado2b = new Estado('b', true, false);
        char simbolo2 = 'a';
        Transicao transicao2 = new Transicao(estado2a , estado2b, simbolo2);
        assertFalse(transicao1.equals(transicao2));
    }
}