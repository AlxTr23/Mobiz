package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal

import me.zywl.fdpclient.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode
import me.zywl.fdpclient.value.impl.FloatValue

class MotionFlagNofall : NoFallMode("MotionFlag") {
    private val flySpeedValue = FloatValue("${valuePrefix}MotionSpeed", -0.01f, -5f, 5f)
    override fun onNoFall(event: UpdateEvent) {
        if (mc.thePlayer.fallDistance > 3) {
            mc.thePlayer.motionY = flySpeedValue.get().toDouble()
        }
    }
}