package io.zerows.core.cloud.cache;

import io.horizon.annotations.Memory;
import io.horizon.uca.cache.Cc;
import io.zerows.core.cloud.atom.secure.KPermit;
import io.zerows.core.cloud.atom.secure.KSemi;

/**
 * 「运行时系统级数据缓存」
 * 氚(chuān) - 3H（稀有能源元素）
 *
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
interface CH3H extends CH2H {

    /*
     * 「环境级别处理」安全管理专用
     * - CC_PERMIT  :  KPermit      权限定义对象
     * - CC_SEMI    :  KSemi        权限执行双维对象（维度+数据）
     */
    @Memory(KPermit.class)
    Cc<String, KPermit> CC_PERMIT = Cc.open();
    @Memory(KSemi.class)
    Cc<String, KSemi> CC_SEMI = Cc.open();
}
