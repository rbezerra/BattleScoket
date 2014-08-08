
import java.net.*;

public class Socket {
	
	private DatagramSocket socket_self;
	private DatagramSocket socket_server;
	private InetAddress partner_ip;
	
	int port = 9192;
	
	public Socket(String ip) throws Exception {
		socket_self = new DatagramSocket();
		partner_ip = InetAddress.getByName(ip);
		socket_server = new DatagramSocket(port);
		socket_server.setReceiveBufferSize(32);
		
		// find eachother
		String hello = "hi";
		DatagramPacket hi = new DatagramPacket(hello.getBytes(), hello.getBytes().length, partner_ip, port);
		try {
			socket_self.send(hi);
		} catch (Exception e) {
			System.out.println(e);
		}
		byte[] howdy = new byte[2];
		DatagramPacket howdy_packet = new DatagramPacket(howdy, howdy.length);	
		partner:
		while (true) {
			try {
				System.out.println("Waiting for opponent to connect...");
				socket_server.receive(howdy_packet);
			} catch (Exception e) {
				System.out.println(e);
			}
			String h = new String(howdy_packet.getData());
			System.out.println(h);
			if (h.equals("hi")) {
				try {
					socket_self.send(hi);
				} catch (Exception e) {
					System.out.println(e);
				}
				hi = null;
				howdy_packet = null;
				break partner;
			}
		}
		
	}
	
	public boolean readyToPlay() {
		clearBuffer();
		String ready_a = "ra";
		String ready_b = "rb";
		DatagramPacket ra = new DatagramPacket(ready_a.getBytes(), ready_a.getBytes().length, partner_ip, port);
		DatagramPacket rb = new DatagramPacket(ready_b.getBytes(), ready_b.getBytes().length, partner_ip, port);
		try {
			socket_self.send(ra);
		} catch (Exception e) {
			System.out.println(e);
		}	
		byte[] im_ready = new byte[2];
		DatagramPacket im_ready_packet = new DatagramPacket(im_ready, im_ready.length);
		while (true) {
			try {
				System.out.println("Waiting for opponent to place boats...");
				socket_server.receive(im_ready_packet);
			} catch (Exception e) {
				System.out.println(e);
			}
			String r = new String(im_ready_packet.getData());
			if (r.equals("rb")) {
				return false;
			}
			else if (r.equals("ra")) {
				try {
					socket_self.send(rb);
				} catch (Exception e) {
					System.out.println(e);
				}
				
				return true;
			}
		}
	}
	
	public int sendMove(int x, int y) {
		String move = x+""+y;
		DatagramPacket p = new DatagramPacket(move.getBytes(), move.getBytes().length, partner_ip, port);
		try {
			socket_self.send(p);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		byte[] response = new byte[2];
		DatagramPacket response_packet = new DatagramPacket(response, response.length);
		try {
			socket_server.receive(response_packet);
		} catch (Exception e) {
			System.out.println(e);
		}
		String res = new String(response_packet.getData());
		int res_int = Integer.parseInt(res.substring(0,1));
		
		return res_int;
	}
	
	public int[] receiveMove() {
		byte[] response = new byte[4];
		DatagramPacket response_packet = new DatagramPacket(response, response.length);
		while(true) {
			try {
				socket_server.receive(response_packet);
			} catch (Exception e) {
				System.out.println(e);
			}
			String res = new String(response_packet.getData());
			int[] move = new int[2];
			move[0] = Integer.parseInt(res.substring(0,1));
			move[1] = Integer.parseInt(res.substring(1,2));

			return move;
		}
	}
	
	public void sendResponse(int res) {
		String response = res+"";
		DatagramPacket p = new DatagramPacket(response.getBytes(), response.getBytes().length, partner_ip, port);
		try {
			socket_self.send(p);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void clearBuffer() {
		socket_server.close();
		try {
			socket_server = new DatagramSocket(port);
			socket_server.setReceiveBufferSize(32);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}