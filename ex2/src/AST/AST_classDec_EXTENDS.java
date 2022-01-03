package AST;

public class AST_classDec_EXTENDS extends AST_classDec{
    String name1;
    String name2;
    AST_cFieldList cfl;

    public AST_classDec_EXTENDS(String name1,String name2, AST_cFieldList cfl){
        SerialNumber = AST_Node_Serial_Number.getFresh();


        System.out.format("====================== classDec -> CLASS ID( %s ) EXTENDS ID( %s ) {cfilds}\n",name1,name2);

        this.name1 = name1;
        this.name2 = name2;
        this.cfl = cfl;
    }
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.print("AST NODE classDec EXTENDS\n");

        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        System.out.format("ID1( %s )\n", name1);
        System.out.format("EXTENDS ID2( %s )\n", name2);
        if (cfl != null) cfl.PrintMe();


        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("classDec\nEXTENDS\nID1\n%s\n%s",name1,name2));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cfl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfl.SerialNumber);
    }
}
