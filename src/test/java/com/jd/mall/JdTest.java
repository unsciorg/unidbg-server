package com.jd.mall;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.DalvikModule;
import com.github.unidbg.linux.android.dvm.VM;
import com.github.unidbg.memory.Memory;

import java.io.File;

public class JdTest {
    public static void main(String[] args) {
        AndroidEmulator emulator = AndroidEmulatorBuilder.for32Bit()
                .setProcessName("com.jingdong.app.mall")
                .build();
        Memory memory = emulator.getMemory();
        memory.setLibraryResolver(new AndroidResolver(23));

        String APK_PATH = "D:\\tmp\\V10.1.032bit.apk";
        File apkFile = new File(APK_PATH);
        VM vm = emulator.createDalvikVM(apkFile);
        vm.setJni(new JniDispatch32JD2(APK_PATH));
        vm.setVerbose(true);

        DalvikModule dm = vm.loadLibrary("jdbitmapkit", false);
        dm.callJNI_OnLoad(emulator);
    }
}
