package AST;

import TYPES.*;

public class AST_cField_VAR_DEC extends AST_cField 
{
    public AST_varDec varDec;

    public int varDecLine;
    public int varDecColumn;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_cField_VAR_DEC(AST_varDec varDec, int line, int column) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("====================== cField -> varDec\n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.varDec = varDec;

        this.varDecLine = line;
        this.varDecColumn = column;
    }

    /***********************************************/
    /* The default message for an exp var AST node */

    /***********************************************/
    public void PrintMe() {
        /************************************/
        /* AST NODE TYPE = EXP VAR AST NODE */
        /************************************/
        System.out.print("AST NODE cField VAR DEC\n");

        /*****************************/
        /* RECURSIVELY PRINT var ... */
        /*****************************/
        if (varDec != null) varDec.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "cField\nVAR\nDEC");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, varDec.SerialNumber);
    }

    public TYPE_CFIELD SemantMe() 
    {
        if (varDec != null) 
        {
            TYPE_CFIELD cfl = new TYPE_CFIELD(null, null);
            cfl.type = varDec.SemantMe();
            cfl.name=cfl.type.name;
            return cfl;
        }
        
        return null;
    }

}
