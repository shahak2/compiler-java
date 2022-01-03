package AST;

public class AST_varDec_SIMPLE extends AST_varDec {

    public String name;
    public AST_type type;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_varDec_SIMPLE(AST_type type,String name) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== varDec -> type ID( %s ); \n", name);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */

    /**************************************************/
    public void PrintMe() {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE VAR DEC SIMPLE\n");

        if (type != null) type.PrintMe();

        System.out.format("ID NAME( %s )\n",name);


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("type ID(%s)", name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);

    }
}
