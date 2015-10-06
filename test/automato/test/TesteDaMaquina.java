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
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gengis
 */
public class TesteDaMaquina {
    
    Maquina maquina1 = null;
    Maquina maquinaComLoop = null;
    Set<String> alfabeto = new HashSet<>();
    
    @Before
    public void setUp() {
        alfabeto.add("a");
        alfabeto.add("b");
        ConstruirMaquina1();
        ConstruirMaquinaComLoop();
    }

    private void ConstruirMaquina1() {
        Estado estadoA = new Estado("A", true, true);
        Estado estadoB = new Estado("B", false, false);
        Estado estadoC = new Estado("C", false, false);
        Set<Estado> estados = new HashSet<Estado>();
        estados.add(estadoA);
        estados.add(estadoB);
        estados.add(estadoC);
        
        Set<Transicao> transicoes = new HashSet<Transicao>();
        transicoes.add(new Transicao(estadoA, estadoB, "b"));
        transicoes.add(new Transicao(estadoA, estadoC, Maquina.Epsilon));
        transicoes.add(new Transicao(estadoB, estadoB, "a"));
        transicoes.add(new Transicao(estadoB, estadoC, "a"));
        transicoes.add(new Transicao(estadoB, estadoC , "b"));
        transicoes.add(new Transicao(estadoC, estadoA, "a"));
        
        maquina1 = new Maquina(estados, transicoes, alfabeto);
    }
    
    private void ConstruirMaquinaComLoop() {
        Estado estadoA = new Estado("A", false, true);
        Estado estadoB = new Estado("B", false, false);
        Estado estadoC = new Estado("C", true, false);
        Set<Estado> estados = new HashSet<Estado>();
        estados.add(estadoA);
        estados.add(estadoB);
        estados.add(estadoC);
        
        Set<Transicao> transicoes = new HashSet<Transicao>();
        transicoes.add(new Transicao(estadoA, estadoB, Maquina.Epsilon));
        transicoes.add(new Transicao(estadoB, estadoA, Maquina.Epsilon));
        
        maquinaComLoop = new Maquina(estados, transicoes, alfabeto);
    }

    
    @Test(expected = MaquinaInvalidaException.class)
    public void deveRejeitarSeNaoPossuiEstadoInicial(){
        Set<Estado> estados = new HashSet<Estado>();
        estados.add(new Estado("A", false, false));
        estados.add(new Estado("B", false, false));
        estados.add(new Estado("C", true, false));
        new Maquina(estados, maquina1.getTransicoes(), alfabeto);
    }
    
    @Test(expected = MaquinaInvalidaException.class)
    public void deveRejeitarSePossuiMaisDeUmEstadoInicial(){
        Set<Estado> estados = new HashSet<Estado>();
        estados.add(new Estado("A", false, true));
        estados.add(new Estado("B", false, true));
        estados.add(new Estado("C", true, false));
        new Maquina(estados, maquina1.getTransicoes(), alfabeto);
    }
    
    @Test(expected = MaquinaInvalidaException.class)
    public void deveRejeitarSeNaoPossuiNenhumEstadoAceitador(){
        Set<Estado> estados = new HashSet<Estado>();
        estados.add(new Estado("A", false, true));
        estados.add(new Estado("B", false, false));
        estados.add(new Estado("C", false, false));
        new Maquina(estados, maquina1.getTransicoes(), alfabeto);
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
        maquina1.processar("bba");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("baa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("bbaaaa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("baaaa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("a");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("aa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("aaaa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("baaaaba");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("baaaabaaa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("bbabaa");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
        maquina1.processar("bbabba");
        Assert.assertTrue(maquina1.isStringPertenceALinguagem());
    }
    
    @Test
    public void deveRecusarString(){
        maquina1.processar("bbb");
        Assert.assertFalse(maquina1.isStringPertenceALinguagem());
        maquina1.processar("bbab");
        Assert.assertFalse(maquina1.isStringPertenceALinguagem());
        maquina1.processar("bbaba");
        Assert.assertFalse(maquina1.isStringPertenceALinguagem());
    }
}

