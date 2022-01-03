package AST;

public class AST_cField_FUNC_DEC extends AST_cField
{
    public AST_funcDec funcDec;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_cField_FUNC_DEC(AST_funcDec funcDec) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("====================== cField -> funcDec\n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.funcDec = funcDec;
    }

    /***********************************************/
    /* The default message for an exp var AST node */

    /***********************************************/
    public void PrintMe() {
        /************************************/
        /* AST NODE TYPE = EXP VAR AST NODE */
        /************************************/
        System.out.print("AST NODE cField FUNC DEC\n");

        /*****************************/
        /* RECURSIVELY PRINT var ... */
        /*****************************/
        if (funcDec != null) funcDec.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "cField\nFUNC\nDEC");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (funcDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcDec.SerialNumber);
    }
}
