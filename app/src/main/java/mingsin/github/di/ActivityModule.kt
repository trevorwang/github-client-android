package mingsin.github.di

import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import mingsin.github.view.activity.BaseActivity

/**
 * Created by trevorwang on 10/12/2016.
 */
@Module
class ActivityModule(val activity: BaseActivity) {
    @Provides
    fun activity(): BaseActivity {
        return activity
    }

    @Provides
    fun inflater(): LayoutInflater {
        return LayoutInflater.from(activity)
    }
}