package TYPES;

public class TYPE_RETURN extends TYPE
{
	public TYPE returnType;


	public TYPE_RETURN(String name, TYPE returnType)
	{
		this.name = name;
		this.returnType = returnType;
	}
	
	public boolean isReturn(){ return true;}
}
