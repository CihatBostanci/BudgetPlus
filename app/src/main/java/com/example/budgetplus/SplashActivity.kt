package com.example.budgetplus

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetplus.databinding.ActivitySplashBinding
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.repository.RetrofilBuilder
import com.example.budgetplus.utils.*
import com.example.budgetplus.viewmodel.AccountViewModel
import com.example.budgetplus.viewmodel.GroupViewModel


class SplashActivity : BaseActivity() {
    private val SPLASHTAG = "SPLASHTAG"

        // Initalization property
    private val SPLASH_TIME_OUT: Long = 6000
    private var itemMap = mutableMapOf<Int, ImageView>()
    lateinit var animation: Animation
    private var width = 0
    private var height = 0
    private var set: AnimatorSet? = null

    //View Binding
    private lateinit var binding: ActivitySplashBinding

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var groupViewModel: GroupViewModel

    private  var userInfoResponseModelTemp : UserInfoResponseModel? = null
    private  var groupDetailsResponseModelTemp : GroupDetailsResponseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        RetrofilBuilder.getInstance(this)

        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        getUserInfoService()
        getGroupDetailsService()
        initUI()


        val intent  = Intent(this, MainActivity::class.java)

        //To Start Main Activity
        Log.d(SPLASHTAG, intent.extras.toString())

        Handler().postDelayed(
            {
                userInfoResponseModelTemp?.let {
                    intent.putExtraJson(USERINFORESPONSEMODEL, it)
                }
                groupDetailsResponseModelTemp?.let {
                    intent.putExtraJson(GROUPDETAILSRESPONSEMODEL, it)
                }
                startActivity(intent)
                finish()
            },
            SPLASH_TIME_OUT
        )
    }

    fun getGroupDetailsService() {
        groupViewModel.getGroupDetails().observe(this,_groupDetailsObserver)
    }

    private fun getUserInfoService() {
        accountViewModel.getUserInfos().observe(this,_userObserver)
    }

    private fun initUI() {
        //Animation Load
        animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.IWPlusLogo.animation = animationRotate
        binding.logo.animation = animation

        itemMap = mutableMapOf(
            0 to binding.IWBubble0,
            1 to binding.IWBubble1,
            2 to binding.IWBubble2,
            3 to binding.IWBubble3
        )

        //To trigger bubbles draw and animations
        bubbleMove()
        binding.logo.bringToFront()
        binding.IWPlusLogo.bringToFront()
    }

    private fun randomAnimateXAndYAxis(
        view: ImageView,
        axisType: String,
        next: Float
    ): ObjectAnimator {
        return if (axisType == "x") {
            ObjectAnimator.ofFloat(view, axisType, view.x, next)
        } else ObjectAnimator.ofFloat(view, axisType, view.y, next)

    }

    private fun bubbleMove() {


        drawAllImageViewAsCircle()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels


        set = createAnimation()
        set?.start()

        set?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {

                val set = animationSet()
                set.duration = 3000
                set.start()

            }
        })
    }

    private fun drawAllImageViewAsCircle() {
        binding.IWBubble0.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble0ColorDark),
                ContextCompat.getColor(this, R.color.bubble0ColorDark)
            )
        binding.IWBubble1.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble1ColorDark),
                ContextCompat.getColor(this, R.color.bubble1ColorDark)
            )
        binding.IWBubble2.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble2ColorDark),
                ContextCompat.getColor(this, R.color.bubble2ColorDark)
            )
        binding.IWBubble3.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble3ColorDark),
                ContextCompat.getColor(this, R.color.bubble3ColorDark)
            )
    }

    private fun drawCircle(backgroundColor: Int, borderColor: Int): GradientDrawable? {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        shape.setColor(backgroundColor)
        shape.setStroke(10, borderColor)
        return shape
    }

    private fun randomNumber(range: Int) = (0..range).random().toFloat()

    // generated random from 1 to 9 included
    private fun createAnimation(): AnimatorSet? {

        val set = animationSet()
        set.duration = 2000

        return set
    }

    private fun animationSet(): AnimatorSet {
        val set = AnimatorSet()
        val animationList = mutableListOf<ObjectAnimator>()

        for (i in 0..4) {
            if (itemMap[i] != null) {
                val animationY = randomAnimateXAndYAxis(
                    itemMap[i]!!, "y",
                    randomNumber(height)
                )
                //animationY.duration = 3000
                val animationX = randomAnimateXAndYAxis(
                    itemMap[i]!!, "x", randomNumber(width)
                )
                //animationX.duration = 3000

                animationList.add(animationX)
                animationList.add(animationY)
                //println(randomNumber(height).toString() + ": " + randomNumber(width))
            }

        }
        set.playTogether(
            animationList as Collection<Animator>?
        )
        return set
    }

    private val _userObserver = Observer<Resource<UserInfoResponseModel>>{
        when(it.status){
            Status.SUCCESS-> {
                hide()
                it.data?.let { userInfo ->

                    userInfoResponseModelTemp = userInfo
                    Log.d(SPLASHTAG , userInfo.toString())
                }
            }
            Status.ERROR ->{
                hide()
                //showToast(it.message)

            }
            Status.LOADING->{
                //show()
            }
        }
    }

    private val _groupDetailsObserver = Observer<Resource<GroupDetailsResponseModel>>{
        when(it.status){
            Status.SUCCESS-> {
                hide()
                it.data?.let { groupDetails ->
                    groupDetailsResponseModelTemp = groupDetails
                    Log.d(SPLASHTAG , groupDetails.toString())
                }
            }
            Status.ERROR ->{
                hide()
                //showToast(it.message)

            }
            Status.LOADING->{
                //show()
            }
        }
    }

}