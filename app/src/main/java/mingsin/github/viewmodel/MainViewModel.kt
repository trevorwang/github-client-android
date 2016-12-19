package mingsin.github.viewmodel

import android.content.Context
import android.databinding.adapters.TextViewBindingAdapter
import android.view.View
import mingsin.github.extension.toast
import javax.inject.Inject

/**
 * Created by trevorwang on 12/12/2016.
 */
class MainViewModel @Inject constructor(val context: Context) : ViewModel<String>() {
    val text = BindableString()
    val onClick = View.OnClickListener { v ->
        context.toast(text.value)
    }
    val textChange = TextViewBindingAdapter.AfterTextChanged { println(it.toString()) }
}
