package com.test.fragmenttest.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.test.fragmenttest.R
import com.test.fragmenttest.databinding.FragmentParentBinding

class FragmentParent: Fragment() {

    private lateinit var callback: OnBackPressedCallback

    private lateinit var binding: FragmentParentBinding

    fun newInstance(): FragmentParent{
        return FragmentParent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_parent, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_parent,container,false)

        binding.btn1Fp.setOnClickListener {
            val fg: FragmentChild1 = FragmentChild1.newInstance()
            Log.d("ToChild","1 - $fg")
            setChildFragment(fg)
        }

        binding.btn2Fp.setOnClickListener {
            val fg: FragmentChild2 = FragmentChild2.newInstance()
            Log.d("ToChild","2 - $fg")
            setChildFragment(fg)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setChildFragment(child: Fragment) {
        val ft: FragmentTransaction = childFragmentManager.beginTransaction()

        if (ft.isEmpty) {
            Log.d("setChildFragment","${ft.isEmpty}")
            ft.add(R.id.fp_fragment,child)
                .addToBackStack(null)
                .commit()
        } else {
            Log.d("setChildFragment","Empty ${ft.isEmpty}")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("BackPress","BackPress")
                // 스택이 있으면 스택을 지우고
                // 스택이 없으면 종료
          }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}