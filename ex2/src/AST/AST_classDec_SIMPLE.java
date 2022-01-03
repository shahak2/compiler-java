package AST;


public class AST_classDec_SIMPLE extends AST_classDec{

    String name;
    AST_cFieldList cfl;

    public AST_classDec_SIMPLE(String name, AST_cFieldList cfl){
        SerialNumber = AST_Node_Serial_Number.getFresh();


        System.out.format("====================== classDec -> CLASS ID( %s ) {cfilds}\n",name);

        this.name = name;
        this.cfl = cfl;
    }
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.print("AST NODE classDec SIMPLE\n");

        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        System.out.format("ID( %s )\n", name);
        if (cfl != null) cfl.PrintMe();


        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("classDec\nSIMPLE\nID->%s",name));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cfl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfl.SerialNumber);
    }


}
