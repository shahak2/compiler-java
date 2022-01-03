package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_cFieldList extends AST_Node
{
    public AST_cField head;
    public AST_cFieldList tail;

    public int headLine;
    public int headColumn;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_cFieldList(AST_cField head, int headLine, int headColumn, AST_cFieldList tail)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("====================== cFields -> cField cFields\n");
        if (tail == null) System.out.print("====================== cFields -> cField      \n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.head = head;
        this.tail = tail;

        this.headLine = headLine;
        this.headColumn = headColumn;
    }

    /******************************************************/
    /* The printing message for a statement list AST node */
    /******************************************************/
    public void PrintMe()
    {
        /**************************************/
        /* AST NODE TYPE = AST STATEMENT LIST */
        /**************************************/
        System.out.print("AST NODE cField LIST\n");

        /*************************************/
        /* RECURSIVELY PRINT HEAD + TAIL ... */
        /*************************************/
        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "STMT\nLIST\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
    }

    public TYPE_CFIELD_LIST SemantMe()
	{
        TYPE_CFIELD_LIST cFieldList = new TYPE_CFIELD_LIST(null, null);
        
		if (head != null) cFieldList.head = (TYPE_CFIELD) head.SemantMe();
        SYMBOL_TABLE.getInstance().enter(cFieldList.head.name, cFieldList.head);
		if (tail != null) cFieldList.tail = (TYPE_CFIELD_LIST) tail.SemantMe();
		
		return cFieldList;
	}

}
