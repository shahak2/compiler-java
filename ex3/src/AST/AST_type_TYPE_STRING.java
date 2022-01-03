package AST;

import TYPES.*;

public class AST_type_TYPE_STRING extends AST_type 
{
    public AST_type_TYPE_STRING()
    {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== type -> TYPE_STRING\n");
        this.name="string";


    }

    /**************************************************/
    /* The printing message for a simple var AST node */
    /**************************************************/
    public void PrintMe()
    {
        System.out.format("AST NODE TYPE STRING\n");

        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE\nSTRING"));
    }
    
    public TYPE SemantMe()
	{
		return TYPE_STRING.getInstance();
	}	
}
