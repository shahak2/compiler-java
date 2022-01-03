package AST;

import TYPES.*;

public class AST_type_TYPE_VOID extends AST_type
{
    public AST_type_TYPE_VOID()
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== type -> TYPE_VOID\n");
        this.name="void";
    }


    public void PrintMe()
    {

        System.out.format("AST NODE TYPE VOID\n");

        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE\nVOID"));
    }
    
    public TYPE SemantMe()
	{
		return TYPE_VOID.getInstance();
	}
}
