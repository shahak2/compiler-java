package AST;

public class AST_arrayTypedef extends AST_Node
{

    /************************/
    /* simple variable name */
    /************************/
    public String name;
    public AST_type type;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_arrayTypedef(String name, AST_type type)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== arrayTypeDef -> ARRAY ID( %s ) = type[]; \n",name);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */
    /**************************************************/
    public void PrintMe()
    {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE ARRAY TYPE DEF\n");

        System.out.format("ID NAME( %s )\n",name);

        if (type != null) type.PrintMe();


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("ARRAY ID(%s) := type [];",name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
    }
    
}
