/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.aac

import me.zywl.fdpclient.FDPClient
import me.zywl.fdpclient.event.JumpEvent
import me.zywl.fdpclient.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed
import me.zywl.fdpclient.value.impl.BoolValue
import me.zywl.fdpclient.value.impl.FloatValue

class AACPushVelocity : VelocityMode("AACPush") {
    private val aacPushXZReducerValue = FloatValue("AACPushXZReducer", 2F, 1F, 3F)
    private val aacPushYReducerValue = BoolValue("AACPushYReducer", true)
    private var jump = false
    override fun onEnable() {
        jump = false
    }

    override fun onVelocity(event: UpdateEvent) {
        if (jump) {
            if (mc.thePlayer.onGround) {
                jump = false
            }
        } else {
            // Strafe
            if (mc.thePlayer.hurtTime > 0 && mc.thePlayer.motionX != 0.0 && mc.thePlayer.motionZ != 0.0) {
                mc.thePlayer.onGround = true
            }

            // Reduce Y
            if (mc.thePlayer.hurtResistantTime > 0 && aacPushYReducerValue.get() &&
                !FDPClient.moduleManager[Speed::class.java]!!.state) {
                mc.thePlayer.motionY -= 0.014999993
            }
        }

        // Reduce XZ
        if (mc.thePlayer.hurtResistantTime >= 19) {
            val reduce = aacPushXZReducerValue.get()

            mc.thePlayer.motionX /= reduce
            mc.thePlayer.motionZ /= reduce
        }
    }

    override fun onJump(event: JumpEvent) {
        if (mc.thePlayer.isInWater || mc.thePlayer.isInLava || mc.thePlayer.isInWeb || (velocity.onlyGroundValue.get() && !mc.thePlayer.onGround)) {
            return
        }

        if ((velocity.onlyGroundValue.get() && !mc.thePlayer.onGround) || (velocity.onlyCombatValue.get() && !FDPClient.combatManager.inCombat)) {
            return
        }

        jump = true

        if (!mc.thePlayer.isCollidedVertically) {
            event.cancelEvent()
        }
    }
}