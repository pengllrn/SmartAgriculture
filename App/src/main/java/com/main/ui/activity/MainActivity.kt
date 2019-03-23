package com.main.ui.activity

import android.os.Bundle
import com.base.ui.activity.BaseActivity
import com.main.R
import com.main.ui.fragment.DiscoveryFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContainer,DiscoveryFragment())
        manager.commit()
    }
}
