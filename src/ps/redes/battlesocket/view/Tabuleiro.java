/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import ps.redes.battlesocket.socket.BattleSocket;

/**
 *
 * @author Inalberth
 */
public class Tabuleiro extends JPanel {
    
    private int linhas;
    private int colunas;
    
    private static final int BUTTON_SIZE = 50;
    private static final Dimension DIMENSION = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
    
    private JButton[][] buttonGrid;
    private Color corJogador;
    private String titulo;
    
    private BattleSocket socket;
    
    public Tabuleiro() {
        
        initComponents();
    }
    
    
    public Tabuleiro(int linhas, int colunas) {
        
        this.linhas = linhas;
        this.colunas = colunas;
        setLayout(new GridLayout(linhas, colunas));
        initComponents();
    }
    
    public Tabuleiro(int linhas, int colunas, Color corJogador, String titulo, BattleSocket socket) {
        
        this.linhas = linhas;
        this.colunas = colunas;
        this.corJogador = corJogador;
        this.titulo = titulo;
        this.socket = socket;
        
        setLayout(new GridLayout(linhas, colunas));
        setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), titulo.toUpperCase()));
        initComponents();
    }
    
    private void initComponents() {
        
        buttonGrid = new JButton[linhas][colunas];
        
        for (int i = 0; i < getLinhas(); i++) {
            for (int j = 0; j < getColunas(); j++) {
              
                buttonGrid[i][j] = new JButton();
                buttonGrid[i][j].setMinimumSize(Tabuleiro.DIMENSION);
                buttonGrid[i][j].setPreferredSize(Tabuleiro.DIMENSION);
                buttonGrid[i][j].setMaximumSize(Tabuleiro.DIMENSION);
                buttonGrid[i][j].addActionListener(listenerInicializar);
                
                add(buttonGrid[i][j]);
            }
        }
    }
    
    public void jogada(int linha, int coluna) {
        
        buttonGrid[linha][coluna].setBackground(Color.red);
    }

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }
    
    public Color getCorJogador() {
        return corJogador;
    }
    
    public void setCorJogador(Color corJogador) {
        this.corJogador = corJogador;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    ActionListener listenerInicializar = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            
            int x = (int)(((JButton)(e.getSource())).getLocation().x);
            int y = (int)(((JButton)(e.getSource())).getLocation().y);
            
            System.out.println(((JButton)(e.getSource())).getLocation().toString() + "AA: " + 
            ((JButton)(e.getSource())).getBounds().getX());
            ((JButton)(e.getSource())).setBackground(corJogador);
            socket.write(x, y);
        }
        }; 
}
