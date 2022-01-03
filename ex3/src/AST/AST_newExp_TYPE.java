package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_newExp_TYPE extends AST_newExp
{
    public AST_type type;

    public int typeLine;
    public int typeColumn;

    public AST_newExp_TYPE(AST_type t, int typeLine, int typeColumn)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("====================== newExp -> NEW type\n");

        this.type = t;

        this.typeLine = typeLine;
        this.typeColumn = typeColumn;

    }
    
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.print("AST NODE newExp TYPE\n");

        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        if (type != null) type.PrintMe();


        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "newExp\nTYPE\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);

    }
    
    public TYPE SemantMe()
    {
    	return SYMBOL_TABLE.getInstance().find(type.name);
    }
}
