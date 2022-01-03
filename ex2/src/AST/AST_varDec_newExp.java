package AST;

public class AST_varDec_newExp extends AST_varDec
{
    public String name;
    public AST_type type;
    public AST_newExp newExp;

    public AST_varDec_newExp(AST_type type,String name, AST_newExp newExp) {
        
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== varDec -> type ID( %s ) ASSIGN newWxp; \n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;
        this.newExp = newExp;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */

    /**************************************************/
    public void PrintMe() {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE varDec newExp(%s)\n", name);

        if (type != null) type.PrintMe();
        if (newExp != null) newExp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("VAR\nDEC\nNEWEXP\n(%s)", name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
    }

}
