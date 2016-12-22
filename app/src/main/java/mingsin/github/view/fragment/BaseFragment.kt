package mingsin.github.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mingsin.github.App
import mingsin.github.R
import mingsin.github.databinding.FragmentBaseBinding
import mingsin.github.databinding.FragmentTrendingBinding
import mingsin.github.di.ActivityComponent
import mingsin.github.di.ActivityModule
import mingsin.github.di.DaggerActivityComponent
import mingsin.github.view.activity.BaseActivity
import rx.subscriptions.CompositeSubscription

/**
 * Created by trevorwang on 17/12/2016.
 */
abstract class BaseFragment : Fragment() {
    lateinit var activityComponent: ActivityComponent
    private lateinit var binding: FragmentBaseBinding
    protected var subscriptions = CompositeSubscription()
    protected var dataInited = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = activity.application as App
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(app.component)
                .activityModule(ActivityModule(activity as BaseActivity)).build()
        onInject()
    }

    final override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentBaseBinding>(inflater, R.layout.fragment_base, container, false)
        val v = onCreateContentView(inflater, binding.container, savedInstanceState)
        if (v != null) {
            binding.container.addView(v, 0)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }

    override fun onResume() {
        super.onResume()
        showLoadingView()
    }

    /**
     * what you have to do is to invoke <code>activityComponent#inject/code>
     */
    abstract fun onInject()

    abstract fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?

    fun showLoadingView() {
        binding.loading = !dataInited
    }

    fun hideLoadingView() {
        binding.loading = false
    }
}