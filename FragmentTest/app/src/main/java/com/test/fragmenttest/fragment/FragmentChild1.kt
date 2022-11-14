package com.test.fragmenttest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.fragmenttest.R

class FragmentChild1: Fragment() {


    companion object {
        fun newInstance(): FragmentChild1 {
            return FragmentChild1()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_child_1, container, false)
    }

}