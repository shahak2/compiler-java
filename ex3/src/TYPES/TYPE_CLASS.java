package TYPES;

public class TYPE_CLASS extends TYPE
{

	private static TYPE_CLASS instance = null;

	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;
	
	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_CFIELD_LIST data_members;


	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String className,TYPE_CFIELD_LIST data_members)
	{
		this.name=className;
		this.father = father;
		this.data_members = data_members;
	}


	public boolean isClass()	{return true;}



}
