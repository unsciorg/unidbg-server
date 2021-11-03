package com.jd.mall;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.linux.android.dvm.array.ByteArray;
import com.github.unidbg.linux.android.dvm.jni.ProxyDvmObject;
import com.github.unidbg.linux.android.dvm.wrapper.DvmInteger;
import com.github.unidbg.memory.Memory;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.ParsingException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.cert.X509Certificate;

public class JniDispatch32JD2 extends AbstractJni {

//    private void destroy() throws IOException {
//        emulator.close();
//        System.out.println("destroy");
//    }


    private void test() {
//        DvmClass cBitmapkitUtils = vm.resolveClass("com/jingdong/common/utils/BitmapkitUtils");
//        StringObject strRc = cBitmapkitUtils.callStaticJniMethodObject(emulator,"getSignFromJni()(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
//                vm.resolveClass("android/app/Activity").newObject(null),
//                "asynInteface",
//                "{\"intefaceType\":\"asynIntefaceType\",\"skuId\":\"100008667315\"}",
//                "99001184062989-f460e22c02fa",
//                "android",
//                "9.2.2");
//
//        System.out.println("666");
//        System.out.println(strRc.getValue());
    }

    @Override
    public DvmObject<?> getStaticObjectField(BaseVM vm, DvmClass dvmClass, String signature) {
        if ("com/jingdong/common/utils/BitmapkitUtils->a:Landroid/app/Application;".equals(signature)) {
            return vm.resolveClass("android/app/Activity", vm.resolveClass("android/content/ContextWrapper", vm.resolveClass("android/content/Context"))).newObject(null);
        }

        return super.getStaticObjectField( vm,  dvmClass,  signature);
    }

    @Override
    public DvmObject<?> callStaticObjectMethod(BaseVM vm, DvmClass dvmClass, String signature, VarArg varArg) {
        switch (signature) {
            case "com/jingdong/common/utils/BitmapkitZip->unZip(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B":
                StringObject apkPath = varArg.getObjectArg(0);
                StringObject directory = varArg.getObjectArg(1);
                StringObject filename = varArg.getObjectArg(2);
                if (APK_PATH.equals(apkPath.getValue()) &&
                        "META-INF/".equals(directory.getValue()) &&
                        ".RSA".equals(filename.getValue())) {
                    byte[] data = vm.unzip("META-INF/JINGDONG.RSA");
                    return new ByteArray(vm, data);
                }
            case "com/jingdong/common/utils/BitmapkitZip->objectToBytes(Ljava/lang/Object;)[B":
                DvmObject<?> obj = varArg.getObjectArg(0);
                byte[] bytes = objectToBytes(obj.getValue());
                return new ByteArray(vm, bytes);
        }

        return super.callStaticObjectMethod(vm, dvmClass, signature, varArg);
    }

    @Override
    public DvmObject<?> newObjectV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        switch (signature) {
            case "java/lang/StringBuffer-><init>()V":
                return vm.resolveClass("java/lang/StringBuffer").newObject(new StringBuffer());
            case "java/lang/Integer-><init>(I)V":
                int value = vaList.getIntArg(0);
                return DvmInteger.valueOf(vm, value);
        }

        return super.newObjectV(vm, dvmClass, signature, vaList);
    }

    @Override
    public DvmObject<?> callObjectMethodV(BaseVM vm, DvmObject<?> dvmObject, String signature, VaList vaList) {
        switch (signature) {
            case "java/lang/StringBuffer->append(Ljava/lang/String;)Ljava/lang/StringBuffer;": {
                StringBuffer buffer = (StringBuffer) dvmObject.getValue();
                StringObject str = vaList.getObjectArg(0);
                buffer.append(str.getValue());
                return dvmObject;
            }
            case "java/lang/Integer->toString()Ljava/lang/String;":
                Integer it = (Integer) dvmObject.getValue();
                return new StringObject(vm, it.toString());
            case "java/lang/StringBuffer->toString()Ljava/lang/String;":
                StringBuffer buffer = (StringBuffer) dvmObject.getValue();
                return new StringObject(vm, buffer.toString());
        }

        return super.callObjectMethodV(vm, dvmObject, signature, vaList);
    }

    @Override
    public DvmObject<?> newObject(BaseVM vm, DvmClass dvmClass, String signature, VarArg varArg) {
        if ("sun/security/pkcs/PKCS7-><init>([B)V".equals(signature)) {
            ByteArray array = varArg.getObjectArg(0);
            try {
                return vm.resolveClass("sun/security/pkcs/PKCS7").newObject(new PKCS7(array.getValue()));
            } catch (ParsingException e) {
                throw new IllegalStateException(e);
            }
        }

        return super.newObject(vm, dvmClass, signature, varArg);
    }

    @Override
    public DvmObject<?> callObjectMethod(BaseVM vm, DvmObject<?> dvmObject, String signature, VarArg varArg) {
        if ("sun/security/pkcs/PKCS7->getCertificates()[Ljava/security/cert/X509Certificate;".equals(signature)) {
            PKCS7 pkcs7 = (PKCS7) dvmObject.getValue();
            X509Certificate[] certificates = pkcs7.getCertificates();
            return ProxyDvmObject.createObject(vm, certificates);
        }

        return super.callObjectMethod(vm, dvmObject, signature, varArg);
    }

    private static byte[] objectToBytes(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            byte[] array = baos.toByteArray();
            oos.close();
            baos.close();
            return array;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public DvmObject<?> getObjectField(BaseVM vm, DvmObject<?> dvmObject, String signature) {
        if ("android/content/pm/ApplicationInfo->sourceDir:Ljava/lang/String;".equals(signature)) {
            return new StringObject(vm, apkPath);
        }

        return super.getObjectField(vm, dvmObject, signature);
    }

    private String apkPath ;

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public JniDispatch32JD2(String apkPath) {
        this.apkPath = apkPath;
    }

    public JniDispatch32JD2() {
    }

    private static final String APK_PATH = "D:\\tmp\\V10.1.032bit.apk";
}
