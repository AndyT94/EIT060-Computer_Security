
public class Client {
	private String name;
	private boolean read,write,execute;
	private int divison;
	
	public Client(String name,boolean read,boolean write, boolean execute,int divison){
		this.name = name;
		this.read = read;
		this.write = write;
		this.execute = execute;
		this.divison = divison;
		
	}
	
	public String getName(){
		return name;
	}
	public boolean getReadRights(){
		return read;
	}
	public boolean getWriteRights(){
		return write;
	}
	public boolean getExecuteRights(){
		return execute;
	}
	
	public int getDivison(){
		return divison;
	}

}
