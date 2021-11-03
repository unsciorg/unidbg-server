package com.spider.unidbgserver.service;


import com.github.unidbg.linux.android.dvm.DalvikModule;
import com.github.unidbg.linux.android.dvm.DvmClass;
import com.github.unidbg.linux.android.dvm.StringObject;
import com.spider.unidbgserver.dto.QiangdongSignDTO;
import com.spider.unidbgserver.vm.QiangDongVM;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QiangdongSignService {


    public String sign(QiangdongSignDTO qiangdongSignDTO){

        DvmClass context = qiangDongVM.getVm().resolveClass("android/app/Activity");
        DvmClass cBitmapkitUtils = qiangDongVM.getVm().resolveClass("com/jingdong/common/utils/BitmapkitUtils");

        StringObject strRc = cBitmapkitUtils.callStaticJniMethodObject(qiangDongVM.getEmulator()
                ,"getSignFromJni()(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
                context,
                qiangdongSignDTO.getFunctionId(),
                qiangdongSignDTO.getData(),
                qiangdongSignDTO.getUuid(),
                "android",
                qiangdongSignDTO.getClientVersion());

        return strRc.getValue();
    }


    @Resource
    private QiangDongVM qiangDongVM;
}
