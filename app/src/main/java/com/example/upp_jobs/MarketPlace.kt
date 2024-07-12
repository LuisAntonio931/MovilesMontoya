package com.example.upp_jobs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarketPlace : AppCompatActivity() {

    private lateinit var marketplaceRecyclerView: RecyclerView
    private lateinit var marketplaceAdapter: MarketplaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        // Configurar RecyclerView para productos
        marketplaceRecyclerView = findViewById(R.id.marketplaceRecyclerView)
        marketplaceRecyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar adaptador con una lista de productos de ejemplo
        val items = listOf(
            MarketplaceItem("Producto 1", "Descripción del producto 1"),
            MarketplaceItem("Producto 2", "Descripción del producto 2")
        )
        marketplaceAdapter = MarketplaceAdapter(items)
        marketplaceRecyclerView.adapter = marketplaceAdapter

        // Puedes reemplazar la lista de productos con una llamada a un API para obtener los productos reales
        fetchProducts()
    }

    private fun fetchProducts() {
        // Aquí iría el código para obtener los productos y actualizar el adaptador
    }
}
