package mingsin.github.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import mingsin.github.App
import mingsin.github.di.ActivityComponent
import mingsin.github.di.ActivityModule
import mingsin.github.di.DaggerActivityComponent
import mingsin.github.view.activity.BaseActivity

/**
 * Created by trevorwang on 17/12/2016.
 */
abstract class BaseFragment : Fragment() {
    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = activity.application as App
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(app.component)
                .activityModule(ActivityModule(activity as BaseActivity)).build()
        onInject()
    }

    /**
     * what you have to do is to invoke <code>activityComponent#inject/code>
     */
    abstract fun onInject()
}