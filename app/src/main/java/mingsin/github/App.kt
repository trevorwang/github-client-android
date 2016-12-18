package mingsin.github

import android.app.Application
import mingsin.github.di.AppComponent
import mingsin.github.di.AppModule
import mingsin.github.di.DaggerAppComponent

/**
 * Created by trevorwang on 10/12/2016.
 */
class App : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = initComp()
    }

    fun initComp(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}