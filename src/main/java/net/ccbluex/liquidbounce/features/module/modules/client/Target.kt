/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.client

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import me.zywl.fdpclient.value.impl.BoolValue

@ModuleInfo(name = "Target", category = ModuleCategory.CLIENT, canEnable = false)
object Target : Module() {
    val playerValue = BoolValue("Player", true)
    val animalValue = BoolValue("Animal", true)
    val mobValue = BoolValue("Mob", true)
    val invisibleValue = BoolValue("Invisible", false)
    val deadValue = BoolValue("Dead", false)
    override fun handleEvents() = true
}