package com.amirbahadoramiri.kalayar.tools.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.amirbahadoramiri.kalayar.core.models.Product
import com.amirbahadoramiri.kalayar.core.models.Store
import com.amirbahadoramiri.kalayar.core.models.Transaction
import com.amirbahadoramiri.kalayar.core.models.TransactionItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PublicDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStore(store: Store): Completable

    @Delete
    fun deleteStore(store: Store): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStore(store: Store): Completable

    @Query("SELECT * FROM store limit 1")
    fun getStore(): Single<Store>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product: Product): Single<Long>

    @Delete
    fun deleteProduct(product: Product): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProduct(product: Product): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProducts(products: List<Product>): Completable

    @Query("SELECT * FROM product WHERE product_name LIKE '%' || :product_name  || '%'")
    fun searchProduct(product_name: String): Single<List<Product>>

    @Query("SELECT * FROM product")
    fun getAllProduct(): Single<List<Product>>

    @Query("SELECT * FROM product WHERE product_count > :productCount")
    fun getAllProduct(productCount: Long): Single<List<Product>>

    @RawQuery
    fun getAllProduct(query: SupportSQLiteQuery): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransaction(transaction: Transaction): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransactions(transactions: List<Transaction>): Completable

    @Delete
    fun deleteTransaction(transaction: Transaction): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(transaction: Transaction): Completable

    @Query("SELECT * FROM `transaction` WHERE transaction_title LIKE '%' || :transaction_title  || '%'")
    fun searchTransactionTitle(transaction_title: String): Single<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE transaction_type = :transaction_type")
    fun searchTransactionType(transaction_type: Byte): Single<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE :from < transaction_create_time < :until")
    fun searchTransactionTime(from: Long,until: Long): Single<List<Transaction>>

    @Query("SELECT * FROM `transaction`")
    fun getAllTransaction(): Single<List<Transaction>>




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransactionItem(transactionItem: TransactionItem): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransactionItems(transactionItems: List<TransactionItem>): Completable

    @Delete
    fun deleteTransactionItem(transactionItem: TransactionItem): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransactionItem(transactionItem: TransactionItem): Completable

    @Query("SELECT * FROM transaction_item WHERE transaction_id = :transaction_id")
    fun searchTransactionItem(transaction_id: Long): Single<List<TransactionItem>>

    @Query("SELECT * FROM transaction_item")
    fun getAllTransactionItems(): Single<List<TransactionItem>>

}
