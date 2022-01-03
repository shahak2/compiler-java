package AST;

public class AST_newExp_EXP extends AST_newExp{
    public AST_type type;
    public AST_exp exp;

    public AST_newExp_EXP(AST_type t,AST_exp e){
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("====================== newExp -> NEW type [exp] \n");

        this.type = t;
        this.exp = e;

    }
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.print("AST NODE newExp EXP\n");

        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();


        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "newExp\nEXP\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
    }

}
