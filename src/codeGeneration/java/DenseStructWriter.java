package codeGeneration.java;

import at.yeoman.tools.generators.CodeWriter;
import codeGeneration.ClassDefinition;
import codeGeneration.Field;

public class DenseStructWriter
{
    private CodeWriter out;
    private ClassDefinition def;

    DenseStructWriter(CodeWriter out, ClassDefinition def)
    {
        this.out = out;
        this.def = def;
    }

    public void run()
    {
        out.println("// dense java");
        out.println();
        out.println("package " + def.getPackageName() + ";");
        out.println();
        out.beginBlock("class " + def.getClassName() + " {");
        for (Field field : def.getFields())
        {
            out.println(field.getType() + " " + field.getName() + ";");
        }
        out.endBlock();
        out.println();
    }
}
