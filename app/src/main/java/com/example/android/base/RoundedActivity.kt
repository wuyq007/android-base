package com.example.android.base

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.android.base.databinding.ActivityRoundedBinding
import com.pers.libs.base.BaseActivity
import com.pers.libs.base.utils.dp2px
import com.pers.libs.base.utils.setCornerRadius

class RoundedActivity : BaseActivity() {


    private lateinit var binding: ActivityRoundedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoundedBinding.inflate(layoutInflater)
        setContentLayout(binding.root)

        binding.textView1.setCornerRadius(ContextCompat.getColor(mContext, R.color.app_theme), dp2px(12f));
    }

}