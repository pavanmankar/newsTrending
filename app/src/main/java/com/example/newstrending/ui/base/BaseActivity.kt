package com.example.newstrending.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.di.component.ActivityComponent
import com.example.newstrending.di.component.DaggerActivityComponent
import com.example.newstrending.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity <T : BaseViewModel<*>, ViewBindingType : ViewBinding> : AppCompatActivity() {


    @Inject
    lateinit var viewModel: T

    private var _binding: ViewBindingType? = null

    protected val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        _binding = setupViewBinding(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setupView(savedInstanceState)
        setupObserver()
    }

    fun setUpToolbar(title:String){
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    protected open fun setupObserver() {
    }

    private fun buildActivityComponent(): ActivityComponent = DaggerActivityComponent.builder()
        .applicationComponent((application as NewsTrendingApplication).applicationComponent)
        .activityModule(ActivityModule(this)).build()


    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    abstract fun setupView(savedInstanceState: Bundle?)

    abstract fun setupViewBinding(inflater: LayoutInflater): ViewBindingType

    override fun onDestroy() {
        _binding = null
        super.onDestroy()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}