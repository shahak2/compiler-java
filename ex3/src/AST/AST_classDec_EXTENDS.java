package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_classDec_EXTENDS extends AST_classDec
{
    String className;
    String parentName;
    AST_cFieldList cfl;

    public int nameLine;
    public int nameColumn;
    public int parentNameLine;
    public int parentNameColumn;

    public AST_classDec_EXTENDS(String className, int nameLine, int nameColumn, String parentName, int parentNameLine, int parentNameColumn, AST_cFieldList cfl)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== classDec -> CLASS ID( %s ) EXTENDS ID( %s ) {cfilds}\n",className,parentName);

        this.className = className;
        this.parentName = parentName;
        this.cfl = cfl;

        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
        this.parentNameLine = parentNameLine;
        this.parentNameColumn = parentNameColumn;
    }
    public void PrintMe()
    {
        System.out.print("AST NODE classDec EXTENDS\n");

        System.out.format("className( %s )\n", className);
        System.out.format("EXTENDS parentName( %s )\n", parentName);
        if (cfl != null) cfl.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("classDec\n%s\nEXTENDS\n%s",className,parentName));

        if (cfl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfl.SerialNumber);
    }
    

    public boolean isLegalOverride(TYPE_FUNCTION f1, TYPE_FUNCTION f2)
        /*
         * This function returns true if function f1 may override function f2.
         */
    {
        TYPE_LIST  classParams = f1.params;
        TYPE_LIST  parentParams = f2.params;

        while(classParams!= null && parentParams!= null)
        {
            if(!(classParams.head.name.equals( parentParams.head.name)))
                return false;
            parentParams=parentParams.tail;
            classParams=classParams.tail;
        }
        if (!(parentParams == null && classParams==null))
            return false;
        if(!(f1.returnType.name.equals(f2.returnType.name)))
            return false;
        
        return true;
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

    public TYPE_CFIELD_LIST duplicate_cfl(TYPE_CFIELD_LIST cfl)
        /*
         * This function gets a CFL and returns a pointer to the head for a new copy.
         */
    {
        TYPE_CFIELD_LIST new_CFL = null ;

        for (TYPE_CFIELD_LIST cfl_iter = cfl; cfl_iter != null ; cfl_iter = cfl_iter.tail)
        {
            TYPE_CFIELD copy = new TYPE_CFIELD(cfl_iter.head.name, cfl_iter.head.type);
            new_CFL = new TYPE_CFIELD_LIST(copy, new_CFL);

        }
        return new_CFL;
    }

    public void enter_father_fields(TYPE_CFIELD_LIST cfl)
        /*
         * This function gets a CFL and returns a pointer to the head for a new copy.
         */
    {

        for (TYPE_CFIELD_LIST cfl_iter = cfl; cfl_iter != null ; cfl_iter = cfl_iter.tail)
        {
            SYMBOL_TABLE.getInstance().enter(cfl_iter.head.name, cfl_iter.head);

        }
    }


    public TYPE SemantMe()
    {
        TYPE class_t = SYMBOL_TABLE.getInstance().find(className);
        TYPE parentName_t = SYMBOL_TABLE.getInstance().find(parentName);
        TYPE_CLASS t = null;
        TYPE_CFIELD_LIST className_cfl=null;

        if (class_t != null)
        {
            System.out.format(">> ERROR [%d:%d] class name %s already exists\n", nameLine, nameColumn, className);
            SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
        }
        TYPE_CLASS className_t= new TYPE_CLASS(null, className,null);
        SYMBOL_TABLE.getInstance().beginScope();
        SYMBOL_TABLE.getInstance().enter(className, className_t);

        if (parentName_t != null)
        {
            if (parentName_t.isClass())
            {
                TYPE_CLASS parentName_tc = (TYPE_CLASS) parentName_t;
                TYPE_CFIELD_LIST parentName_cfl = parentName_tc.data_members;
                className_cfl= duplicate_cfl(parentName_cfl);
                TYPE_CFIELD_LIST concat_temp= className_cfl;
                TYPE_CFIELD_LIST NEW_className_cfl = null;
                enter_father_fields(parentName_cfl);
                /* temporarily push parents cFieldList elements into son scope */
                /* TEST_12_Nested_Var_d.txt helps to understand why this is needed */

                for (AST_cFieldList class_cfl_iter = cfl; class_cfl_iter  != null; class_cfl_iter = class_cfl_iter.tail)
                {
                    int cFieldNameLine   = class_cfl_iter.head.nameLine;
                    int cFieldNameColumn = class_cfl_iter.head.nameColumn;
                    TYPE_CFIELD class_cfield_t = (TYPE_CFIELD) class_cfl_iter.head.SemantMe(); //className CFL SEMANT ME

                    for (TYPE_CFIELD_LIST parent_cfl_iter = parentName_cfl; (parent_cfl_iter != null) || (className_cfl != null);
                         parent_cfl_iter = parent_cfl_iter.tail, className_cfl=className_cfl.tail)
                    {
                        TYPE_CFIELD parent_cfield_t = parent_cfl_iter.head;//CFL parentName
                        if (parent_cfield_t.name.equals(class_cfield_t.name)) //same name
                        {
                            className_cfl.head = class_cfield_t;
                            className_cfl=concat_temp;
                            break;
                        }

                    }
                    className_cfl = concat_temp;
                    NEW_className_cfl = new TYPE_CFIELD_LIST(class_cfield_t, NEW_className_cfl);
                }
                /* Get to end of new cFieldList defined in className */
                if(NEW_className_cfl!=null)
                {
                    TYPE_CFIELD_LIST temp= NEW_className_cfl;
                    while (NEW_className_cfl.tail != null)
                    {
                        NEW_className_cfl = NEW_className_cfl.tail;
                    }
                    NEW_className_cfl.tail = concat_temp;
                    className_cfl = temp;
                }
                t = new TYPE_CLASS(parentName_tc, className, className_cfl);
            }
            else
            {
                System.out.format(">> ERROR [%d:%d] access %s field of a non-class variable\n", parentNameLine, parentNameColumn, parentName);
                SYMBOL_TABLE.getInstance().printERROR(parentNameLine);
                System.exit(0);
            }
        }
        else
        {
            System.out.format(">> ERROR [%d:%d] access %s has not been initialized \n",parentNameLine, parentNameColumn, parentName);
            SYMBOL_TABLE.getInstance().printERROR(parentNameLine);
            System.exit(0);
        }

        SYMBOL_TABLE.getInstance().endScope();

        SYMBOL_TABLE.getInstance().enter(className, t);

        return null;
    }
}

