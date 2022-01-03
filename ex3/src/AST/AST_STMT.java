package AST;

import TYPES.*;

public abstract class AST_STMT extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/

	public TYPE SemantMe() {
		return null;
	}
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}
}
