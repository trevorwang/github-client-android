package mingsin.github.di

import dagger.Component
import mingsin.github.MainActivity

/**
 * Created by trevorwang on 10/12/2016.
 */
@ForActivity
@Component(modules = arrayOf(ActivityModule::class), dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
}