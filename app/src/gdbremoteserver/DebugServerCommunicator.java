package gdbremoteserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import avrdebug.communication.Message;
import avrdebug.communication.Messenger;
import avrdebug.communication.SimulAVRConfigs;
import avrdebug.communication.SimulAVRInitData;

public class DebugServerCommunicator {

	
	String address;
	int port;
	private SimulatorResultReceivedEvent event;
	public DebugServerCommunicator(String address, int port, SimulatorResultReceivedEvent event) {
		this.address = address;
		this.port = port;
		this.event = event;
	}
	
	public int loadAndRun(File file, String token){
		Message message = null;
		try {		
			Socket s = new Socket(address, port);
			Messenger.writeMessage(s, new Message("DEBUG_MCU"));
			Messenger.writeMessage(s, new Message(token));
			message = Messenger.readMessage(s);
			if(message == null){
				return -1;
			}
			if(message.getParameter()<0){
				System.err.println(message.getText());
				return message.getParameter();
			}
			System.out.println("Sending file");
			sendFile(s, file);
			message = Messenger.readMessage(s);
			s.close();
		} catch (IOException e) {
			return -1;
		}
		if("OK".equals(message.getText()))
			return message.getParameter();
		else return -2;
	}

	public void setAddressPort(String address, int port){
		this.address = address;
		this.port = port;		
	}
	
	public int loadAndRunSimulator(File file, String token, final String resultStoragePath,SimulAVRConfigs configs){
		Message message = null;
		try {		
			final Socket s = new Socket(address, port);
			Messenger.writeMessage(s, new Message("DEBUG_SIMUL"));
			Messenger.writeMessage(s, new Message(token));
			message = Messenger.readMessage(s);
			
			if(message == null){
				return -1;
			}
			if(message.getParameter()<0){
				System.err.println(message.getText());
				return message.getParameter();
			}
			
			System.out.println("Send START");
			Messenger.writeMessage(s, new Message("START"));
			
			message = Messenger.readMessage(s);
			
			if(message == null){
				return -11;
			}
			if(message.getParameter()<0){
				System.err.println(message.getText());
				return message.getParameter();
			}
			
			
			Messenger.writeSimulAVRConfigs(s, configs);
			sendFile(s, file);
			
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						File file = new File(resultStoragePath + "simulAVR-vcd-output");
						if(file.exists())
							file.delete();
						loadFile(s, resultStoragePath + "simulAVR-vcd-output");
						s.close();
						event.resultReceived();
					} catch (IOException e) {
					}		
				}
			});

			message = Messenger.readMessage(s);
			if(message == null)
				return -3;
			if(message.getText().equals("OK")){
				if(configs.isVCDTraceEnable())
					thread.start();
				return message.getParameter();
			}
			else
				return -2;
			
		} catch (IOException e) {
			return -1;
		}

	}
	
	public int stopSimulator(String token){
		Message message = null;
		try {		
			final Socket s = new Socket(address, port);
			Messenger.writeMessage(s, new Message("DEBUG_SIMUL"));
			Messenger.writeMessage(s, new Message(token));
			message = Messenger.readMessage(s);
			
			if(message == null){
				return -1;
			}
			if(message.getParameter()<0){
				System.err.println(message.getText());
				return message.getParameter();
			}
			
			Messenger.writeMessage(s, new Message("STOP"));
		} catch (IOException e) {
			return -1;
		}
		return 0;		
	}
	
	public SimulAVRInitData getSimulAvrInitData(){
		SimulAVRInitData result = null;
		try {
			Socket s = new Socket(address, port);
			Messenger.writeMessage(s, new Message("GET_INIT_SIMUL_CONFIG"));
			result = Messenger.readSimulAVRInitData(s);
			return result;
		} catch (IOException e) {
			return null;
		}
	}
	
	private void sendFile(Socket s, File file) throws IOException{
		OutputStream str = s.getOutputStream();
		DataOutputStream  dos = new DataOutputStream(str);
		dos.writeLong(file.length());
		RandomAccessFile rfile = new RandomAccessFile(file.getAbsolutePath(), "r");
		byte buff[] = new byte[128];
		int size;
		while((size = rfile.read(buff)) >0){
			dos.write(buff, 0, size);
		}
		rfile.close();
	}
	
	private void loadFile(Socket s, String filename) throws IOException{
		System.out.println("Loading file from server");
		InputStream inputStream = s.getInputStream();
		DataInputStream dis = new DataInputStream(inputStream);
			long size = dis.readLong();
			System.out.println("File size to receive: " + size);
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			for(long i=0; i<size; i++){
				file.writeByte(dis.readByte());
			}
			file.close();
		System.out.println("File loaded successfuly");
	}
	
}

