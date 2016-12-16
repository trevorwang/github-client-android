package mingsin.github.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import mingsin.github.App
import mingsin.github.data.RestApi
import mingsin.github.di.ActivityComponent
import mingsin.github.di.ActivityModule
import mingsin.github.di.DaggerActivityComponent
import javax.inject.Inject

/**
 * Created by trevorwang on 10/12/2016.
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var activityComponent: ActivityComponent

    @Inject lateinit var mRestApi: RestApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as? App
        if (app != null) {
            activityComponent = DaggerActivityComponent.builder()
                    .appComponent(app.component)
                    .activityModule(ActivityModule(this)).build()
            onInject()
        }
    }

    /**
     * what you have to do is to invoke <code>activityComponent#inject/code>
     */
    abstract fun onInject()

}