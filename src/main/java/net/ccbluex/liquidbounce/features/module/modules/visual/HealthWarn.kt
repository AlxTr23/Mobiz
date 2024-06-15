/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.visual

import me.zywl.fdpclient.FDPClient
import me.zywl.fdpclient.event.EventTarget
import me.zywl.fdpclient.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.hud.element.elements.Notification
import net.ccbluex.liquidbounce.ui.hud.element.elements.NotifyType
import me.zywl.fdpclient.value.impl.IntegerValue

@ModuleInfo(name = "HealthWarn", category = ModuleCategory.VISUAL, array = false)
object HealthWarn : Module() {

    private val healthValue = IntegerValue("Health", 7, 1, 20)

    private var canWarn = true

    override fun onEnable() {
        canWarn = true
    }

    override fun onDisable() {
        canWarn = true
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (mc.thePlayer.health <= healthValue.get()) {
            if (canWarn) {
                FDPClient.hud.addNotification(
                    Notification("HP Warning", "YOU ARE AT LOW HP!", NotifyType.ERROR, 3000))
                canWarn = false
            }
        } else {
            canWarn = true
        }
    }
}
