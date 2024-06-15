package net.ccbluex.liquidbounce.features.module.modules.movement.jesus.aac

import me.zywl.fdpclient.event.MoveEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.jesus.JesusMode
import me.zywl.fdpclient.value.impl.FloatValue

class AACFlyJesus : JesusMode("AACFly") {
    private val aacMotionValue = FloatValue("${valuePrefix}Motion", 0.5f, 0.1f, 1f)
    override fun onMove(event: MoveEvent) {
        if (!mc.thePlayer.isInWater) {
            return
        }

        event.y = aacMotionValue.get().toDouble()
        mc.thePlayer.motionY = aacMotionValue.get().toDouble()
    }
}
