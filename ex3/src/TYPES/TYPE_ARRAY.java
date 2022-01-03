package TYPES;

public class TYPE_ARRAY extends TYPE
{
	public TYPE arrayType;
	
	public TYPE_ARRAY(String arrayName, TYPE arrayType)
	{
		this.name=arrayName;
		this.arrayType = arrayType;
	}
	public boolean isArray(){return true;}
}
