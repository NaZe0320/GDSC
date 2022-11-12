package com.test.fragmenttest.fragment

import androidx.fragment.app.Fragment

open class ContainerFragment : Fragment() {

    private var mFragmentID = -1

    fun changeFragment(fragment: Fragment, id: Int, tag: String? = null) {
        mFragmentID = id

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(id,fragment,tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}