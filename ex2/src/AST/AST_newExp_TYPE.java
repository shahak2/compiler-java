package AST;

public class AST_newExp_TYPE extends AST_newExp{
    public AST_type type;

    public AST_newExp_TYPE(AST_type t){
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("====================== newExp -> NEW type\n");

        this.type = t;

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
}
