package com.test.fragmenttest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.fragmenttest.R

class FragmentChild2: Fragment() {

    companion object {
        fun newInstance(): FragmentChild2{
            return FragmentChild2()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_child_2, container, false)
    }

}