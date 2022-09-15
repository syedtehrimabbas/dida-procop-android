package com.androidstarter.data.cart.dao

import androidx.room.*
import com.androidstarter.data.cart.models.CartProduct
import com.androidstarter.data.cart.models.FavouriteProduct

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

    @Query("SELECT * from FavouriteProductTable")
    suspend fun getFavProducts(): MutableList<FavouriteProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(favouriteProduct: FavouriteProduct)

    @Query("SELECT * FROM FavouriteProductTable WHERE product_id==:id")
    fun getFavProductById(id: Int): FavouriteProduct?

    @Query("DELETE from FavouriteProductTable WHERE product_id = :id")
    fun deleteProductFromFav(id: Int)

    @Query("SELECT COUNT(product_id) from FavouriteProductTable")
    suspend fun favCount(): Int

    @Query("DELETE FROM FavouriteProductTable")
    suspend fun truncateFavProductTable()
}