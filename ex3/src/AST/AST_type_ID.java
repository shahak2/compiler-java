package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_type_ID extends AST_type
{
    
    public int nameLine;
    public int nameColumn;

    public AST_type_ID(String name, int nameLine, int nameColumn)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== type -> ( %s )\n",name);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.name = name;

        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */
    /**************************************************/
    public void PrintMe()
    {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE TYPE ID( %s )\n",name);

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE\nID\n(%s)",name));
    }
    
    public TYPE SemantMe()
	{
    	
    	TYPE t = SYMBOL_TABLE.getInstance().find(name);
		/*if (t == null)
		{
			System.exit(0);
			return null;
		}
		else
		{
			SYMBOL_TABLE.getInstance().enter(name, t);
		}
*/
		return t;
	}	
}
