package com.amirbahadoramiri.kalayar.tools.timer

class Timer {

    companion object {

        val day : Long = 86_400_000 // 1 day
        val week : Long = 604_800_000 // 7 day
        val month : Long = 2_592_000_000 // 30 day
        val three_month : Long = 7_776_000_000 // 90 day
        val six_month : Long = 15_552_000_000 // 180 day
        val year : Long = 31_536_000_000 // 365 day

        fun until(from: Long,to: Long) = to-from
        fun until_to(to: Long) = to-System.currentTimeMillis()
        fun until_from(from: Long) = System.currentTimeMillis()-from

    }

}