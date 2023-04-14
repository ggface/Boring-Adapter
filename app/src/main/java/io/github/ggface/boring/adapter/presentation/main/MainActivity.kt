package io.github.ggface.boring.adapter.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.ggface.boring.adapter.databinding.ActivityMainBinding
import io.github.ggface.boring.adapter.presentation.adapter.legacy.RendererRecyclerAdapter
import io.github.ggface.boring.adapter.presentation.adapter.legacy.SimpleDiffCallback

/**
 * Экран для вывода списка идентификаторов.
 *
 * @author Ivan Novikov on 2023-04-14.
 */
class MainActivity : AppCompatActivity() {

    private val topAdapter = RendererRecyclerAdapter()
    private val topDiffCallback = SimpleDiffCallback()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initTopList()
    }

    private fun initTopList() {
        topAdapter.registerRenderer(
            ExampleLegacyRendererView { productId ->
                highlightText(productId)
            }
        )
        binding.topRecyclerView.adapter = topAdapter

        val modelsList = (0..9).map {
            ExampleLegacyRendererView.Model(it.toString())
        }
        topAdapter.setItems(modelsList, topDiffCallback)
    }

    private fun highlightText(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}