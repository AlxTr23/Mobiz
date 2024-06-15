/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.client

import me.zywl.fdpclient.event.EventTarget
import me.zywl.fdpclient.event.Render3DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.RenderWings
import me.zywl.fdpclient.value.impl.BoolValue
import me.zywl.fdpclient.value.impl.IntegerValue
import me.zywl.fdpclient.value.impl.ListValue

@ModuleInfo(name = "Wings", category = ModuleCategory.CLIENT, array = false)
object Wings : Module() {
    private val onlyThirdPerson = BoolValue("OnlyThirdPerson", true)
    val ColourType = ListValue("Color Type", arrayOf("Custom", "Theme", "None"), "Chroma")
    val CR = IntegerValue("R", 255, 0, 255).displayable { ColourType.get() == "Custom" }
    val CG = IntegerValue("G", 255, 0, 255).displayable { ColourType.get() == "Custom" }
    val CB = IntegerValue("B", 255, 0, 255).displayable { ColourType.get() == "Custom" }
    var wingStyle = ListValue("WingStyle", arrayOf("Dragon", "Simple"),"Dragon")

    @EventTarget
    fun onRenderPlayer(event: Render3DEvent) {
        if (onlyThirdPerson.get() && mc.gameSettings.thirdPersonView == 0) return
        val renderWings = RenderWings()
        renderWings.renderWings(event.partialTicks)
    }

}

