package mingsin.github.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import mingsin.github.R
import mingsin.github.data.GithubApiService
import mingsin.github.databinding.FragmentUserBinding
import mingsin.github.databinding.ItemUserBinding
import mingsin.github.model.User
import mingsin.github.view.ItemHolder
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by trevorwang on 25/12/2016.
 */

class UserFragment : BaseFragment() {
    @Inject lateinit var api: GithubApiService
    @Inject lateinit var adapter: UserAdapter
    private lateinit var binding: FragmentUserBinding
    override fun onInject() {
        activityComponent.inject(this)
    }

    override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        binding.rvUsers.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        api.users().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Logger.d(it)
                    hideLoadingView()
                    adapter.users = it
                }
    }


    class UserAdapter @Inject constructor(val inflater: LayoutInflater) : RecyclerView.Adapter<ItemHolder<ItemUserBinding>>() {
        var users: List<User> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onBindViewHolder(holder: ItemHolder<ItemUserBinding>?, position: Int) {
            holder?.binding?.user = users[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder<ItemUserBinding> {
            val itemBinding = ItemUserBinding.inflate(inflater, parent, false)
            return ItemHolder(itemBinding)
        }

        override fun getItemCount(): Int {
            return users.size
        }

    }
}