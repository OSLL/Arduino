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
	
	public int loadAndRun(File file, String key){
		Message message = null;
		try {		
			Socket s = new Socket(address, port);
			Messenger.writeMessage(s, new Message("LOAD"));
			Messenger.writeMessage(s, new Message(key));
			message = Messenger.readMessage(s);
			switch (message.getText()) {
			case "ACCESS_ERROR":
				System.out.println("Access error");
				return -4;
			}
			if(!message.getText().equals("OK")){
				return -3;
			}
			sendFile(s, file);
			message = Messenger.readMessage(s);
			s.close();
		} catch (IOException e) {
			return -1;
		}
		if(message.getText().equals("OK"))
			return message.getParameter();
		else return -2;
	}

	public int loadAndRunSimulator(File file, String key, final String resultStoragePath,SimulAVRConfigs configs){
//		Message message = null;
		try {		
			final Socket s = new Socket(address, port);
			Messenger.writeMessage(s, new Message("LOAD_SIMUL"));
			Messenger.writeSimulAVRConfigs(s, configs);
			sendFile(s, file);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						loadFile(s, resultStoragePath + "simulAVR-vcd-output");
						s.close();
						event.resultReceived();
					} catch (IOException e) {
					}		
				}
			});
			thread.start();
//			message = Messenger.readMessage(s);
			
		} catch (IOException e) {
			return -1;
		}
//		if(message.getText().equals("OK"))
//			return message.getParameter();
//		else return -2;
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
		System.out.println("Loading file");
		InputStream inputStream = s.getInputStream();
		DataInputStream dis = new DataInputStream(inputStream);
			long size = dis.readLong();
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			for(long i=0; i<size; i++){
				file.writeByte(dis.readByte());
			}
			file.close();
	}
	
}

