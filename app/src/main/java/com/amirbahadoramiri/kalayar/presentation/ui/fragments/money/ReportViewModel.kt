package com.amirbahadoramiri.kalayar.presentation.ui.fragments.money

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomProductDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionItemDataSource
import com.amirbahadoramiri.kalayar.domain.models.ReportData
import com.amirbahadoramiri.kalayar.domain.models.TransactionType
import com.amirbahadoramiri.kalayar.domain.repository.product.ProductRepository
import com.amirbahadoramiri.kalayar.domain.repository.transaction.TransactionRepository
import com.amirbahadoramiri.kalayar.domain.repository.transactionitem.TransactionItemRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepository(RoomProductDataSource(application))
    private val transactionRepository = TransactionRepository(RoomTransactionDataSource(application))
    private val transactionItemRepository = TransactionItemRepository(RoomTransactionItemDataSource(application))

    val reportLiveData = MutableLiveData<ReportData>()

    fun calculateReport() {
        viewModelScope.launch {
            val productsDeferred = async { productRepository.getAllProducts() }
            val transactionsDeferred = async { transactionRepository.getAllTransactions() }
            val allItemsDeferred = async { transactionItemRepository.getAllItems() }

            val products = productsDeferred.await()
            val transactions = transactionsDeferred.await()
            val allItems = allItemsDeferred.await()

            // 1. Inventory Calculations
            var totalInventoryValue: Long = 0
            var totalProductsCount: Long = 0
            products.forEach {
                totalInventoryValue += (it.product_price * it.product_count)
                totalProductsCount += it.product_count
            }

            // 2. Sales Calculations
            val now = System.currentTimeMillis()
            val cal = Calendar.getInstance()

            fun getStartTime(monthsAgo: Int = 0, isToday: Boolean = false): Long {
                val c = Calendar.getInstance()
                if (isToday) {
                    c.set(Calendar.HOUR_OF_DAY, 0)
                    c.set(Calendar.MINUTE, 0)
                    c.set(Calendar.SECOND, 0)
                    c.set(Calendar.MILLISECOND, 0)
                } else {
                    c.add(Calendar.MONTH, -monthsAgo)
                }
                return c.timeInMillis
            }

            val todayStart = getStartTime(isToday = true)
            val oneMonthStart = getStartTime(1)
            val threeMonthsStart = getStartTime(3)
            val sixMonthsStart = getStartTime(6)
            val oneYearStart = getStartTime(12)

            var salesToday = 0L
            var sales1Month = 0L
            var sales3Months = 0L
            var sales6Months = 0L
            var sales1Year = 0L
            var totalSales = 0L

            // Map transaction ID to its items
            val transactionItemsMap = allItems.groupBy { it.transaction_id }

            transactions.filter { it.transaction_type == TransactionType.DECREASE }.forEach { trans ->
                val transItems = transactionItemsMap[trans.transaction_id] ?: emptyList()
                var transTotal = 0L
                transItems.forEach { transTotal += (it.product_price * it.change_amount) }

                val time = trans.transaction_create_time
                if (time >= todayStart) salesToday += transTotal
                if (time >= oneMonthStart) sales1Month += transTotal
                if (time >= threeMonthsStart) sales3Months += transTotal
                if (time >= sixMonthsStart) sales6Months += transTotal
                if (time >= oneYearStart) sales1Year += transTotal
                totalSales += transTotal
            }

            reportLiveData.postValue(
                ReportData(
                    totalInventoryValue = totalInventoryValue,
                    totalProductsCount = totalProductsCount,
                    productTypesCount = products.size,
                    salesToday = salesToday,
                    sales1Month = sales1Month,
                    sales3Months = sales3Months,
                    sales6Months = sales6Months,
                    sales1Year = sales1Year,
                    totalSales = totalSales
                )
            )
        }
    }
}