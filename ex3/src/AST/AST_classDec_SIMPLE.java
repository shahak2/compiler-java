package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_classDec_SIMPLE extends AST_classDec{

    String name;
    AST_cFieldList cfl;

    public int nameLine;
    public int nameColumn;

    public AST_classDec_SIMPLE(String name, int nameLine, int nameColumn, AST_cFieldList cfl){
        SerialNumber = AST_Node_Serial_Number.getFresh();


        System.out.format("====================== classDec -> CLASS ID( %s ) {cfilds}\n",name);

        this.name = name;
        this.cfl = cfl;

        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
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
                String.format("classDec\n%s",name));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cfl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfl.SerialNumber);
    }
    public void printCflList(TYPE_CFIELD_LIST cfl)
        /*
         * for debug purposes
         */
    {
        for (TYPE_CFIELD_LIST cfl_iter = cfl; cfl_iter != null ; cfl_iter = cfl_iter.tail)
        {
            System.out.println(cfl_iter.head.name);
        }
    }
    public TYPE SemantMe()
    {
        /*************************/
        /* [1] Begin Class Scope */
        /*************************/

        //TYPE_CFIELD_LIST cfield_list;
    
        TYPE class_type = SYMBOL_TABLE.getInstance().find(name);
        
        if (class_type != null) {
            System.out.format(">> ERROR [%d:%d] name already exists \n", nameLine, nameColumn, class_type.name);
            SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
        }

        SYMBOL_TABLE.getInstance().beginScope();

        /***************************/
        /* [2] Semant Data Members */
        /***************************/
        TYPE_CLASS t_c= new TYPE_CLASS(null,name,null);

        SYMBOL_TABLE.getInstance().enter(name,t_c);


        TYPE_CLASS t = new TYPE_CLASS(null, name, cfl.SemantMe());



        SYMBOL_TABLE.getInstance().endScope();

        SYMBOL_TABLE.getInstance().enter(name,t);

        return null;
    }

}

