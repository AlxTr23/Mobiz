/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.vanilla

import me.zywl.fdpclient.event.PacketEvent
import me.zywl.fdpclient.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode
import me.zywl.fdpclient.value.impl.BoolValue
import me.zywl.fdpclient.value.impl.FloatValue
import net.minecraft.network.play.server.S12PacketEntityVelocity

class TickVelocity : VelocityMode("Tick") {
    private val tickReductionValue = FloatValue("${valuePrefix}TickReductionAmount", 1f, 0f, 1f)
    private val verticalValue = BoolValue("${valuePrefix}ResetMotionY", true)
    private val bypassValue = BoolValue("${valuePrefix}TickBypass", true)
    override fun onVelocity(event: UpdateEvent) {
        if(velocity.velocityTick > velocity.velocityTickValue.get()) {
            if(mc.thePlayer.motionY > 0 && verticalValue.get())
                mc.thePlayer.motionY = 0.0
            mc.thePlayer.motionX *= 1.0 - tickReductionValue.get().toDouble()
            mc.thePlayer.motionZ *= 1.0 - tickReductionValue.get().toDouble()
            if(bypassValue.get()) {
                mc.thePlayer.jumpMovementFactor = -0.001f
            }else {
                mc.thePlayer.jumpMovementFactor = 0.0f
            }
            velocity.velocityInput = false
        }
        if(mc.thePlayer.onGround && velocity.velocityTick > 1) {
            velocity.velocityInput = false
        }
    }

    override fun onVelocityPacket(event: PacketEvent) {
        val packet = event.packet
        if(packet is S12PacketEntityVelocity) {
            velocity.velocityInput = true
            val horizontal = velocity.horizontalValue.get()
            val vertical = velocity.verticalValue.get()

            if (horizontal == 0F && vertical == 0F) {
                event.cancelEvent()
            }

            packet.motionX = (packet.getMotionX() * horizontal).toInt()
            packet.motionY = (packet.getMotionY() * vertical).toInt()
            packet.motionZ = (packet.getMotionZ() * horizontal).toInt()
        }
    }
}
