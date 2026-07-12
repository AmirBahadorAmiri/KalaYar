package com.amirbahadoramiri.kalayar.domain.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.amirbahadoramiri.kalayar.tools.text_utils.TextUtils

@Entity(tableName = "product")
data class Product(
    var product_name: String,   // String (64)
    var product_unit: String,   // String (32)
    var product_price: Long,    // max: 9,223,372,036,854,775,808  /* max 12 */
    var product_count: Long,    // max: 9,223,372,036,854,775,808  /* max 9  */

    @PrimaryKey(autoGenerate = true)
    var product_id: Long? = null,
) {

    @Ignore
    fun getProductPrice() = product_price.toString()

    @Ignore
    fun getProductCount() = product_count.toString()

    @Ignore
    fun formatMoney(): String {
        return TextUtils.numberFormat(product_price)
    }

    @Ignore
    var change_amount : Long = 0

    @Ignore
    var final_value : Long = 0

}