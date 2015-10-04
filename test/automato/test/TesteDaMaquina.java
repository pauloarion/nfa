package automato.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import automato.Estado;
import automato.Maquina;
import automato.MaquinaInvalidaException;
import automato.Transicao;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author gengis
 */
public class TesteDaMaquina {
    
    Maquina maquina1 = null;
    Maquina maquinaComLoop = null;
    
    @Before
    public void setUp() {

        ConstruirMaquina1();
        ConstruirMaquinaComLoop();
    }

    private void ConstruirMaquina1() {
        Estado estadoA = new Estado('A', false, true);
        Estado estadoB = new Estado('B', false, false);
        Estado estadoC = new Estado('C', true, false);
        List<Estado> estados = new ArrayList<Estado>();
        estados.add(estadoA);
        estados.add(estadoB);
        estados.add(estadoC);
        
        List<Transicao> transicoes = new ArrayList<Transicao>();
        transicoes.add(new Transicao(estadoA, estadoB, 'b'));
        transicoes.add(new Transicao(estadoA, estadoC, Maquina.EpsilonSymbol));
        transicoes.add(new Transicao(estadoB, estadoB, 'a'));
        transicoes.add(new Transicao(estadoB, estadoC, 'a'));
        transicoes.add(new Transicao(estadoB, estadoC , 'b'));
        transicoes.add(new Transicao(estadoC, estadoA, 'a'));
        
        maquina1 = new Maquina(estados, transicoes);
    }
    
    private void ConstruirMaquinaComLoop() {
        Estado estadoA = new Estado('A', false, true);
        Estado estadoB = new Estado('B', false, false);
        Estado estadoC = new Estado('C', true, false);
        List<Estado> estados = new ArrayList<Estado>();
        estados.add(estadoA);
        estados.add(estadoB);
        estados.add(estadoC);
        
        List<Transicao> transicoes = new ArrayList<Transicao>();
        transicoes.add(new Transicao(estadoA, estadoB, Maquina.EpsilonSymbol));
        transicoes.add(new Transicao(estadoB, estadoA, Maquina.EpsilonSymbol));
        
        maquinaComLoop = new Maquina(estados, transicoes);
    }

    
    @Test(expected = MaquinaInvalidaException.class)
    public void deveRejeitarSeNaoPossuiEstadoInicial(){
        List<Estado> estados = new ArrayList<Estado>();
        estados.add(new Estado('A', false, false));
        estados.add(new Estado('B', false, false));
        estados.add(new Estado('C', true, false));
        new Maquina(estados, maquina1.getTransicoes());
    }
    
    @Test(expected = MaquinaInvalidaException.class)
    public void deveRejeitarSePossuiMaisDeUmEstadoInicial(){
        List<Estado> estados = new ArrayList<Estado>();
        estados.add(new Estado('A', false, true));
        estados.add(new Estado('B', false, true));
        estados.add(new Estado('C', true, false));
        new Maquina(estados, maquina1.getTransicoes());
    }
    
    @Test(expected = MaquinaInvalidaException.class)
    public void deveRejeitarSeNaoPossuiNenhumEstadoAceitador(){
        List<Estado> estados = new ArrayList<Estado>();
        estados.add(new Estado('A', false, true));
        estados.add(new Estado('B', false, false));
        estados.add(new Estado('C', false, false));
        new Maquina(estados, maquina1.getTransicoes());
    }
    
    @Test
    public void deveIgnorarLoop(){
        maquinaComLoop.processar("aab");
        Assert.assertFalse(maquinaComLoop.isStringPertenceALinguagem());
    }
    
    @Test
    public void deveAceitarString(){
        maquina1.processar("aaa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
    }
    
    @Test
    public void deveRecusarString(){
        maquina1.processar("bbb");
        Assert.assertFalse(maquina1.isStringPertenceALinguagem());
    }
}
