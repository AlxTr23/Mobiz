/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.other

import me.zywl.fdpclient.FDPClient
import me.zywl.fdpclient.event.EventTarget
import me.zywl.fdpclient.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.hud.element.elements.Notification
import net.ccbluex.liquidbounce.ui.hud.element.elements.NotifyType
import me.zywl.fdpclient.value.impl.IntegerValue
import net.minecraft.network.play.server.S14PacketEntity
import net.minecraft.network.play.server.S1DPacketEntityEffect

@ModuleInfo(name = "AntiVanish", category = ModuleCategory.OTHER)
object AntiVanish : Module() {

    private var lastNotify=-1L

    private val notifyLast = IntegerValue("Notification-Seconds", 2, 1, 30)

    @EventTarget
    fun onPacket(event: PacketEvent){
        if (mc.theWorld == null || mc.thePlayer == null) return
        if(event.packet is S1DPacketEntityEffect){
            if(mc.theWorld.getEntityByID(event.packet.entityId)==null){
                vanish()
            }
        }else if(event.packet is S14PacketEntity){
            if(event.packet.getEntity(mc.theWorld)==null){
                vanish()
            }
        }
    }

    private fun vanish() {
        if((System.currentTimeMillis()-lastNotify)>5000){
            FDPClient.hud.addNotification(Notification("Found a vanished entity!", "someone just vanished!", NotifyType.WARNING, notifyLast.get() * 1000))

        }
        lastNotify=System.currentTimeMillis()

    }
}
