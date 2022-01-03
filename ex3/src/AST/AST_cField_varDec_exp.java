package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_cField_varDec_exp extends AST_varDec
{
    public String name;
    public AST_type type;
    public AST_exp exp;

    public int nameLine;
    public int nameColumn;
    public int typeLine;
    public int typeColumn;
    public int expLine;
    public int expColumn;


    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_cField_varDec_exp(AST_type type, int typeLine, int typeColumn,  String name, int nameLine, int nameColumn,  AST_exp exp, int expLine, int expColumn)
    {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        if(exp == null) System.out.format("====================== cFieldVarDec -> type ID( %s ); \n", name);
        if(exp != null) System.out.format("====================== cFieldVarDec -> type ID( %s ) ASSIGN exp; \n", name);

        this.name = name;
        this.type = type;
        this.exp = exp;

        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
        this.typeLine = typeLine;
        this.typeColumn = typeColumn;
        this.expLine = expLine;
        this.expColumn = expColumn;
    }

    public void PrintMe() 
    {

        System.out.format("AST NODE cFieldVarDec exp( %s )\n", name);

        if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("ASSIGN\n(%s) := right\n", name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }



    public TYPE SemantMe()
    {
        TYPE t;

        t = SYMBOL_TABLE.getInstance().find(type.name);
        if(t == null)
        {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n",typeLine, typeColumn, type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
        if(t.name.equals("void")){
            System.out.format(">> ERROR [%d:%d] illegal to declare a void type\n",typeLine, typeColumn);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }

        if (exp!= null) 
        {
            TYPE exp_t = exp.SemantMe();

            if (exp_t != t) {
                if (t.isClass() == true || t.isArray() == true) {
                    if (exp_t != TYPE_NIL.getInstance()) {
                        System.out.format(">> ERROR [%d:%d] illegal to assign string/int type to class/array type\n", expLine, expColumn);
                        SYMBOL_TABLE.getInstance().printERROR(expLine);
                        System.exit(0);
                    }
                } else {
                    System.out.format(">> ERROR [%d:%d] can't assign type %s to type %s\n", expLine, expColumn, exp_t.name, t.name);
                    SYMBOL_TABLE.getInstance().printERROR(expLine);
                    System.exit(0);
                }
            }
        }
        TYPE id_t=SYMBOL_TABLE.getInstance().find_in_curr_scope(name);
        TYPE_CFIELD id_t_cfield= null;
        if(id_t!= null)
        {
            if(id_t.isCFIELD()){
                id_t_cfield=(TYPE_CFIELD)id_t;
                if(id_t_cfield.type.isVar()){
                    TYPE_VAR father_var=(TYPE_VAR)id_t_cfield.type;
                    if(father_var.varType.name.equals(t.name)){
                        SYMBOL_TABLE.getInstance().enter(name, t);
                        TYPE_VAR var= new TYPE_VAR(name,t);
                        return var;
                    }

                }
                System.out.format(">> ERROR [%d:%d] shadowing \n", nameLine, nameColumn);
                SYMBOL_TABLE.getInstance().printERROR(nameLine);
                System.exit(0);
            }

            System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",nameLine, nameColumn, name);
            SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
        }

        SYMBOL_TABLE.getInstance().enter(name, t);
        TYPE_VAR var= new TYPE_VAR(name,t);
        return var;
    }

}
