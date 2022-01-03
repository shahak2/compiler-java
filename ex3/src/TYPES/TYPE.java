package TYPES;

public abstract class TYPE
{
	public String name;

	public boolean isClass()		{return false;}

	public boolean isArray()		{ return false;}
	
	public boolean isFunction()		{ return false;}
	
	public boolean isCFIELD()		{ return false;}
	
	public boolean isReturn()		{ return false;}

	public boolean isVar()			{ return false;}
}
