package com.androidstarter.data.cart.dao

import androidx.room.*
import com.androidstarter.data.cart.models.CartProduct

@Dao
interface CartProductDao {
    @Query("SELECT * from Product")
    suspend fun getCartProducts(): MutableList<CartProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductToCart(product: CartProduct)

    @Query("DELETE from Product WHERE product_id = :id")
    fun deleteProductFromCart(id: Int)

    @Update
    suspend fun updateProductFromCart(product: CartProduct)

    @Query("SELECT * FROM Product WHERE product_id==:id")
    fun getProductById(id: Int): CartProduct?

    @Query("SELECT COUNT(product_id) from Product")
    suspend fun cartCount(): Int

    @Query("DELETE FROM Product")
    suspend fun truncateProductTable()
}