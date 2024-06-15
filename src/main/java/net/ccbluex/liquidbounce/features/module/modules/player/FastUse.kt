/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.player

import me.zywl.fdpclient.event.EventTarget
import me.zywl.fdpclient.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import me.zywl.fdpclient.value.impl.BoolValue
import me.zywl.fdpclient.value.impl.FloatValue
import me.zywl.fdpclient.value.impl.IntegerValue
import me.zywl.fdpclient.value.impl.ListValue
import net.minecraft.item.ItemBucketMilk
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemPotion
import net.minecraft.network.play.client.C03PacketPlayer

@ModuleInfo(name = "FastUse", category = ModuleCategory.PLAYER)
object FastUse : Module() {

    private val modeValue = ListValue("Mode", arrayOf("NCP", "NCP2", "Instant", "Timer", "CustomDelay", "DelayedInstant", "MinemoraTest", "AAC", "NewAAC","Medusa","Matrix","Fast", "Grim", "Test"), "DelayedInstant")
    private val timerValue = FloatValue("Timer", 1.22F, 0.1F, 2.0F).displayable { modeValue.equals("Timer") }
    private val durationValue = IntegerValue("InstantDelay", 14, 0, 35).displayable { modeValue.equals("DelayedInstant") }
    private val delayValue = IntegerValue("CustomDelay", 0, 0, 300).displayable { modeValue.equals("CustomDelay") }
    private val viaFixValue = BoolValue("ViaVersion", false)

    private val msTimer = MSTimer()
    private var usedTimer = false
    private var sentPacket = false
    private var lastState = false

    private fun send(int: Int) {
        repeat(int) {
            mc.netHandler.addToSendQueue(C03PacketPlayer(mc.thePlayer.onGround))
        }
    }

    private fun send() {
        mc.netHandler.addToSendQueue(C03PacketPlayer(mc.thePlayer.onGround))
    }
    
    private fun stopUsing() {
        if (viaFixValue.get()) {
            sentPacket = true
            if (mc.thePlayer.itemInUseCount < 10) {
                mc.thePlayer.itemInUseCount = 20
            }
        } else {
            sentPacket = false
            mc.playerController.onStoppedUsingItem(mc.thePlayer)
        }
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (usedTimer) {
            mc.timer.timerSpeed = 1F
            usedTimer = false
        }

        if (!mc.thePlayer.isUsingItem) {
            sentPacket = false
            lastState = mc.thePlayer.isUsingItem
            return
        } else if(!lastState) {
            sentPacket = false
        }
        
        lastState = mc.thePlayer.isUsingItem
        
        if (viaFixValue.get() && sentPacket) {
            if (mc.thePlayer.itemInUseCount < 10) {
                mc.thePlayer.itemInUseCount = 20
            }
            return
        }

        val usingItem = mc.thePlayer.itemInUse.item

        if (usingItem is ItemFood || usingItem is ItemBucketMilk || usingItem is ItemPotion) {
            when (modeValue.get().lowercase()) {
                "test" -> {
                    if (mc.thePlayer.itemInUseDuration > 28) {
                        stopUsing()
                    }
                }
                "matrix" -> {
                    mc.timer.timerSpeed = 0.5f
                    usedTimer = true
                    send()
                }
                "fast" -> {
                    if (mc.thePlayer.itemInUseDuration < 25) {
                        mc.timer.timerSpeed = 0.3f
                        usedTimer = true
                        send(5)
                    }
                }
                
                "medusa" -> {
                    if (mc.thePlayer.itemInUseDuration > 5 || !msTimer.hasTimePassed(360L))
                        return

                    send(20)

                    msTimer.reset()
                }
                "delayedinstant" -> if (mc.thePlayer.itemInUseDuration > durationValue.get()) {
                    send(35 - mc.thePlayer.itemInUseDuration)

                    stopUsing()
                }

                "ncp" -> if (mc.thePlayer.itemInUseDuration > 14) {
                    send(20)

                    stopUsing()
                }
                "ncp2" -> if (mc.thePlayer.itemInUseDuration > 14) {
                    repeat(20) {
                        mc.netHandler.addToSendQueue(C03PacketPlayer(mc.thePlayer.onGround))
                    }

                    mc.playerController.onStoppedUsingItem(mc.thePlayer)
                }
                "instant" -> {
                    send(35)

                    stopUsing()
                }
                "aac" -> {
                    mc.timer.timerSpeed = 0.49F
                    usedTimer = true
                    if (mc.thePlayer.itemInUseDuration > 14) {
                        send(23)
                    }
                }
                "grim" -> {
                    mc.timer.timerSpeed = 0.3F
                    usedTimer = true
                    repeat(34) {
                        mc.netHandler.addToSendQueue(C03PacketPlayer(mc.thePlayer.onGround))
                    }
                }
                "newaac" -> {
                    mc.timer.timerSpeed = 0.49F
                    usedTimer = true
                    send(2)
                }
                "timer" -> {
                    mc.timer.timerSpeed = timerValue.get()
                    usedTimer = true
                }

                "minemoratest" -> {
                    mc.timer.timerSpeed = 0.5F
                    usedTimer = true
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        send(2)
                    }
                }

                "customdelay" -> {
                    if (!msTimer.hasTimePassed(delayValue.get().toLong())) {
                        return
                    }

                    send()
                    msTimer.reset()
                }
            }
            if (mc.thePlayer.itemInUseDuration >= 30 && viaFixValue.get()) {
                sentPacket = true
                if (mc.thePlayer.itemInUseCount < 10) {
                    mc.thePlayer.itemInUseCount = 20
                }
            }
        }
    }

    // @EventTarget
    // fun onMove(event: MoveEvent?) {
    //     if (event == null) return

    //     if (!mc.thePlayer.isUsingItem || !modeValue.get().lowercase()=="aac") return
    //     val usingItem1 = mc.thePlayer.itemInUse.item
    //     if ((usingItem1 is ItemFood || usingItem1 is ItemBucketMilk || usingItem1 is ItemPotion))
    //         event.zero()
    // }

    override fun onDisable() {
        if (usedTimer) {
            mc.timer.timerSpeed = 1F
            usedTimer = false
        }
        sentPacket = false
        lastState = mc.thePlayer.isUsingItem
    }

    override val tag: String
        get() = modeValue.get()
}
