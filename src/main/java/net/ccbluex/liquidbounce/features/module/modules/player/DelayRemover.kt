/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.player

import me.zywl.fdpclient.event.EventTarget
import me.zywl.fdpclient.event.MotionEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MovementUtils

import me.zywl.fdpclient.value.impl.BoolValue
import me.zywl.fdpclient.value.impl.IntegerValue

@ModuleInfo(name = "DelayRemover", category = ModuleCategory.PLAYER, defaultOn = true)
object DelayRemover : Module() {

    val jumpDelay = BoolValue("NoJumpDelay", false)
    val jumpDelayTicks = IntegerValue("JumpDelayTicks", 0, 0, 4).displayable { jumpDelay.get() }

    val noClickDelay = BoolValue("NoClickDelay", true)

    val blockBreakDelay = BoolValue("NoBlockHitDelay", false)

    val noSlowBreak = BoolValue("NoSlowBreak", false)
    val airValue = BoolValue("Air", true).displayable { noSlowBreak.get() }
    val waterValue = BoolValue("Water", false).displayable { noSlowBreak.get() }

    val exitGuiValue = BoolValue("NoExitGuiDelay", true)

    private var prevGui = false

    @EventTarget
    fun onMotion(event: MotionEvent) {
        if (mc.thePlayer != null && mc.theWorld != null && noClickDelay.get()) {
            mc.leftClickCounter = 0
        }

        if (blockBreakDelay.get()) {
            mc.playerController.blockHitDelay = 0
        }

        if (mc.currentScreen == null && exitGuiValue.get()) {
            if (prevGui) MovementUtils.updateControls()
            prevGui = false
        } else {
            prevGui = true
        }
    }

}
