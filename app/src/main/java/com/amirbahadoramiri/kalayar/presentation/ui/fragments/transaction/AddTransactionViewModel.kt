package com.amirbahadoramiri.kalayar.presentation.ui.fragments.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirbahadoramiri.kalayar.data.repository.RoomProductDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionDataSource
import com.amirbahadoramiri.kalayar.data.repository.RoomTransactionItemDataSource
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem
import com.amirbahadoramiri.kalayar.domain.repository.product.ProductRepository
import com.amirbahadoramiri.kalayar.domain.repository.transaction.TransactionRepository
import com.amirbahadoramiri.kalayar.domain.repository.transactionitem.TransactionItemRepository
import kotlinx.coroutines.launch

class AddTransactionViewModel(application: Application) : AndroidViewModel(application) {

    val productRepository = ProductRepository(RoomProductDataSource(application))
    val transactionRepository = TransactionRepository(RoomTransactionDataSource(application))
    val transactionItemRepository = TransactionItemRepository(RoomTransactionItemDataSource(application))

    val transactionAddLiveData = MutableLiveData<Boolean>()
    val getAllProductLiveData = MutableLiveData<List<Product>>()
    val allProductShownLiveData = MutableLiveData<MutableList<Product>>()

    fun addTransaction(transaction: Transaction, listProducts: MutableList<Product>) {
        viewModelScope.launch {
            transactionRepository.addTransaction(transaction).let {
                transaction.transaction_id = it

                val transactionItems = mutableListOf<TransactionItem>()

                for (productItem in listProducts) {
                    val transactionItem = TransactionItem(productItem.product_id,productItem.product_name,productItem.product_unit,productItem.product_price,productItem.product_count,productItem.change_amount,productItem.final_value, transaction_id = transaction.transaction_id)
                    transactionItems.add(transactionItem)
                    productItem.product_count = productItem.final_value
                }
                transactionItemRepository.addItems(transactionItems).let {
                    productRepository.updateProducts(listProducts).let {
                        transactionAddLiveData.postValue(true)
                    }
                }
            }
        }
    }

    fun getAllProduct() {
        viewModelScope.launch {
            productRepository.getAllProducts().let {
                getAllProductLiveData.postValue(it.asReversed())
            }
        }
    }

}