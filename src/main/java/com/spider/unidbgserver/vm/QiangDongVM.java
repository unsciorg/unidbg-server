package com.spider.unidbgserver.vm;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.linux.android.dvm.VM;

public class QiangDongVM {

    private AndroidEmulator emulator;
    private VM vm;

    public QiangDongVM() {
    }

    public QiangDongVM(AndroidEmulator emulator, VM vm) {
        this.emulator = emulator;
        this.vm = vm;
    }

    public AndroidEmulator getEmulator() {
        return emulator;
    }

    public void setEmulator(AndroidEmulator emulator) {
        this.emulator = emulator;
    }

    public VM getVm() {
        return vm;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }
}
