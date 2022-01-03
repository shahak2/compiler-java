package TYPES;

public class TYPE_FUNCTION extends TYPE
{
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST params;

	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String funcName,TYPE_LIST params)
	{
		this.name=funcName;
		this.returnType = returnType;
		this.params = params;
	}
	
	public boolean isFunction(){ return true;}
}
