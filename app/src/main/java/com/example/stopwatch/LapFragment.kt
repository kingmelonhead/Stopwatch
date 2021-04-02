package com.example.stopwatch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lap_fragment.*


class LapFragment : Fragment() {


    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>? = null

    private lateinit var viewModel: MainActivityViewModel


    companion object {
        fun newInstance() = LapFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.lap_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        viewModel.lapList.observe(this, Observer {
            Log.e(null, "inside observer")
            recycler_view.apply {
                Log.e(null, "size of it: ${it.size}")
                if (it.size == 0){
                    Log.e(null, "attempting to set adapter")
                    adapter?.notifyDataSetChanged()
                    adapter = RecyclerAdapter(it)
                    setHasFixedSize(true)
                }
                else {
                    Log.e(null, "attempting to update data set")
                    adapter?.notifyDataSetChanged()
                }


            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


}