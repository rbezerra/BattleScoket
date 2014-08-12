/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inalberth
 */
public class BattleSocket {
    
    private static final int PORTA = 2014;
    private static final String IP = "127.0.0.1";
    
    private DatagramSocket socketP1, server;
    private InetAddress socketP2;
    private byte[] mensagem;
    
    public BattleSocket() throws SocketException, UnknownHostException {
        
        init();
    }
    
    private void init() throws SocketException, UnknownHostException {
        
        String str = "Hello, ";
        
        socketP1 = new DatagramSocket();
        socketP2 = InetAddress.getByName(IP);
        
        server = new DatagramSocket(PORTA);
        
        DatagramPacket pacote = new DatagramPacket(str.getBytes(), str.getBytes().length, socketP2, PORTA);
        
        try {
            
            socketP1.send(pacote);
            
        } catch (IOException ex) {
            Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mensagem = new byte[str.length()];
        
        DatagramPacket pa = new DatagramPacket(mensagem, mensagem.length);
        
        while (true) {
            
            try {
                
                server.receive(pa);
                
            } catch (IOException ex) {
                Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String hash = new String(pa.getData());
            
            if (hash.equals(str)) {
                
                try {
                    
                    socketP1.send(pacote);
                    
                } catch (IOException ex) {
                    Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
