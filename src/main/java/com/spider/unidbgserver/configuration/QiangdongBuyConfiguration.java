package com.spider.unidbgserver.configuration;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.DalvikModule;
import com.github.unidbg.linux.android.dvm.Jni;
import com.github.unidbg.linux.android.dvm.VM;
import com.github.unidbg.memory.Memory;
import com.spider.unidbgserver.jni.QiangDongJni;
import com.spider.unidbgserver.vm.QiangDongVM;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class QiangdongBuyConfiguration {

    @Bean
    public QiangDongVM qiangDongVM() throws FileNotFoundException {

        AndroidEmulator emulator = AndroidEmulatorBuilder.for32Bit()
                .setProcessName("com.jingdong.app.mall")
                .build();
        Memory memory = emulator.getMemory();
        memory.setLibraryResolver(new AndroidResolver(23));

        File apkFile = ResourceUtils.getFile("classpath:example_binaries\\V10.1.032bit.apk");
        VM vm = emulator.createDalvikVM(apkFile);
        Jni jni = new QiangDongJni(apkFile.getAbsolutePath());
        vm.setJni(jni);
        vm.setVerbose(true);

        DalvikModule dm = vm.loadLibrary("jdbitmapkit", false);
        dm.callJNI_OnLoad(emulator);

        return new QiangDongVM(emulator, vm);
    }


    @PreDestroy
    public void destroy() {

//        emulator.close();
//        System.out.println("destroy");
    }
}
