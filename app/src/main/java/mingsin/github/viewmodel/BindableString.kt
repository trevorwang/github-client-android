package mingsin.github.viewmodel

import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.orhanobut.logger.Logger

/**
 * Created by trevorwang on 13/12/2016.
 */
class BindableString : BaseObservable() {
    var value: String = ""
        set(v) {
            if (v != value) {
                field = v
                notifyChange()
            }
        }
}

@BindingConversion
fun convertBindableToString(bindableString: BindableString): String {
    return bindableString.value
}

@BindingAdapter("app:binding")
fun bindEditText(view: EditText, bindableString: BindableString) {
    if (view.getTag(0x1111111) == null) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                bindableString.value = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        if (view.text.toString() != bindableString.value) {
            view.setText(bindableString.value)
        }
    }
}


@BindingAdapter("android:textColor")
fun bindTextColor(view: TextView, color: String?) {
    Logger.v(color)
    if (color != null) {
        val colorValue = Color.parseColor(color)
        view.setTextColor(colorValue)
    }
}