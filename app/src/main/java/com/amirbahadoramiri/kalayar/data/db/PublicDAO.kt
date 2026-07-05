package com.amirbahadoramiri.kalayar.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amirbahadoramiri.kalayar.domain.models.Product
import com.amirbahadoramiri.kalayar.domain.models.Store
import com.amirbahadoramiri.kalayar.domain.models.Transaction
import com.amirbahadoramiri.kalayar.domain.models.TransactionItem

@Dao
interface PublicDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addStore(store: Store): Long?
    @Delete suspend fun deleteStore(store: Store)
    @Update(onConflict = OnConflictStrategy.REPLACE) suspend fun updateStore(store: Store)
    @Query("SELECT * FROM store limit 1") suspend fun getStore(): Store?


    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addProduct(product: Product): Long
    @Delete suspend fun deleteProduct(product: Product)
    @Update(onConflict = OnConflictStrategy.REPLACE) suspend fun updateProduct(product: Product)
    @Update(onConflict = OnConflictStrategy.REPLACE) suspend fun updateProducts(products: List<Product>)
    @Query("SELECT * FROM product WHERE product_id = :id") suspend fun getProduct(id: Long): Product?
    @Query("SELECT * FROM product") suspend fun getAllProduct(): List<Product>


    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addTransaction(transaction: Transaction): Long?
    @Delete suspend fun deleteTransaction(transaction: Transaction)
    @Update(onConflict = OnConflictStrategy.REPLACE) suspend fun updateTransaction(transaction: Transaction)
    @Query("SELECT * FROM `transaction` LIMIT :limit_count") suspend fun getAllTransaction(limit_count: Int): List<Transaction>

    @Query("SELECT * FROM `transaction`") suspend fun getAllTransaction(): List<Transaction>
    @Query("SELECT * FROM `transaction` WHERE transaction_id=:id") suspend fun getTransaction(id: Long): Transaction?


    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addTransactionItem(transactionItem: TransactionItem): Long?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addTransactionItems(transactionItems: List<TransactionItem>)
    @Delete suspend fun deleteTransactionItem(transactionItem: TransactionItem)
    @Delete suspend fun deleteTransactionItems(transactionItems: List<TransactionItem>)
    @Query("DELETE FROM transaction_item WHERE transaction_id = :transaction_id") suspend fun deleteTransactionItems(transaction_id: Long)
    @Update(onConflict = OnConflictStrategy.REPLACE) suspend fun updateTransactionItem(transactionItem: TransactionItem)
    @Update(onConflict = OnConflictStrategy.REPLACE) suspend fun updateTransactionItems(transactionItems: List<TransactionItem>)
    @Query("SELECT * FROM transaction_item WHERE item_id = :item_id") suspend fun getTransactionItem(item_id: Long): TransactionItem?
    @Query("SELECT * FROM transaction_item WHERE transaction_id = :transaction_id") suspend fun getTransactionItems(transaction_id: Long): List<TransactionItem>
    @Query("SELECT * FROM transaction_item") suspend fun getAllTransactionItems(): List<TransactionItem>

}
