/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.client

import me.zywl.fdpclient.FDPClient
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import me.zywl.fdpclient.value.impl.BoolValue
import me.zywl.fdpclient.value.impl.ListValue
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.util.ResourceLocation

@ModuleInfo(name = "SoundModules", category = ModuleCategory.CLIENT, canEnable = false)
object SoundModule : Module() {
    val toggleIgnoreScreenValue = BoolValue("ToggleIgnoreScreen", false)
    private val toggleSoundValue = ListValue("ToggleSound", arrayOf("None", "Click", "Custom"), "Click")

    fun playSound(enable: Boolean) {
        when (toggleSoundValue.get().lowercase()) {
            "click" -> {
                mc.soundHandler.playSound(PositionedSoundRecord.create(ResourceLocation("random.click"), 1F))
            }

            "custom" -> {
                if (enable) {
                    FDPClient.tipSoundManager.enableSound.asyncPlay()
                } else {
                    FDPClient.tipSoundManager.disableSound.asyncPlay()
                }
            }
        }
    }
}