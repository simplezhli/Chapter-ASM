package com.geektime.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class TryCatchMethodAdapter extends AdviceAdapter {

    private String methodName;
    private String className;
    private Label tryStart = new Label();
    private Label tryEnd = new Label();
    private Label catchStart = new Label();
    private Label catchEnd = new Label();

    protected TryCatchMethodAdapter(int api, MethodVisitor mv, int access, String name, String desc, String className) {
        super(api, mv, access, name, desc);
        this.className = className;
        this.methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        if (className.equals("com/sample/asm/MainActivity") && methodName.equals("mm")) {
            mv.visitTryCatchBlock(tryStart, tryEnd, catchStart, "java/lang/Exception");
            mv.visitLabel(tryStart);
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (className.equals("com/sample/asm/MainActivity") && methodName.equals("mm")) {
            mv.visitLabel(tryEnd);
            mv.visitJumpInsn(GOTO, catchEnd);
            mv.visitLabel(catchStart);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/RuntimeException", "printStackTrace", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitLabel(catchEnd);
        }
    }
}