/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement

import me.zywl.fdpclient.event.EventTarget
import me.zywl.fdpclient.event.PacketEvent
import me.zywl.fdpclient.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import me.zywl.fdpclient.value.impl.BoolValue
import net.minecraft.network.play.client.C03PacketPlayer

@ModuleInfo(name = "AirJump", category = ModuleCategory.MOVEMENT)
object AirJump : Module() {

    private val spoofGroundValue = BoolValue("SpoofGround", false)
    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        mc.thePlayer.onGround = true
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val packet = event.packet
        if(packet is C03PacketPlayer) {
            if(spoofGroundValue.get()) {
                packet.onGround = true
            }
        }
    }
}
