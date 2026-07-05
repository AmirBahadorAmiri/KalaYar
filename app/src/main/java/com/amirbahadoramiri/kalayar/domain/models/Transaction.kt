package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.persian.PersianCalendar
import java.util.Date


@Entity(tableName = "transaction")
data class Transaction(
    var transaction_type: Byte,
    var transaction_title: String, // String (32)
    var transaction_create_time: Long,
    var transaction_description: String, // String (256)

    @PrimaryKey(autoGenerate = true)
    var transaction_id: Long? = null,

) {

    @Ignore
    fun getTransactionPersianDate() : String {
        val calendar: PrimeCalendar = PersianCalendar()
        calendar.setTime(Date(transaction_create_time))
        return "${calendar.shortDateString}  ${calendar.hour}:${calendar.minute}:${calendar.second}\n${calendar.weekDayName}"
    }

}